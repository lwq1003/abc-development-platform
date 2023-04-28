<template>
  <el-tree
    ref="tree"
    class="aside-tree"
    :data="treeData"
    node-key="id"
    :default-expanded-keys="cacheTreeExpandedKeys"
    @current-change="handleTreeSelectChange"
    @node-expand="handleNodeExpand"
    @node-collapse="handleNodeCollapse"
  />
</template>

<script>
import { treeMixin } from '@/mixin/treeMixin.js'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'permissionItem'
export default {
  name: ENTITY_TYPE + '-tree',
  mixins: [treeMixin],
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
