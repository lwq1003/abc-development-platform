<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="名称">
          <QueryText v-model="queryCondition.name" type="LK" />
        </el-form-item>
        <el-form-item label="编码">
          <QueryText v-model="queryCondition.code" type="LK" />
        </el-form-item>
        <el-form-item label="类型">
          <dictionary-select v-model="queryCondition.type" code="PermissionType" multiple />
        </el-form-item>
        <el-form-item label="权限编码">
          <QueryText v-model="queryCondition.permissionCode" type="LK" />
        </el-form-item>
        <el-form-item label="状态">
          <dictionary-select v-model="queryCondition.status" code="Status" multiple />
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
    <div class="mb-10px mt-10px">
      <el-button type="primary" icon="refresh" @click="refresh">刷新</el-button>
      <el-button v-permission="pageCode + 'add'" type="primary" icon="plus" @click="add"
        >新增</el-button
      >
      <el-button
        v-permission="pageCode + 'remove'"
        type="primary"
        icon="delete"
        @click="batchRemove"
        >删除</el-button
      >
      <el-button
        v-permission="pageCode + 'addByCopy'"
        type="primary"
        icon="CopyDocument"
        @click="addByCopy"
        >复制新增</el-button
      >
    </div>

    <el-card style="width: 100%">
      <div style="float: right; margin-top: 0; margin-bottom: 10px">
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
        @selection-change="selectionChange"
        @row-dblclick="rowDoubleClick"
      >
        <el-table-column type="selection" width="55" />
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
        <el-table-column fixed="right" label="操作" width="250">
          <template #default="scope">
            <el-button v-permission="pageCode + 'modify'" type="primary" @click="modify(scope.row)"
              >修改</el-button
            >
            <el-button v-permission="pageCode + 'remove'" type="primary" @click="remove(scope.row)"
              >删除</el-button
            >
            <el-dropdown class="ml-10px">
              <el-button type="primary">
                更多
                <el-icon class="el-icon--right"><arrow-down /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-button text v-permission="pageCode + 'enable'" @click="enable(scope.row)"
                      >启用</el-button
                    >
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button text v-permission="pageCode + 'disable'" @click="disable(scope.row)"
                      >停用</el-button
                    >
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <ListPager
        :page-num="pageInfo.pageNum"
        :page-size="pageInfo.pageSize"
        :page-total="pageTotal"
      />
    </el-card>

    <AddPage ref="addPage" @refresh="refresh" />
    <ModifyPage ref="modifyPage" @refresh="refresh" />
    <ViewPage ref="viewPage" />
  </ContentWrap>
</template>

<script lang="ts">
import { listMixin } from '@/mixin/listMixin.js'
import AddPage from './add.vue'
import ModifyPage from './modify.vue'
import ViewPage from './view.vue'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'permissionItem'
export default {
  components: {
    AddPage,
    ModifyPage,
    ViewPage
  },
  mixins: [listMixin],
  data() {
    return {
      name: ENTITY_TYPE + '-list',
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      columnList: [
        {
          prop: 'name',
          label: '名称',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'code',
          label: '编码',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'typeName',
          label: '类型',
          show: true,
          showOverflowTooltip: true,
          width: '120'
        },
        {
          prop: 'permissionCode',
          label: '权限编码',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'viewCode',
          label: '视图编码',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'component',
          label: '组件',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'statusName',
          label: '状态',
          show: true,
          showOverflowTooltip: true,
          width: '120'
        },
        {
          prop: 'permissionItemName',
          label: '上级',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'orderNo',
          label: '排序',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        }
      ],
      queryCondition: {
        //默认值处理
        type: '',
        status: 'NORMAL'
      }
    }
  },

  methods: {
    add() {
      this.$refs.addPage.init({ id: this.queryCondition.permissionItem })
    },
    commonParamChange(param) {
      this.queryCondition.permissionItem = param.id
    }
  }
}
</script>
