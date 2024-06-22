<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="标题">
          <QueryText v-model="queryCondition.title" type="LK" />
        </el-form-item>
        <el-form-item label="发布时间">
          <el-date-picker
            v-model="queryCondition.publishTimeBeginForQuery"
            :value-format="$dateFormatter.getDatetimeFormat('DAY')"
            :type="$dateFormatter.getDatetimeType('DAY')"
            align="right"
            unlink-panels
            placeholder="开始"
          />
        </el-form-item>
        <el-form-item label="至">
          <el-date-picker
            v-model="queryCondition.publishTimeEndForQuery"
            :value-format="$dateFormatter.getDatetimeFormat('DAY')"
            :type="$dateFormatter.getDatetimeType('DAY')"
            align="right"
            unlink-panels
            placeholder="结束"
          />
        </el-form-item>

        <el-form-item style="float: right">
          <QueryButton :page-code="pageCode" />
        </el-form-item>
        <div class="clearfix"></div>
      </el-form>
    </CollapseTab>
    <div class="mb-10px mt-10px"> </div>

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
  </ContentWrap>
</template>

<script lang="ts">
import { listMixin } from '@/mixin/listMixin.js'

import { getFormatMethod } from '@/utils/TableColumnFormatter.js'
const MODULE_CODE = 'support'
const ENTITY_TYPE = 'notice'
export default {
  name: ENTITY_TYPE,
  components: {},
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
          prop: 'title',
          label: '标题',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'readCount',
          label: '阅读次数',
          show: true,
          showOverflowTooltip: true,
          sortable: true,
          width: 120
        },
        {
          prop: 'publisher',
          label: '发布人',
          show: true,
          showOverflowTooltip: true,
          sortable: true,
          width: 120
        },
        {
          prop: 'publishTime',
          label: '发布时间',
          show: true,
          showOverflowTooltip: true,
          formatFunction: getFormatMethod('FORMAT_TIME'),
          sortable: true,
          width: 240
        }
      ],
      queryCondition: {
        //默认值处理
        status: 'NORMAL',
        importantFlag: '',
        topFlag: ''
      }
    }
  },

  methods: {
    // 覆写方法，使用带组织机构数据权限过滤的处理
    loadData() {
      return new Promise((resolve) => {
        this.loading = true
        const params = Object.assign(this.queryCondition, this.pageInfo, this.sortInfo)
        this.api
          .viewList(params)
          .then((res) => {
            this.tableData = res.data.records
            this.pageTotal = res.data.total
            resolve()
          })
          .finally(() => {
            this.loading = false
            this.currentId = this.$constant.NO_ITEM_SELECTED
          })
      })
    },
    rowDoubleClick(row) {
      this.$router.push({ path: '/support/noticeView', query: { id: row.id } })
    }
  }
}
</script>
