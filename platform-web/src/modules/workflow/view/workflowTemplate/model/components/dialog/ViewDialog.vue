<template>
  <el-dialog title="查看" v-model="visible" width="500px">
    <el-input v-model="modelJson" readonly type="textarea" :rows="20" />
    <template #footer>
      <el-button type="primary" @click="copy" :data-clipboard-text="modelJson" class="clipboardObj"
        >复制</el-button
      >
      <el-button @click="close">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script>
import Clipboard from 'clipboard'
export default {
  data() {
    return {
      modelJson: '',
      visible: false
    }
  },
  methods: {
    show(value) {
      this.modelJson = JSON.stringify(JSON.parse(value), null, '\t')
      this.visible = true
    },
    close() {
      this.visible = false
    },
    copy() {
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
    }
  }
}
</script>

<style></style>
