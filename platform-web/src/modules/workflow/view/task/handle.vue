<template>
  <el-container>
    <el-header height="35" style="padding: 0">
      <el-row style="background-color: #1890ff; padding: 0">
        <el-button-group style="float: right">
          <el-button type="primary" @click="commit">提交</el-button>
          <el-button type="primary" @click="reject">驳回</el-button>
          <el-button type="primary" @click="jump">跳转</el-button>
          <el-button type="primary" @click="save">保存</el-button>
          <el-button type="primary" @click="transfer">转办</el-button>
          <el-button type="primary" @click="delegate">委派</el-button>
          <el-button v-show="signInButtonVisible" type="primary" @click="signIn">签收</el-button>
          <el-button v-show="cancelSignInButtonVisible" type="primary" @click="cancelSignIn"
            >撤销签收</el-button
          >
          <el-button type="primary" @click="viewDiagram">流程图</el-button>
          <el-button type="primary" @click="close">关闭</el-button>
        </el-button-group>
        <FlowPreview ref="flowPreview" />
        <transfer
          ref="transfer"
          :task-id="taskData.taskId"
          :process-instance-id="taskData.processInstanceId"
          :step-name="taskData.stepName"
          @success="close"
        />
        <delegate
          ref="delegate"
          :task-id="taskData.taskId"
          :process-instance-id="taskData.processInstanceId"
          :step-name="taskData.stepName"
          @success="close"
        />
      </el-row>
    </el-header>
    <el-main style="padding: 0">
      <el-container>
        <el-aside width="70%">
          <commit ref="commit" default-comment="同意" @success="close" />
          <reject ref="reject" default-comment="驳回" @success="close" />
          <jump ref="jump" default-comment="跳转" @success="close" />
          <component
            v-if="taskData.processDefinitionKey"
            :is="taskData.processDefinitionKey"
            ref="flowComponent"
          />
        </el-aside>
        <el-main width="30%" style="padding: 0">
          <history-step ref="historyStep" />
        </el-main>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
import HistoryStep from './historyStep.vue'
import Commit from './commit.vue'
import Reject from './reject.vue'
import Jump from './jump.vue'
import Transfer from './transfer.vue'
import Delegate from './delegate.vue'
import * as flowComponent from '@/modules/businessflow/view/dictionary/list'
import { flowMixin } from '@/mixin/flowMixin'

export default {
  name: 'TaskHandle',
  components: {
    HistoryStep,
    Transfer,
    Delegate,
    Commit,
    Reject,
    Jump,
    ...flowComponent
  },
  mixins: [flowMixin],
  data() {
    return {
      // 是否设置人员
      setAssigneeFlag: ''
    }
  },
  computed: {
    signInButtonVisible() {
      if (this.setAssigneeFlag === 'NO' && !this.taskData.assignee) {
        return true
      }
      return false
    },
    cancelSignInButtonVisible() {
      if (this.setAssigneeFlag === 'NO' && this.taskData.assignee) {
        return true
      }
      return false
    }
  },
  mounted() {
    // 获取任务标识
    this.taskData.taskId = this.$route.query.taskId
    this.refresh()
  },
  methods: {
    // 签收
    signIn() {
      this.$api.workflow.task.signIn(this.taskData.taskId).then(() => {
        this.refresh()
      })
    },
    // 撤销签收
    cancelSignIn() {
      this.$api.workflow.task.cancelSignIn(this.taskData.taskId).then(() => {
        this.refresh()
      })
    },
    viewDiagram() {
      this.$refs.flowPreview.show(this.taskData.processDefinitionId)
    },
    refresh() {
      // 查询任务信息
      this.$api.workflow.task.get(this.taskData.taskId).then((res) => {
        const taskInfo = res.data
        this.taskData.taskId = taskInfo.id
        this.taskData.processInstanceId = taskInfo.processInstanceId
        this.taskData.stepName = taskInfo.name
        this.taskData.processDefinitionId = taskInfo.processDefinitionId
        this.taskData.delegation = taskInfo.delegation
        this.taskData.assignee = taskInfo.assignee
        this.taskData.processDefinitionKey = taskInfo.processDefinitionKey
        // 获取环节设置
        this.$api.workflow.workflowNodeConfig
          .getNodeConfig(taskInfo.processDefinitionId, taskInfo.taskDefinitionKey)
          .then((res) => {
            if (res.data) {
              // 首环节无配置信息，所有新增一层判断
              this.setAssigneeFlag = res.data.setAssigneeFlag
            }
          })
        // 业务数据
        const businessNo = taskInfo.businessNo
        this.$nextTick(function () {
          // 业务单据
          this.$refs.flowComponent.modify(
            businessNo,
            this.taskData.processDefinitionId,
            taskInfo.taskDefinitionKey
          )
        })

        // 历史
        this.$refs.historyStep.view(this.taskData.processInstanceId)
      })
    }
  }
}
</script>

<style scoped></style>
