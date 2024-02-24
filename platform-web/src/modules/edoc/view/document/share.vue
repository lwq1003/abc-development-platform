<template>
  <el-dialog
    title="分享文档"
    v-model="visible"
    :close-on-click-modal="false"
    append-to-body
    destroy-on-close
    width="400px"
    @close="close"
  >
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin-top: 10px"
    >
      <!--表单区域 -->
      <el-form-item label="有效时长(小时)" prop="validHours">
        <el-input v-model="entityData.validHours" />
      </el-form-item>
      <el-form-item label="共享地址">
        <el-input v-model="shareUrl" readonly />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
      <el-button type="primary" @click="copy" :data-clipboard-text="shareUrl" class="clipboardObj"
        >复制</el-button
      >
    </template>
  </el-dialog>
</template>

<script>
import Clipboard from 'clipboard'
export default {
  data() {
    return {
      loading: false,
      visible: false,
      folderId: '',
      entityData: {
        documentId: '',
        validHours: 24
      },
      rules: {
        validHours: [{ required: true, message: '请输入文档共享时长', trigger: 'blur' }]
      },
      shareUrl: '',
      documentName: ''
    }
  },
  methods: {
    show(documentId, documentName) {
      this.documentName = documentName
      this.entityData.documentId = documentId
      this.generateShareUrl()
      this.visible = true
    },
    // 关闭
    close() {
      // 清理数据
      this.entityData = {
        documentId: '',
        validHours: 24
      }
      this.visible = false
    },
    generateShareUrl() {
      if (!this.entityData.validHours) {
        this.$message.warning('请输入文档共享时长')
        return
      }
      return this.$api.edoc.document
        .getTokenWithTime(this.entityData.documentId, this.entityData.validHours)
        .then((res) => {
          const token = res.data
          // 拼接文档访问路径
          let previewUrl = import.meta.env.VITE_BASE_URL + '/edoc/document/getStream'
          previewUrl = previewUrl + '?fullfilename=' + this.documentName + '&token=' + token
          // 生成访问地址
          this.shareUrl =
            import.meta.env.VITE_DOCUMENT_PREVIEW_URL +
            encodeURIComponent(this.$base64Util.encode(previewUrl))
        })
    },

    // 复制
    copy() {
      // 此处需要重新调用一遍生成，主要在于用户修改共享时长后立即点击复制按钮，此时令牌尚在请求处理中，会造成不一致
      this.generateShareUrl().then(() => {
        // 复制
        let clipboard = new Clipboard('.clipboardObj')
        clipboard.on('success', (e) => {
          this.$message({
            type: 'info',
            message: '复制成功'
          })
          // 释放内存
          clipboard.destroy()
        })
        clipboard.on('error', (e) => {
          // 不支持复制
          this.$message({
            type: 'warning',
            message: '复制失败'
          })
          // 释放内存
          clipboard.destroy()
        })
      })
    }
  }
}
</script>

<style lang="scss"></style>
