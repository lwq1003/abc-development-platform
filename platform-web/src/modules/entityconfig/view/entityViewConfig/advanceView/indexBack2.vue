<template>
  <fc-designer v-model="value" ref="designer" :menu="menu" />
</template>

<script lang="ts">
import DictionarySelect from '@/components/abc/DictionarySelect/DictionarySelect.vue'
import DictionarySelectDesigner from '@/components/abc/FormCreateComponent/DictionarySelect.js'
import row from '@form-create/designer'
export default {
  components: { DictionarySelect, DictionarySelectDesigner, row },
  data() {
    return {
      //表单数据
      value: {},
      //组件参数配置
      option: {
        //表单提交事件
        onSubmit: function (formData) {
          alert(JSON.stringify(formData))
        }
      },
      menu: [
        {
          title: '自定义',
          name: 'custom',
          list: []
        },
        {
          name: 'layout',
          title: '布局组件',
          list: [row]
        }
      ]
    }
  },
  created() { },
  mounted() {
    //插入组件规则
    this.$refs.designer.addComponent(DictionarySelectDesigner)
    this.$refs.designer.addComponent(row)

    // const dictionarySelect = {
    //   icon: DictionarySelectDesigner.icon,
    //   name: DictionarySelectDesigner.name,
    //   label: DictionarySelectDesigner.label
    // }
    // //插入分组
    // this.$refs.designer.addMenu({
    //   title: '自定义',
    //   name: 'custom',
    //   list: [dictionarySelect]
    // })

    /*
    // const property = maker.input('title', 'field', 'value')
    // console.log(property)
    // console.log(property.getRule())
    // this.rule.push(property.getRule())
    // this.$refs.designer.setRule(this.rule)
    // const property = maker.create('input', 'name', '张三').props({ type: 'text' })
    // const row = maker.create('FcRow')
    // const col1 = maker.create('col')
    // row.children = []
    // row.children.push(col1.getRule())
    // const property = maker.input('title', 'field', 'value')
    // col1.children = []
    // col1.children.push(property.getRule())
    // const row = {
    //   type: 'FcRow',
    //   _fc_drag_tag: 'row',
    //   hidden: false,
    //   display: true
    // }
    // const col = {
    //   type: 'col',
    //   props: {
    //     span: 12
    //   },
    //   _fc_drag_tag: 'col',
    //   hidden: false,
    //   display: true
    // }
    // row.children = [col]
    // const property = maker.input('title', 'field', 'value').getRule()
    // col.children = [property]
    // this.rule.push(row)
    // this.$refs.designer.setRule(this.rule)
    // this.rule.push(maker.input('title', 'field', 'value'))
    // const rules = this.fApi.rule
    // console.log(rules)
    // this.$refs.designer.setRule(rules)
    // const json = this.$refs.designer.getJson()
    // console.log(json)
    // 注册自定义组件
    // this.$formCreate.component('DictionaryRadioGroup', DictionaryRadioGroup)
    this.$formCreate.component('DictionarySelect', DictionarySelect)
    // this.$formCreate.component('OrganizationReference', OrganizationReference)
    // this.$refs.designer.addComponent('DictionaryRadioGroup', DictionaryRadioGroup)
    // this.$refs.designer.addComponent('DictionarySelect', DictionarySelect)
    // this.$refs.designer.addComponent('OrganizationReference', OrganizationReference)
    // 获取视图属性
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
        let property = {}
        if (item.widgetType == 'TEXT') {
          property = maker
            .create('input', 'entityData.' + item.code, item.name)
            .props({ type: 'text' })
        }
        // else if (item.widgetType == 'RADIO_BUTTON_GROUP') {
        //   property = maker
        //     .create('DictionaryRadioGroup', 'entityData.' + item.code, item.name)
        //     .props({ code: item.dictionaryType })
        // }
        else if (item.widgetType == 'DROP_DOWN_LIST') {
          property = maker
            .create('DictionarySelect', 'entityData.' + item.code, item.name)
            .props({ code: item.dictionaryType })
        } else if (item.widgetType == 'DATETIME') {
          property = maker
            .datePicker(item.name, 'entityData.' + item.code, item.defaultValue)
            .props({
              valueFormat: this.$dateFormatter.getDatetimeFormat(item.formatPattern),
              type: this.$dateFormatter.getDatetimeType(item.formatPattern)
            })
        }
        // else if (item.dataType == 'ENTITY') {
        //   property = maker
        //     .create(item.entityCode + 'Reference', 'entityData.' + item.code, item.name)
        //     .props({ code: item.dictionaryType })
        // }
        else {
          property = maker.input(item.name, 'entityData.' + item.code, item.defaultValue)
        }
        // 设置默认值
        property = property
          .value(item.defaultValue)
          // 设置两列
          .col({ span: 12, labelWidth: 150 })
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
    */
  },
  methods: {}
}
</script>

<style></style>
