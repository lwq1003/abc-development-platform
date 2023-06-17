<template>
  <Dialog title="用户组——多选" v-model="visible" width="300px">
    <el-input v-model="searchValue" placeholder="请输入过滤值" style="margin-bottom: 10px" />
    <el-tree
      ref="tree"
      class="aside-tree"
      :data="treeData"
      node-key="id"
      show-checkbox
      :filter-node-method="filterNode"
      :default-expanded-keys="cacheTreeExpandedKeys"
      :default-checked-keys="checkedNodesId"
    />
    <template #footer>
      <el-button type="primary" @click="confirm">确定</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { treeMultipleDirectReferenceMixin } from '@/mixin/treeMultipleDirectReferenceMixin.js'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'userGroup'
export default {
  mixins: [treeMultipleDirectReferenceMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':'
    }
  },
  methods: {}
}
</script>

<style></style>
