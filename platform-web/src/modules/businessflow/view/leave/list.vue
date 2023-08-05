<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="单据编号">
          <QueryText v-model="queryCondition.billNo" type="LK" />
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker
            v-model="queryCondition.startTimeBeginForQuery"
            :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
            :type="$dateFormatter.getDatetimeType('SECOND')"
            align="right"
            unlink-panels
            placeholder="开始"
          />
        </el-form-item>
        <el-form-item label="至">
          <el-date-picker
            v-model="queryCondition.startTimeEndForQuery"
            :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
            :type="$dateFormatter.getDatetimeType('SECOND')"
            align="right"
            unlink-panels
            placeholder="结束"
          />
        </el-form-item>

        <el-form-item label="总计天数">
          <el-input v-model="queryCondition.totalBeginForQuery" />
        </el-form-item>
        <el-form-item label="至">
          <el-input v-model="queryCondition.totalEndForQuery" />
        </el-form-item>

        <el-form-item label="请假类型">
          <dictionary-select v-model="queryCondition.leaveType" code="LeaveType" multiple />
        </el-form-item>
        <el-form-item style="float: right">
          <QueryButton :page-code="pageCode" />
        </el-form-item>
        <div class="clearfix"></div>
      </el-form>
    </CollapseTab>
    <div class="mb-10px mt-10px">
      <el-button type="primary" icon="Refresh" @click="refresh">刷新</el-button>
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
const MODULE_CODE = 'businessflow'
const ENTITY_TYPE = 'leave'
export default {
  name: ENTITY_TYPE,
  components: {
    AddPage,
    ModifyPage,
    ViewPage
  },
  mixins: [listMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 排序信息
      sortInfo: {
        sort_field: 'id',
        sort_sortType: 'descending'
      },
      columnList: [
        {
          prop: 'billNo',
          label: '单据编号',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'startTime',
          label: '开始时间',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'endTime',
          label: '结束时间',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'total',
          label: '总计天数',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'leaveTypeName',
          label: '请假类型',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'reason',
          label: '事由',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'flowStatusName',
          label: '流程状态',
          show: true,
          showOverflowTooltip: true
        }
      ],
      queryCondition: {
        //默认值处理
        leaveType: ''
      }
    }
  },

  methods: {}
}
</script>
