<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="日志类型">
          <dictionary-select v-model="queryCondition.logType" code="LogType" multiple />
        </el-form-item>
        <el-form-item label="操作人账号">
          <QueryText v-model="queryCondition.operatorAccount" type="LK" />
        </el-form-item>
        <el-form-item label="操作人姓名">
          <QueryText v-model="queryCondition.operatorName" type="LK" />
        </el-form-item>
        <el-form-item label="操作人ip">
          <QueryText v-model="queryCondition.operatorIp" type="LK" />
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

    <ViewPage ref="viewPage" />
  </ContentWrap>
</template>

<script lang="ts">
import { listMixin } from '@/mixin/listMixin.js'

import ViewPage from './view.vue'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'log'
export default {
  components: {
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
          prop: 'content',
          label: '内容',
          show: true,
          showOverflowTooltip: true,

          sortable: true
        },
        {
          prop: 'logTypeName',
          label: '日志类型',
          show: true,
          showOverflowTooltip: true,
          width: '120'
        },
        {
          prop: 'requestTime',
          label: '请求时间',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'requestPath',
          label: '请求路径',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'requestMethod',
          label: '请求方法',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'operatorId',
          label: '操作人标识',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'operatorAccount',
          label: '操作人账号',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'operatorName',
          label: '操作人姓名',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'operatorIp',
          label: '操作人ip',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'responseCode',
          label: '响应结果',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'timeConsuming',
          label: '执行耗时ms',
          show: true,
          showOverflowTooltip: true,
          width: '150',
          sortable: true
        }
      ],
      queryCondition: {
        //默认值处理
        logType: ''
      }
    }
  },

  methods: {}
}
</script>
