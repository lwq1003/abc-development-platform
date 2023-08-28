<template>
  <el-button type="primary" @click="handleClick">
    流程图
    <FlowPreview ref="flowPreview" />
  </el-button>
</template>

<script>
import FlowPreview from './flowPreview.vue'
export default {
  components: { FlowPreview },
  props: {
    processDefinitionId: {
      type: String,
      required: false,
      default: ''
    },
    clickEvent: {
      type: Function,
      required: false,
      default: null
    }
  },
  data() {
    return {}
  },
  methods: {
    handleClick() {
      if (this.clickEvent) {
        this.clickEvent()
      } else {
        this.$refs.flowPreview.show(this.processDefinitionId)
      }
    }
  }
}
</script>

<style scoped>
.main-report_button {
  float: right;
  margin-left: 10px;
}
</style>
