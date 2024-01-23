<template>
  <div class="w-full">
    <el-input v-model="displayName" disabled style="width: 152px" />
    <el-button-group>
      <el-button icon="grid" @click="init" style="border-left-width: 0; padding: 10px" />
      <el-button icon="delete" @click="clear" style="border-left-width: 0; padding: 10px" />
    </el-button-group>
    <Dialog title="组织机构——多选" v-model="visible" width="300px">
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
  </div>
</template>

<script>
import { treeMultipleSelectMixin } from '@/mixin/treeMultipleSelectMixin.js'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'organization'
export default {
  name: 'OrganizationMultipleSelect',
  label: '组织机构多选',
  mixins: [treeMultipleSelectMixin],
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
