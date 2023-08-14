<template>
  <Dialog title="流程建模" v-model="visible" fullscreen>
    <div>
      <div class="fd-nav">
        <div class="fd-nav-left">
          <div class="fd-nav-title">{{ workflowTemplateName }}</div>
        </div>
        <div class="fd-nav-right">
          <el-button @click="close">关闭</el-button>
        </div>
      </div>
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
  </Dialog>
</template>

<script>
import { Dialog } from '@/components/abc/Dialog'

import nodeWrap from './nodeWrap.vue'
import HandleNodeConfig from './drawer/HandleNodeConfig.vue'
import ConditionNodeConfig from './drawer/ConditionNodeConfig.vue'
export default {
  components: {
    Dialog,
    nodeWrap
  },
  props: {
    modelValue: {
      type: String
    },
    // 流程模板名称
    workflowTemplateName: {
      type: String
    }
  },
  data() {
    return {
      visible: false,
      // 缩放值
      scaleValue: 100,
      // 节点数据
      nodeData: {},
      // 错误提示可见性
      tipVisible: false,
      // 错误列表
      tipList: [],
      // 默认节点数据
      defaultNodeData: {
        name: '填报',
        id: '1',
        type: 'ROOT',
        config: {},
        branchList: [],
        child: {}
      }
    }
  },
  methods: {
    show() {
      // TODO 加载模型数据

      this.visible = true
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
    },
    close() {
      this.visible = false
    }
  }
}
</script>

<style scoped>
@import '../css/workflow.css';
.error-modal-list {
  width: 455px;
}
</style>
