<template>
  <Dialog title="用户设置" v-model="visible" fullscreen>
    <el-container class="layout-container" style="height: 100%">
      <el-aside width="200px">
        <TreeView ref="treeView" @change-selected="changeNode" />
      </el-aside>
      <el-main>
        <ListView :common-param="commonParam" @refresh="refresh" />
      </el-main>
    </el-container>
  </Dialog>
</template>

<script>
import { treelistMixin } from '@/mixin/treelistMixin.js'
import { Dialog } from '@/components/abc/Dialog'
import TreeView from '@/modules/system/view/organization/tree.vue'
import ListView from './list.vue'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'user'
export default {
  name: ENTITY_TYPE + '-treelist',
  components: {
    Dialog,
    TreeView,
    ListView
  },
  mixins: [treelistMixin],
  data() {
    return {
      visible: false
    }
  },
  methods: {
    init(param) {
      this.commonParam = Object.assign(this.commonParam, { userGroupId: param.id })
      this.visible = true
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
