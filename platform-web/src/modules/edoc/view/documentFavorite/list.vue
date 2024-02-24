<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="名称">
          <el-input v-model="queryCondition.name" />
        </el-form-item>
        <el-form-item style="float: right">
          <QueryButton :page-code="pageCode" />
        </el-form-item>
        <div class="clearfix"></div>
      </el-form>
    </CollapseTab>
    <div class="mb-10px mt-10px">
      <el-button type="primary" icon="Refresh" @click="refresh">刷新</el-button>
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
        <el-table-column fixed="right" label="操作" width="130">
          <template #default="scope">
            <el-button
              v-if="scope.row.objectType === $constant.POP_DOCUMENT_TYPE_DOCUMENT"
              type="primary"
              link
              @click="previewDocument(scope.row)"
              >预览</el-button
            >
            <el-button
              v-if="scope.row.objectType === $constant.POP_DOCUMENT_TYPE_DOCUMENT"
              type="primary"
              link
              @click="downloadDocument(scope.row)"
              >下载</el-button
            >
            <el-button
              type="primary"
              link
              v-if="scope.row.objectType === $constant.POP_DOCUMENT_TYPE_FOLDER"
              @click="viewFolder(scope.row)"
              >查看</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </ContentWrap>
  <PreviewDocument ref="previewDocument" />
</template>

<script lang="ts">
import { listMixin } from '@/mixin/listMixin.js'
import PreviewDocument from '../document/preview.vue'
import { closeTab } from '@/utils'
const MODULE_CODE = 'edoc'
const ENTITY_TYPE = 'documentFavorite'
export default {
  name: ENTITY_TYPE,
  components: { PreviewDocument },
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
          showOverflowTooltip: true
        },
        {
          prop: 'type',
          label: '类型',
          show: true,
          width: 120,
          showOverflowTooltip: true
        },
        {
          prop: 'size',
          label: '大小',
          show: true,
          width: 120,
          showOverflowTooltip: true
        }
      ],
      queryCondition: {
        //默认值处理
        name: ''
      }
    }
  },

  methods: {
    // 获取表格数据
    loadData() {
      return new Promise((resolve) => {
        this.loading = true
        this.$api.edoc.documentFavorite
          .getFavoriteList(this.queryCondition.name)
          .then((res) => {
            this.tableData = res.data
            resolve()
          })
          .finally(() => {
            this.loading = false
            this.currentId = this.$constant.NO_ITEM_SELECTED
          })
      })
    },
    // 处理表格双击
    rowDoubleClick(row) {
      if (row.objectType === this.$constant.POP_DOCUMENT_TYPE_FOLDER) {
        // 文件夹,切换到文档库页面
        this.viewFolder(row)
      } else if (row.objectType === this.$constant.POP_DOCUMENT_TYPE_DOCUMENT) {
        // 文档,预览
        this.previewDocument(row)
      }
    },
    previewDocument(row) {
      this.$refs.previewDocument.show(row.objectId, row.name)
    },
    downloadDocument(row) {
      this.$api.edoc.document.download(row.objectId)
    },
    viewFolder(row) {
      // 跳转到文档库并自动展开,当文档库页面已打开的情况下会无法展开，因此先删除tab页
      const view = { path: '/edoc/document', name: 'document' }
      closeTab(view)
      this.$router.push({ name: 'document', query: { id: row.objectId } })
    }
  }
}
</script>
