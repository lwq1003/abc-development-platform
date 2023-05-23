<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
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

        <el-form-item label="标题">
          <QueryText v-model="queryCondition.title" type="LK" />
        </el-form-item>
        <el-form-item label="状态">
          <dictionary-select v-model="queryCondition.status" code="Status" multiple />
        </el-form-item>
        <el-form-item label="是否重要">
          <dictionary-select v-model="queryCondition.importantFlag" code="YesOrNo" multiple />
        </el-form-item>
        <el-form-item label="是否置顶">
          <dictionary-select v-model="queryCondition.topFlag" code="YesOrNo" multiple />
        </el-form-item>
        <el-form-item label="允许评论">
          <dictionary-select v-model="queryCondition.commentFlag" code="YesOrNo" multiple />
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
                  <el-dropdown-item>
                    <el-button text v-permission="pageCode + 'setTop'" @click="setTop(scope.row)"
                      >置顶</el-button
                    >
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button
                      text
                      v-permission="pageCode + 'cancelTop'"
                      @click="cancelTop(scope.row)"
                      >取消置顶</el-button
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
import { getFormatMethod } from '@/utils/TableColumnFormatter.js'
const MODULE_CODE = 'support'
const ENTITY_TYPE = 'notice'
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
          prop: 'title',
          label: '标题',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'statusName',
          label: '状态',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'importantFlagName',
          label: '是否重要',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'topFlagName',
          label: '是否置顶',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'commentFlagName',
          label: '允许评论',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'readCount',
          label: '阅读次数',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'publisher',
          label: '发布人',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'publishTime',
          label: '发布时间',
          show: true,
          showOverflowTooltip: true,
          formatFunction: getFormatMethod('FORMAT_TIME'),
          sortable: true
        }
      ],
      queryCondition: {
        //默认值处理
        status: '',
        importantFlag: '',
        topFlag: '',
        commentFlag: ''
      }
    }
  },

  methods: {
    setTop(row) {
      this.api.setTop(row.id).then(() => {
        this.refresh()
      })
    },
    cancelTop(row) {
      this.api.cancelTop(row.id).then(() => {
        this.refresh()
      })
    }
  }
}
</script>
