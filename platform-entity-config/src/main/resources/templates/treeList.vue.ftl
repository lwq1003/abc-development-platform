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
    import {treelistMixin} from '@/mixin/treelistMixin.js'
    <#if treeListEntityView.treePath??>
    import TreeView from '@/modules/${treeListEntityView.treePath}'
    <#else>
    import TreeView from './tree.vue'
    </#if>
    import ListView from './list.vue'
    const MODULE_CODE = '${package.ModuleName}'
    const ENTITY_TYPE = '${entity?uncap_first}'
    export default {
        <#if mainViewCode=="treeList">
        name: ENTITY_TYPE,
        </#if>
        mixins: [treelistMixin],
        components: {
            TreeView,
            ListView
        },
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
