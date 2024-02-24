<template>
  <el-dialog
    title="上传文件"
    v-model="visible"
    :close-on-click-modal="false"
    append-to-body
    destroy-on-close
    @close="close"
  >
    <AttachmentUploader
      entity-type="Document"
      :entity-id="folderId"
      module-code="edoc"
      :show-success-files="true"
      :server-url="'/edoc/document/upload'"
      :merge-chunk-api="'this.$api.edoc.document'"
      @file-complete="fileComplete"
    />
  </el-dialog>
</template>

<script>
import AttachmentUploader from '@/modules/support/view/attachment/attachmentUploader.vue'

export default {
  components: { AttachmentUploader },
  data() {
    return {
      visible: false,
      folderId: ''
    }
  },
  methods: {
    show(folderId) {
      this.folderId = folderId
      this.visible = true
    },
    fileComplete() {
      this.$emit('refresh')
      this.close()
    },
    // 关闭
    close() {
      this.$emit('refresh')
      this.visible = false
    }
  }
}
</script>

<style lang="scss"></style>
