<template>
  <Dialog title="文档版本" v-model="visible" width="800px" @close="close">
    <el-card style="width: 100%">
      <div style="margin-top: 0; margin-bottom: 10px; float: right">
        <ColumnsController :value="columnList" :tableKey="tableKey" />
      </div>

      <el-table :data="tableData" style="width: 100%" highlight-current-row border>
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
            <el-button
              v-permission="pageCode + 'preview'"
              type="primary"
              @click="preview(scope.row)"
              >预览</el-button
            >
            <el-button
              v-permission="pageCode + 'download'"
              type="primary"
              @click="download(scope.row)"
              >下载</el-button
            >
            <el-button
              v-permission="pageCode + 'restore'"
              type="primary"
              @click="restore(scope.row)"
              >恢复</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <PreviewDocument ref="previewDocument" />
  </Dialog>
</template>

<script lang="ts">
import ColumnsController from '@/components/abc/ColumnsController/index.vue'
import PreviewDocument from '../document/preview.vue'
import { Dialog } from '@/components/abc/Dialog'
const MODULE_CODE = 'edoc'
const ENTITY_TYPE = 'documentVersion'
export default {
  components: { Dialog, ColumnsController, PreviewDocument },
  mixins: [],
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
          prop: 'documentVersion',
          label: '文档版本',
          show: true,
          showOverflowTooltip: true,
          sortable: false
        },
        {
          prop: 'versionTag',
          label: '版本标记',
          show: true,
          showOverflowTooltip: true,
          sortable: false
        },
        {
          prop: 'uploadUserName',
          label: '创建人',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'createTime',
          label: '创建时间',
          show: true,
          showOverflowTooltip: true,
          sortable: false
        }
      ],
      queryCondition: {
        //默认值处理
      },
      tableData: [],
      visible: false,
      documentName: '',
      documentId: ''
    }
  },
  computed: {
    showCols() {
      return this.columnList.filter((item) => item.show)
    },
    tableKey() {
      const { path } = this.$route
      return `${path}/table`
    }
  },
  methods: {
    show(documentId, documentName) {
      this.documentId = documentId
      this.documentName = documentName
      this.loadData()
    },
    loadData() {
      this.api.getList(this.documentId).then((res) => {
        this.tableData = res.data
        this.visible = true
      })
    },
    preview(row) {
      this.$refs.previewDocument.showVersion(row.documentId, row.documentVersion, this.documentName)
    },
    download(row) {
      this.$api.edoc.document.downloadVersion(row.documentId, row.documentVersion)
    },
    restore(row) {
      this.$confirm('该操作将当前版本设置为最新版本，是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.$api.edoc.document.restore(row.documentId, row.documentVersion).then(() => {
            this.loadData()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    close() {
      this.visible = false
    }
  }
}
</script>
