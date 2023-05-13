<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="应用编码">
          <QueryText v-model="queryCondition.appCode" type="LK" />
        </el-form-item>
        <el-form-item label="服务编码">
          <QueryText v-model="queryCondition.serviceCode" type="LK" />
        </el-form-item>
        <el-form-item label="请求时间">
          <el-date-picker
            v-model="queryCondition.requestTimeBeginForQuery"
            :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
            :type="$dateFormatter.getDatetimeType('SECOND')"
            align="right"
            unlink-panels
            placeholder="开始"
          />
        </el-form-item>
        <el-form-item label="至">
          <el-date-picker
            v-model="queryCondition.requestTimeEndForQuery"
            :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
            :type="$dateFormatter.getDatetimeType('SECOND')"
            align="right"
            unlink-panels
            placeholder="结束"
          />
        </el-form-item>

        <el-form-item label="执行结果">
          <QueryText v-model="queryCondition.executeResult" type="LK" />
        </el-form-item>
        <el-form-item label="错误编码">
          <QueryText v-model="queryCondition.errorCode" type="LK" />
        </el-form-item>
        <el-form-item label="错误信息">
          <QueryText v-model="queryCondition.errorMessage" type="LK" />
        </el-form-item>
        <el-form-item label="请求业务数据">
          <QueryText v-model="queryCondition.requestBusinessData" type="LK" />
        </el-form-item>
        <el-form-item label="执行耗时ms">
          <el-input v-model="queryCondition.executionTimeBeginForQuery" />
        </el-form-item>
        <el-form-item label="至">
          <el-input v-model="queryCondition.executionTimeEndForQuery" />
        </el-form-item>

        <el-form-item style="float: right">
          <QueryButton :page-code="pageCode" />
        </el-form-item>
        <div class="clearfix"></div>
      </el-form>
    </CollapseTab>
    <div class="mb-10px mt-10px">
      <el-button type="primary" icon="refresh" @click="refresh">刷新</el-button>
      <el-button
        v-permission="pageCode + 'remove'"
        type="primary"
        icon="delete"
        @click="batchRemove"
        >删除</el-button
      >
    </div>

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

    <ViewPage ref="viewPage" />
  </ContentWrap>
</template>

<script lang="ts">
import { listMixin } from '@/mixin/listMixin.js'
import { formatTime } from '@/utils/TableColumnFormatter.js'

import ViewPage from './view.vue'
const MODULE_CODE = 'cip'
const ENTITY_TYPE = 'apiServiceLog'
export default {
  name: ENTITY_TYPE,
  components: {
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
          prop: 'appCode',
          label: '应用编码',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'serviceCode',
          label: '服务编码',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'requestTime',
          label: '请求时间',
          show: true,
          showOverflowTooltip: true,
          formatFunction: formatTime,
          sortable: true
        },
        {
          prop: 'receiveTime',
          label: '收到时间',
          show: true,
          showOverflowTooltip: true,
          formatFunction: formatTime,
          sortable: true
        },
        {
          prop: 'executeResult',
          label: '执行结果',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'errorCode',
          label: '错误编码',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'errorMessage',
          label: '错误信息',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'responseTime',
          label: '响应时间',
          show: true,
          showOverflowTooltip: true,
          formatFunction: formatTime,
          sortable: true
        },
        {
          prop: 'executionTime',
          label: '执行耗时ms',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        }
      ],
      queryCondition: {
        //默认值处理
        errorMessage: ''
      }
    }
  },

  methods: {}
}
</script>
