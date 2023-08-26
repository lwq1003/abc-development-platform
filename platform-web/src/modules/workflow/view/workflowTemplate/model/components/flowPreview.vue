<template>
  <el-dialog title="流程图" v-model="visible" fullscreen>
    <div>
      <div class="fd-nav-content">
        <section class="dingflow-design">
          <div class="zoom">
            <div
              class="zoom-out"
              :class="scaleValue == 50 && 'disabled'"
              @click="zoomSize(1)"
            ></div>
            <span>{{ scaleValue }}%</span>
            <div
              class="zoom-in"
              :class="scaleValue == 300 && 'disabled'"
              @click="zoomSize(2)"
            ></div>
          </div>
          <div class="box-scale" :style="`transform: scale(${scaleValue / 100});`">
            <nodeWrap v-model="nodeData" />
            <div class="end-node">
              <div class="end-node-circle"></div>
              <div class="end-node-text">流程结束</div>
            </div>
          </div>
        </section>
      </div>
    </div>
    <template #footer>
      <el-button type="primary" @click="close">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script>
import nodeWrap from './nodeWrap.vue'

export default {
  components: {
    nodeWrap
  },
  props: {
    modelValue: {
      type: String
    }
  },
  data() {
    return {
      visible: false,
      // 节点数据
      nodeData: {},
      // 缩放值
      scaleValue: 100
    }
  },
  methods: {
    show(processDefinitionId) {
      this.$api.workflow.workflowTemplate
        .getModelByProcessDefinitionId(processDefinitionId)
        .then((res) => {
          this.nodeData = JSON.parse(res.data)
        })

      this.visible = true
    },
    close() {
      this.visible = false
    },
    zoomSize(type) {
      if (type == 1) {
        if (this.scaleValue == 50) {
          return
        }
        this.scaleValue -= 10
      } else {
        if (this.scaleValue == 300) {
          return
        }
        this.scaleValue += 10
      }
    }
  }
}
</script>

<style scoped>
@import '../css/workflow.css';
</style>
