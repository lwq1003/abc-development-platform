<template>
  <div class="w-full">
    <el-input v-model="displayName" disabled style="width: 152px" />
    <el-button-group>
      <el-button icon="grid" @click="init" style="border-left-width: 0; padding: 10px" />
      <el-button icon="delete" @click="clear" style="border-left-width: 0; padding: 10px" />
    </el-button-group>
    <Dialog title="用户——选择" v-model="visible" width="80%">
      <el-container class="layout-container" style="height: 100%">
        <el-aside width="200px">
          <TreeView ref="treeView" @change-selected="changeNode" />
        </el-aside>
        <el-main>
          <CollapseTab>
            <el-form :inline="true" :model="queryCondition" label-width="80px" @keyup.enter="query">
              <!--查询条件区 -->
              <el-form-item label="姓名">
                <QueryText v-model="queryCondition.name" type="LK" />
              </el-form-item>
              <el-form-item label="账号">
                <QueryText v-model="queryCondition.account" type="LK" />
              </el-form-item>
              <el-form-item>
                <el-checkbox v-model="queryCondition.ignoreParent">查询全部</el-checkbox>
              </el-form-item>
              <el-form-item style="float: right">
                <QueryButton :page-code="pageCode" />
              </el-form-item>
              <div class="clearfix"></div>
            </el-form>
          </CollapseTab>
          <el-card style="width: 100%">
            <div style="margin-top: 0; margin-bottom: 10px; float: right">
              <ColumnsController :value="columnList" :tableKey="tableKey" />
            </div>
            <el-table
              v-loading="loading"
              :data="tableData"
              style="width: 100%"
              highlight-current-row
              border
              @sort-change="sortChange"
              @current-change="rowChange"
            >
              <el-table-column
                v-for="(item, index) in showCols"
                :key="index"
                :label="item.label"
                :prop="item.prop"
                :show-overflow-tooltip="item.showOverflowTooltip"
                :width="item.width"
                :formatter="item.formatFunc"
                :sortable="item.sortable"
              />
            </el-table>
            <ListPager
              :page-num="pageInfo.pageNum"
              :page-size="pageInfo.pageSize"
              :page-total="pageTotal"
            />
          </el-card>
        </el-main>
      </el-container>
      <template #footer>
        <el-button type="primary" @click="confirm">确定</el-button>
        <el-button @click="close">关闭</el-button>
      </template>
    </Dialog>
  </div>
</template>

<script>
import { treeListReferenceMixin } from '@/mixin/treeListReferenceMixin.js'
import TreeView from '@/modules/system/view/organization/tree.vue'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'user'
export default {
  name: ENTITY_TYPE,
  components: {
    TreeView
  },
  mixins: [treeListReferenceMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      columnList: [
        {
          prop: 'name',
          label: '姓名',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'account',
          label: '账号',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'organizationName',
          label: '组织机构',
          show: true,
          showOverflowTooltip: true
        }
      ],
      queryCondition: {
        //默认值处理
      },
      // 名称键值
      nameKey: 'name'
    }
  },
  methods: {
    commonParamChange(param) {
      this.queryCondition.organization = param.id
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
