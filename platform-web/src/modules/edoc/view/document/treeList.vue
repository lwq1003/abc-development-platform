<template>
  <el-container class="layout-container" style="height: 100%">
    <el-aside width="200px">
      <TreeView ref="treeView" @change-selected="changeNode" />
    </el-aside>
    <el-main>
      <ListView :common-param="commonParam" @refresh="refresh" @change-tree-node="changeTreeNode" />
    </el-main>
  </el-container>
</template>

<script>
import { treeListMixin } from '@/mixin/treeListMixin.js'
import TreeView from '@/modules/edoc/view/folder/tree.vue'
import ListView from './list.vue'
const MODULE_CODE = 'edoc'
const ENTITY_TYPE = 'document'
export default {
  name: ENTITY_TYPE,
  components: {
    TreeView,
    ListView
  },
  mixins: [treeListMixin],
  data() {
    return {}
  },
  mounted() {
    const folderId = this.$route.query.id
    if (folderId) {
      this.$refs.treeView.init(folderId)
    }
  },
  methods: {
    changeTreeNode(id) {
      this.$refs.treeView.init(id)
    }
  }
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
