<template>
  <ContentWrap>
    <collapse-tab class="main-search">
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="类型">
          <el-cascader
            ref="processDefinition"
            v-model="queryCondition.processDefinitionSelected"
            :options="templateData"
            :props="{ expandTrigger: 'hover' }"
            clearable
            filterable
            :show-all-levels="false"
            class="form-item"
            @change="handleChange"
          />
        </el-form-item>
        <el-form-item label="单号">
          <QueryText v-model="queryCondition.businessNo" class="form-item" />
        </el-form-item>
        <el-form-item label="处理时间">
          <el-date-picker
            v-model="queryCondition.endTimeBeginForQuery"
            value-format="YYYY-MM-DD 00:00:00"
            type="date"
            align="right"
            unlink-panels
            class="form-item"
          />
        </el-form-item>
        <el-form-item label="至">
          <el-date-picker
            v-model="queryCondition.endTimeEndForQuery"
            value-format="YYYY-MM-DD 00:00:00"
            type="date"
            align="right"
            unlink-panels
            class="form-item"
          />
        </el-form-item>
        <el-form-item style="float: right">
          <QueryButton :page-code="pageCode" />
        </el-form-item>
        <div class="clearfix"></div>
      </el-form>
    </collapse-tab>
    <div class="mb-10px mt-10px">
      <el-button type="primary" icon="Refresh" @click="refresh">刷新</el-button>
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
        <el-table-column fixed="right" label="操作" align="center" width="100">
          <template #default="scope">
            <el-button type="primary" text @click="view(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>

      <list-pager
        :page-num="pageInfo.pageNum"
        :page-size="pageInfo.pageSize"
        :page-total="pageTotal"
      />
    </el-card>
  </ContentWrap>
</template>

<script>
import { listMixin } from '@/mixin/listMixin'
import { formatTime } from '@/utils/TableColumnFormatter.js'

const MODULE_CODE = 'workflow'
const ENTITY_TYPE = 'done'
export default {
  name: ENTITY_TYPE,
  mixins: [listMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      sortInfo: {
        sort_field: 'endTime',
        sort_sortType: 'descending'
      },
      queryCondition: {},
      columnList: [
        // prop label show 必须定义， width/showOverflowTooltip/formatFunc/sortable 需要的话定义
        { prop: 'processDefinitionName', label: '流程类型', show: true, showOverflowTooltip: true },
        { prop: 'nodeName', label: '环节', show: true, showOverflowTooltip: true },
        { prop: 'businessNo', label: '单号', show: true },
        { prop: 'processApplyName', label: '申请人', show: true },
        {
          prop: 'endTime',
          label: '处理时间',
          show: true,
          formatFunc: formatTime
        },
        {
          prop: 'startTime',
          label: '接收时间',
          show: true,
          formatFunc: formatTime
        }
      ],
      // 流程模板数据
      templateData: []
    }
  },
  mounted() {
    this.initFlowData()
  },
  methods: {
    initFlowData() {
      this.$api.businessflow.navigate.cascader().then((res) => {
        this.templateData = res.data
      })
    },
    // 选择级联组件
    handleChange() {
      const selectedNode = this.$refs.processDefinition.getCheckedNodes(true)
      if (selectedNode && selectedNode.length > 0) {
        this.queryCondition.processDefinitionKey = selectedNode[0].value
      } else {
        // 清空操作会触发
        this.queryCondition.processDefinitionKey = ''
      }
    },
    // 查看任务实例
    view(row) {
      this.$router.push({ name: 'flowView', query: { processInstanceId: row.processInstanceId } })
    }
  }
}
</script>

<style scoped></style>
