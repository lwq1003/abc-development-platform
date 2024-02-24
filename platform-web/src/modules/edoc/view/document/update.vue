<template>
  <el-dialog
    title="更新文档"
    v-model="visible"
    :close-on-click-modal="false"
    append-to-body
    destroy-on-close
    width="400px"
    @close="close"
  >
    <AttachmentUploader
      ref="attachmentUploader"
      entity-type="Document"
      :entity-id="folderId"
      module-code="edoc"
      :show-success-files="true"
      :merge-chunk-api="'this.$api.edoc.document'"
      @file-complete="fileComplete"
      :single-flag="true"
      :auto-start="false"
      :server-url="serverUrl"
      :append-data="entityData"
    />

    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="80px"
      label-position="right"
      style="width: 90%; margin-top: 10px"
    >
      <!--表单区域 -->
      <el-form-item label="版本号" prop="documentVersion">
        <el-input v-model="entityData.documentVersion" />
      </el-form-item>
      <el-form-item label="版本标签">
        <el-input v-model="entityData.versionTag" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
      <el-button type="primary" @click="save">保存</el-button>
    </template>
  </el-dialog>
</template>

<script>
import AttachmentUploader from '@/modules/support/view/attachment/attachmentUploader.vue'
import qs from 'qs'
export default {
  components: { AttachmentUploader },
  data() {
    return {
      loading: false,
      visible: false,
      folderId: '',
      entityData: {
        documentVersion: '1.0.0',
        documentId: ''
      },
      rules: {
        documentVersion: [{ required: true, message: '请输入版本号', trigger: 'blur' }]
      },
      serverUrl: '/edoc/document/update'
    }
  },
  methods: {
    show(documentId) {
      this.entityData.documentId = documentId
      this.$api.edoc.document.get(documentId).then((res) => {
        this.folderId = res.data.parentId
      })
      this.$api.edoc.documentVersion.getNewestVersion(documentId).then((res) => {
        const version = res.data.documentVersion
        const versionArray = version.split('.')
        this.entityData.documentVersion =
          versionArray[0] + '.' + versionArray[1] + '.' + (parseInt(versionArray[2]) + 1)
      })

      this.visible = true
    },
    // 关闭
    close() {
      // 清理数据
      this.entityData = {
        documentVersion: '1.0.0',
        documentId: ''
      }
      this.$emit('refresh')
      this.visible = false
    },
    // 保存
    save() {
      const uploader = this.$refs.attachmentUploader.$refs.uploader
      // 验证文件是否为空
      const files = uploader.files
      if (!files || files.length === 0) {
        this.$message.warning('请选择要上传的文档')
        return
      }

      // 表单验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.serverUrl = '/edoc/document/update?' + qs.stringify(this.entityData)
          uploader.uploader.opts.target = import.meta.env.VITE_BASE_URL + this.serverUrl
          uploader.uploader.upload()
        }
      })
    },
    fileComplete() {
      this.close()
    }
  }
}
</script>

<style lang="scss"></style>
