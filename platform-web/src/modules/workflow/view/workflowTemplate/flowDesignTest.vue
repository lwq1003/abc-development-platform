<template>
  <div>
    <div class="fd-nav">
      <div class="fd-nav-left">
        <!-- TODO 显示流程模板名称 -->
        <!-- <div class="fd-nav-title">{{ workFlowDef.name }}</div> -->
      </div>
      <div class="fd-nav-right">
        <el-button type="primary" @click="save">保存</el-button>
      </div>
    </div>
    <div class="fd-nav-content">
      <section class="dingflow-design">
        <div class="zoom">
          <div class="zoom-out" :class="nowVal == 50 && 'disabled'" @click="zoomSize(1)"></div>
          <span>{{ nowVal }}%</span>
          <div class="zoom-in" :class="nowVal == 300 && 'disabled'" @click="zoomSize(2)"></div>
        </div>
        <div class="box-scale" :style="`transform: scale(${nowVal / 100});`">
          <nodeWrap v-model:nodeConfig="nodeConfig" v-model:flowPermission="flowPermission" />
          <div class="end-node">
            <div class="end-node-circle"></div>
            <div class="end-node-text">流程结束</div>
          </div>
        </div>
      </section>
    </div>
    <errorDialog v-model:visible="tipVisible" :list="tipList" />
    <promoterDrawer />
    <approverDrawer :directorMaxLevel="directorMaxLevel" />
    <copyerDrawer />
    <conditionDrawer />
  </div>
</template>

<script>
import errorDialog from './model/components/dialog/errorDialog.vue'
import promoterDrawer from './model/components/drawer/promoterDrawer.vue'
import approverDrawer from './model/components/drawer/approverDrawer.vue'
import copyerDrawer from './model/components/drawer/copyerDrawer.vue'
import conditionDrawer from './model/components/drawer/conditionDrawer.vue'
import nodeWrap from './model/components/nodeWrap.vue'

export default {
  components: {
    errorDialog,
    promoterDrawer,
    approverDrawer,
    copyerDrawer,
    conditionDrawer,
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
      // 当前缩放值
      nowVal: 100,
      // 模型配置
      nodeConfig: {},
      flowPermission: [],
      tipVisible: false,
      directorMaxLevel: 0,
      tipList: [],
      // 模拟数据
      flowData: {
        code: '200',
        msg: 'success',
        data: {
          tableId: 1,
          workFlowDef: {
            name: '合同审批'
          },
          directorMaxLevel: 4,
          flowPermission: [],
          nodeConfig: {
            nodeName: '发起人',
            type: 0,
            priorityLevel: '',
            settype: '',
            selectMode: '',
            selectRange: '',
            directorLevel: '',
            examineMode: '',
            noHanderAction: '',
            examineEndDirectorLevel: '',
            ccSelfSelectFlag: '',
            conditionList: [],
            nodeUserList: [],
            childNode: {
              nodeName: '审核人',
              error: false,
              type: 1,
              settype: 2,
              selectMode: 0,
              selectRange: 0,
              directorLevel: 1,
              examineMode: 1,
              noHanderAction: 2,
              examineEndDirectorLevel: 0,
              childNode: {
                nodeName: '路由',
                type: 4,
                priorityLevel: 1,
                settype: 1,
                selectMode: 0,
                selectRange: 0,
                directorLevel: 1,
                examineMode: 1,
                noHanderAction: 2,
                examineEndDirectorLevel: 1,
                ccSelfSelectFlag: 1,
                conditionList: [],
                nodeUserList: [],
                childNode: {
                  nodeName: '抄送人',
                  type: 2,
                  ccSelfSelectFlag: 1,
                  childNode: null,
                  nodeUserList: [],
                  error: false
                },
                conditionNodes: [
                  {
                    nodeName: '条件1',
                    type: 3,
                    priorityLevel: 1,
                    settype: 1,
                    selectMode: 0,
                    selectRange: 0,
                    directorLevel: 1,
                    examineMode: 1,
                    noHanderAction: 2,
                    examineEndDirectorLevel: 1,
                    ccSelfSelectFlag: 1,
                    conditionList: [
                      {
                        columnId: 0,
                        type: 1,
                        conditionEn: '',
                        conditionCn: '',
                        optType: '',
                        zdy1: '',
                        zdy2: '',
                        opt1: '',
                        opt2: '',
                        columnDbname: '',
                        columnType: '',
                        showType: '',
                        showName: '',
                        fixedDownBoxValue: ''
                      }
                    ],
                    nodeUserList: [
                      {
                        targetId: 85,
                        type: 1,
                        name: '天旭'
                      }
                    ],
                    childNode: {
                      nodeName: '审核人',
                      type: 1,
                      priorityLevel: 1,
                      settype: 1,
                      selectMode: 0,
                      selectRange: 0,
                      directorLevel: 1,
                      examineMode: 1,
                      noHanderAction: 2,
                      examineEndDirectorLevel: 1,
                      ccSelfSelectFlag: 1,
                      conditionList: [],
                      nodeUserList: [
                        {
                          targetId: 2515744,
                          type: 1,
                          name: '哈哈哈哈'
                        }
                      ],
                      childNode: null,
                      conditionNodes: [],
                      error: false
                    },
                    conditionNodes: [],
                    error: false
                  },
                  {
                    nodeName: '条件2',
                    type: 3,
                    priorityLevel: 2,
                    settype: 1,
                    selectMode: 0,
                    selectRange: 0,
                    directorLevel: 1,
                    examineMode: 1,
                    noHanderAction: 2,
                    examineEndDirectorLevel: 1,
                    ccSelfSelectFlag: 1,
                    conditionList: [],
                    nodeUserList: [],
                    childNode: null,
                    conditionNodes: [],
                    error: false
                  }
                ]
              },
              nodeUserList: []
            },
            conditionNodes: []
          }
        }
      }
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      // 初始化流程
      this.nodeConfig = this.flowData.data.nodeConfig
      this.visible = true
    },
    save() {
      // this.$emit('update:modelValue', JSON.stringify(this.layout))
      this.visible = false
    },
    // 清空配置
    clear() {
      this.$confirm('此操作将重置流程配置为初始状态, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          // this.$emit('update:modelValue', '[]')
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    zoomSize(type) {
      if (type == 1) {
        if (this.nowVal == 50) {
          return
        }
        this.nowVal -= 10
      } else {
        if (this.nowVal == 300) {
          return
        }
        this.nowVal += 10
      }
    }
  }
}
</script>

<style scoped>
@import './model/css/workflow.css';
.error-modal-list {
  width: 455px;
}
</style>
