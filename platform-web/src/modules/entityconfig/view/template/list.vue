<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="名称">
          <QueryText v-model="queryCondition.name" type="LK"></QueryText>
        </el-form-item>
        <el-form-item style="float: right">
          <QueryButton :page-code="pageCode" />
          <el-button type="primary" icon="Search" @click="customQuery">自定义查询</el-button>
        </el-form-item>
        <div class="clearfix"></div>
      </el-form>
    </CollapseTab>
    <div class="mb-10px mt-10px">
      <el-button type="primary" icon="refresh" @click="refresh">刷新</el-button>
      <el-button v-permission="pageCode + 'add'" type="primary" icon="plus" @click="add"
        >新增
      </el-button>
      <el-button
        v-permission="pageCode + 'remove'"
        type="primary"
        icon="delete"
        @click="batchRemove"
        >删除
      </el-button>
      <el-button
        v-permission="pageCode + 'addByCopy'"
        type="primary"
        icon="CopyDocument"
        @click="addByCopy"
        >复制新增
      </el-button>
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
        <el-table-column fixed="right" label="操作" width="250">
          <template #default="scope">
            <el-button v-permission="pageCode + 'modify'" type="primary" @click="modify(scope.row)"
              >修改
            </el-button>
            <el-button v-permission="pageCode + 'remove'" type="primary" @click="remove(scope.row)"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <ListPager
        :page-num="pageInfo.pageNum"
        :page-size="pageInfo.pageSize"
        :page-total="pageTotal"
      />
    </el-card>
    <DetailPage ref="detailPage" @refresh="refresh" />
    <CustomQuery ref="customQuery" @confirm="queryWithCustom" />
  </ContentWrap>
</template>

<script lang="ts">
import { listMixin } from '@/mixin/listMixin.js'
import DetailPage from './detail.vue'

const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'template'
export default {
  components: {
    DetailPage
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
          prop: 'name',
          label: '名称',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'content',
          label: '描述',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'organizaitonName',
          label: '组织机构',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'time1',
          label: '时分秒',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'time2',
          label: '时分',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'time3',
          label: '时',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'date1',
          label: '年月日',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'day2',
          label: '年月日时分',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'day3',
          label: '年月日时分秒',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        }
      ],
      queryCondition: {
        //默认值处理
      }
    }
  },

  methods: {}
}
</script>
