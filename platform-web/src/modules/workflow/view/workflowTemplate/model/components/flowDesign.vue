<template>
  <div class="w-full">
    <el-input disabled style="width: 152px" />
    <el-button-group>
      <el-button icon="grid" @click="init" style="border-left-width: 0; padding: 10px" />
      <el-button icon="delete" @click="clear" style="border-left-width: 0; padding: 10px" />
    </el-button-group>
    <Dialog title="流程建模" v-model="visible" fullscreen>
      <div>
        <div class="fd-nav">
          <div class="fd-nav-left">
            <div class="fd-nav-title">{{ workflowTemplateName }}</div>
          </div>
          <div class="fd-nav-right">
            <el-button type="primary" @click="save">保存</el-button>
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
        <errorDialog v-model:visible="tipVisible" :list="tipList" />

        <HandleNodeConfig />
        <ConditionNodeConfig />
      </div>
    </Dialog>
  </div>
</template>

<script>
import { Dialog } from '@/components/abc/Dialog'
import errorDialog from './dialog/errorDialog.vue'
import nodeWrap from './nodeWrap.vue'
import HandleNodeConfig from './drawer/HandleNodeConfig.vue'
import ConditionNodeConfig from './drawer/ConditionNodeConfig.vue'
export default {
  components: {
    Dialog,
    errorDialog,
    nodeWrap,
    HandleNodeConfig,
    ConditionNodeConfig
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
    init() {
      if (this.modelValue && this.modelValue != '{}') {
        // 绑定数据不为空，则使用绑定数据初始化
        this.nodeData = JSON.parse(this.modelValue)
      } else {
        // 否则，加载默认配置
        this.nodeData = this.defaultNodeData
      }

      this.visible = true
    },
    save() {
      this.$emit('update:modelValue', JSON.stringify(this.nodeData))
      this.visible = false
    },
    // 清空配置
    clear() {
      this.$confirm('此操作将重置流程配置为初始状态, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.$emit('update:modelValue', '{}')
        })
        .catch(() => {
          this.$message.info('已取消')
        })
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
      this.$confirm('未保存的修改将丢失, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.visible = false
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    }
  }
}
</script>

<style>
@import '../css/workflow.css';
.error-modal-list {
  width: 455px;
}
</style>
