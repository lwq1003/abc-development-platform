<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="参数名称">
          <QueryText v-model="queryCondition.paramName" type="LK" />
        </el-form-item>
        <el-form-item label="参数编码">
          <QueryText v-model="queryCondition.paramCode" type="LK" />
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
        <el-table-column fixed="right" label="操作" width="320">
          <template #default="scope">
            <el-button v-permission="pageCode + 'modify'" type="primary" @click="modify(scope.row)"
              >修改</el-button
            >
            <el-button v-permission="pageCode + 'remove'" type="primary" @click="remove(scope.row)"
              >删除</el-button
            >
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
const MODULE_CODE = 'scheduler'
const ENTITY_TYPE = 'schedulerJobParam'
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
      columnList: [
        {
          prop: 'paramName',
          label: '参数名称',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'paramCode',
          label: '参数编码',
          show: true,
          showOverflowTooltip: true,
          width: '',
          sortable: true
        },
        {
          prop: 'paramValue',
          label: '参数值',
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
        paramName: ''
      }
    }
  },

  methods: {
    beforeInit(param) {
      this.queryCondition.job = this.$route.query.id
    },
    add() {
      this.$refs.addPage.init({ id: this.queryCondition.schedulerJob })
    },
    commonParamChange(param) {
      this.queryCondition.schedulerJob = param.id
    }
  }
}
</script>
