<template>
    <Dialog title="${table.comment!}——多选" v-model="visible" width="300px">
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
    import { treeMultipleReferenceMixin } from '@/mixin/treeMultipleReferenceMixin.js'
    const MODULE_CODE = '${package.ModuleName}'
    const ENTITY_TYPE = '${entity?uncap_first}'
    export default {
        mixins: [treeMultipleReferenceMixin],
        data() {
            return {
                entityType: ENTITY_TYPE,
                moduleCode: MODULE_CODE,
                // eslint-disable-next-line no-eval
                api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
                pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',

            }
        },
        methods: {}
    }
</script>

<style></style>
