<template>
  <el-container>
    <el-header height="35" style="padding: 0">
      <el-row style="background-color: #1890ff; padding: 0">
        <span class="title">{{ taskData.processDefinitionName }}</span>
        <el-button-group style="position: absolute; right: 0">
          <!-- <view-diagram-button :process-definition-id="taskData.processDefinitionId" /> -->
          <el-button type="primary" @click="close">关闭</el-button>
        </el-button-group>
      </el-row>
    </el-header>
    <el-main style="padding: 0">
      <el-container>
        <el-aside width="70%">
          <component :is="taskData.processDefinitionKey" ref="flowComponent" />
        </el-aside>
        <el-main width="30%" style="padding: 0">
          <history-step ref="historyStep" />
        </el-main>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
import HistoryStep from '@/modules/workflow/view/task/historyStep.vue'
import * as flowComponent from '@/modules/businessflow/view/dictionary/list'
// import ViewDiagramButton from '@/components/popsoft/FlowModel/ViewDiagramButton'
import { flowMixin } from '@/mixin/flowMixin'
export default {
  name: 'FlowView',
  components: { HistoryStep, ...flowComponent },
  mixins: [flowMixin],
  data() {
    return {}
  },
  mounted() {
    console.log(this.$route.query.processInstanceId)
    // 获取流程实例标识
    this.taskData.processInstanceId = this.$route.query.processInstanceId
    // 查询流程实例信息
    this.$api.workflow.processInstance.get(this.taskData.processInstanceId).then((res) => {
      this.taskData.processDefinitionName = res.data.processDefinitionName
      this.taskData.processDefinitionId = res.data.processDefinitionId
      this.taskData.processDefinitionKey = res.data.processDefinitionKey
      // 业务单据号
      const businessNo = res.data.businessNo
      this.$nextTick(function () {
        // 业务单据
        this.$refs.flowComponent.view(businessNo, this.taskData.processDefinitionId)
      })

      // 历史
      this.$refs.historyStep.view(this.taskData.processInstanceId)
    })
  },
  methods: {}
}
</script>

<style scoped>
.title {
  float: left;
  color: #fff;
  margin: 10px 20px;
}
</style>
