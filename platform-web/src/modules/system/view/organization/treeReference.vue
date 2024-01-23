<template>
  <div class="w-full">
    <el-input v-model="displayName" disabled style="width: 152px" />
    <el-button-group>
      <el-button icon="grid" @click="init" style="border-left-width: 0; padding: 10px" />
      <el-button icon="delete" @click="clear" style="border-left-width: 0; padding: 10px" />
    </el-button-group>
    <Dialog title="组织机构选择" v-model="visible" width="300px">
      <el-input v-model="searchValue" placeholder="请输入关键词" style="margin-bottom: 10px" />
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
import { treeReferenceMixin } from '@/mixin/treeReferenceMixin.js'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'organization'
export default {
  name: 'OrganizationSingleSelect',
  label: '组织机构单选',
  mixins: [treeReferenceMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 名称键值
      nameKey: 'name'
    }
  },
  methods: {}
}
</script>

<style></style>
