<template>
  <el-dialog title="预览" v-model="visible" append-to-body fullscreen @close="visible = false">
    <iframe
      id="iframeId"
      :src="url"
      frameborder="0"
      height="800px"
      style="width: 98%; margin-top: 10px"
      scrolling="auto"
    ></iframe>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      url: '',
      visible: false
    }
  },
  methods: {
    show(id, name) {
      this.$api.edoc.document.getToken(id).then((res) => {
        // 获取访问令牌
        const token = res.data
        // 拼接文档访问路径
        let previewUrl = import.meta.env.VITE_BASE_URL + '/edoc/document/getStream'
        previewUrl = previewUrl + '?fullfilename=' + name + '&token=' + token
        // 调用预览服务
        this.url =
          import.meta.env.VITE_DOCUMENT_PREVIEW_URL +
          encodeURIComponent(this.$base64Util.encode(previewUrl))
        this.visible = true
      })
    },
    showVersion(id, version, name) {
      this.$api.edoc.document.getToken(id).then((res) => {
        // 获取访问令牌
        const token = res.data
        // 拼接文档访问路径
        let previewUrl = import.meta.env.VITE_BASE_URL + '/edoc/document/getStreamVersion'
        previewUrl =
          previewUrl + '?fullfilename=' + name + '&token=' + token + '&version=' + version
        // 调用预览服务
        this.url =
          import.meta.env.VITE_DOCUMENT_PREVIEW_URL +
          encodeURIComponent(this.$base64Util.encode(previewUrl))
        this.visible = true
      })
    }
  }
}
</script>

<style lang="scss"></style>
