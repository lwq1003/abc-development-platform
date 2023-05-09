<template>
  <div class="w-full">
    <el-input v-model="displayName" disabled>
      <template #append>
        <el-button-group>
          <el-button icon="grid" @click="init" />
          <el-button icon="delete" @click="clear" />
        </el-button-group>
      </template>
    </el-input>
    <Dialog title="字典类型选择" v-model="visible" width="300px">
      <el-input v-model="searchValue" placeholder="请输入名称查询" style="margin-bottom: 10px" />
      <el-tag>当前选中：{{ currentName }}</el-tag>
      <el-tree
        ref="tree"
        class="aside-tree"
        :data="treeData"
        node-key="id"
        :filter-node-method="filterNode"
        :default-expanded-keys="cacheTreeExpandedKeys"
        @current-change="handleTreeSelectChange"
        @node-expand="handleNodeExpand"
        @node-collapse="handleNodeCollapse"
      />
      <template #footer>
        <el-button type="primary" @click="confirm">确定</el-button>
        <el-button @click="close">关闭</el-button>
      </template>
    </Dialog>
  </div>
</template>

<script>
// 数据字典相比普通实体选择，返回的值是编码code而不是标识id，因此这里增加了额外属性和覆写了方法
import { treeReferenceMixin } from '@/mixin/treeReferenceMixin.js'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'dictionaryType'
export default {
  mixins: [treeReferenceMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 名称键值
      nameKey: 'name',
      // 当前编码
      currentCode: ''
    }
  },
  methods: {
    // 初始化
    init(param) {
      if (this.beforeInit != null) {
        this.beforeInit(param)
      }

      if (this.modelValue) {
        this.currendCode = this.modelValue
        this.api.getByCode(this.modelValue).then((res) => {
          this.displayName = res.data[this.nameKey]
          this.currentName = this.displayName
          this.currentId = res.data.id
        })
      }

      this.loadData().then((res) => {
        if (this.afterInit) {
          this.afterInit(param)
        }
        this.visible = true
      })
    },
    confirm() {
      // 更新父组件绑定值
      this.$emit('update:modelValue', this.currentCode)
      this.$emit('change', this.currentId)
      this.visible = false
    },
    // 树表相关操作
    handleTreeSelectChange(data) {
      // 保存标识及名称用于新增操作
      this.currentId = data.id
      this.currentName = data.label
      this.currentCode = data.code
      this.$emit('change-selected', this.currentId, this.currentName)
    },
    // 获取选中的名称
    getSelectedName() {
      if (this.modelValue) {
        this.api.getByCode(this.modelValue).then((res) => {
          this.displayName = res.data[this.nameKey]
          this.currentName = this.displayName
        })
      }
    }
  }
}
</script>

<style></style>
