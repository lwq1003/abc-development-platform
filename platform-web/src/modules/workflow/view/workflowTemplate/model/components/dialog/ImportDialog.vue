<template>
  <el-dialog title="导入" v-model="visible" width="500px">
    <el-input v-model="modelJson" type="textarea" :rows="20" />
    <template #footer>
      <el-button type="primary" @click="save">保存</el-button>
      <el-button type="primary" @click="format">格式化</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      modelJson: '',
      visible: false
    }
  },
  methods: {
    show() {
      this.visible = true
    },
    close() {
      this.visible = false
    },
    format() {
      this.modelJson = JSON.stringify(JSON.parse(this.modelJson), null, '\t')
    },
    save() {
      this.$emit('update', this.modelJson)
      this.visible = false
    }
  }
}
</script>

<style></style>
