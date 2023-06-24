<template>
  <el-table :data="entityData" highlight-current-row border>
    <el-table-column type="index" label="序号" sortable width="55" />
    <el-table-column prop="name" label="名称" show-overflow-tooltip />
    <el-table-column prop="size" label="大小" width="80" />

    <el-table-column prop="createTime" label="时间" width="100" />
    <el-table-column fixed="right" label="操作" width="170">
      <template #default="scope">
        <el-button
          type="primary"
          icon="Download"
          class="header-search_button"
          size="small"
          @click="download(scope.row)"
          >下载</el-button
        >
        <el-button
          type="primary"
          icon="Delete"
          class="header-search_button"
          size="small"
          @click="remove(scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  components: {},
  props: {
    entityId: {
      type: String,
      default: '',
      required: true
    },
    showDelete: {
      type: Boolean,
      default: true,
      required: false
    }
  },
  data() {
    return {
      entityData: []
    }
  },
  watch: {
    entityId: function () {
      this.list()
    }
  },
  mounted: function () {
    this.list()
  },
  methods: {
    list() {
      if (this.entityId) {
        // 只有当entityId不为空时才发起查询
        this.$api.support.attachment.list({ entityId: this.entityId }).then((res) => {
          this.entityData = res.data
        })
      }
    },
    remove(row) {
      this.$confirm('是否删除该附件?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.$api.support.attachment.remove(row.id).finally(() => {
            this.list()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    download(row) {
      this.$api.support.attachment.download(row.id, row.name)
    }
  }
}
</script>
