<template>
  <el-container class="layout-container" style="height: 100%">
    <el-aside width="200px">
      <TreeView ref="treeView" @change-selected="changeNode" />
    </el-aside>
    <el-main>
      <ListView :common-param="commonParam" @refresh="refresh" />
    </el-main>
  </el-container>
</template>

<script>
import { treeListMixin } from '@/mixin/treeListMixin.js'
import TreeView from './tree.vue'
import ListView from './list.vue'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'dictionaryType'
export default {
  name: ENTITY_TYPE + '-treeList',
  components: {
    TreeView,
    ListView
  },
  mixins: [treeListMixin],
  data() {
    return {}
  },
  methods: {}
}
</script>

<style scoped>
.layout-container .el-aside {
  color: var(--el-text-color-primary);
  background: white;
}

.layout-container .el-main {
  padding: 0;
}
</style>
