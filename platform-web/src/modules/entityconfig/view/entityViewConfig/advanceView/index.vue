<template>
  <el-row><form-create :rule="rule" v-model:api="fApi" :option="options" /></el-row>
  <el-row>
    <fc-designer v-model="value" ref="designer">
      <template #handle>
        <el-button type="primary" @click="preview">预览</el-button>
      </template>
    </fc-designer>
  </el-row>
</template>

<script lang="ts">
import DictionarySelectDesigner from '@/components/abc/FormCreateComponent/DictionarySelect.js'
import DictionarySelect from '@/components/abc/DictionarySelect/DictionarySelect.vue'
import { maker } from '@form-create/element-ui'
export default {
  components: { DictionarySelectDesigner, DictionarySelect, maker },
  data() {
    return {
      //form-create属性
      fApi: {},
      options: {
        onSubmit: (formData) => {
          alert(JSON.stringify(formData))
        },
        resetBtn: true
      },
      rule: [],
      //fc-designer属性
      //表单数据
      value: {}
    }
  },

  mounted() {
    //插入组件规则
    this.$refs.designer.addComponent(DictionarySelectDesigner)
    // this.$refs.designer.addComponent('DictionarySelect', DictionarySelect)
    //读取自定义组件信息，生成左侧组件对象
    const dictionarySelect = {
      icon: DictionarySelectDesigner.icon,
      name: DictionarySelectDesigner.name,
      label: DictionarySelectDesigner.label
    }
    // 插入自定义分组及自定义组件
    this.$refs.designer.addMenu({
      title: '自定义',
      name: 'custom',
      list: [dictionarySelect]
    })

    //  双列初始化
    let row = {}
    let col = {}
    this.$api.entityconfig.viewProperty.listByView(this.$route.query.id).then((res) => {
      const propertyList = res.data
      propertyList.forEach((item, index) => {
        if (index % 2 == 0) {
          row = {
            type: 'FcRow',
            _fc_drag_tag: 'row',
            hidden: false,
            display: true,
            children: []
          }
          col = {
            type: 'col',
            props: {
              span: 12
            },
            _fc_drag_tag: 'col',
            hidden: false,
            display: true
          }
          row.children = [col]
          this.rule.push(row)
        } else {
          col = {
            type: 'col',
            props: {
              span: 12
            },
            _fc_drag_tag: 'col',
            hidden: false,
            display: true
          }
          row.children.push(col)
        }
        // 注册自定义组件
        this.$formCreate.component(DictionarySelect.name, DictionarySelect)
        let property = {}
        if (item.widgetType == 'TEXT') {
          property = maker
            .create('input', 'entityData.' + item.code, item.name)
            .props({ type: 'text' })
        } else if (item.widgetType == 'DROP_DOWN_LIST') {
          property = maker
            .create(DictionarySelect.name, 'entityData.' + item.code, item.name)
            .props({ code: item.dictionaryType })
        } else if (item.widgetType == 'DATETIME') {
          property = maker
            .datePicker(item.name, 'entityData.' + item.code, item.defaultValue)
            .props({
              valueFormat: this.$dateFormatter.getDatetimeFormat(item.formatPattern),
              type: this.$dateFormatter.getDatetimeType(item.formatPattern)
            })
        } else {
          property = maker.input(item.name, 'entityData.' + item.code, item.defaultValue)
        }
        // 设置默认值
        property = property.value(item.defaultValue)
        //设置必填
        if (item.requireFlag == 'YES') {
          property = property.validate([
            { required: true, message: '【' + item.name + '】不能为空', trigger: 'blur' }
          ])
        }
        // 设置只读
        if (item.readonly == 'YES') {
          property = property.readonly(true)
        }
        col.children = [property.getRule()]
      })
      this.$refs.designer.setRule(this.rule)
    })
  },
  methods: {
    preview() {
      this.rule = this.$refs.designer.getRule()
      this.options = this.$refs.designer.getOption()
    }
  }
}
</script>

<style></style>
