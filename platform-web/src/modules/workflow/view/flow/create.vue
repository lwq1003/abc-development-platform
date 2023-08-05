<template>
  <el-container>
    <el-header height="35" style="padding: 0">
      <el-row style="background-color: #1890ff; padding: 0">
        <span class="title">{{ taskData.processDefinitionName }}</span>
        <el-button-group style="position: absolute; right: 0">
          <el-button v-show="taskData.isSaved" type="primary" @click="commit">提交</el-button>
          <el-button type="primary" @click="save">保存</el-button>
          <!-- <view-diagram-button :process-definition-id="taskData.processDefinitionId" /> -->
          <el-button type="primary" @click="close">关闭</el-button>
        </el-button-group>
      </el-row>
    </el-header>
    <el-main style="padding: 0">
      <commit ref="commit" :task-id="taskData.taskId" default-comment="发起申请" @success="close" />
      <component :is="taskData.processDefinitionKey" ref="flowComponent" @save="saved" />
    </el-main>
  </el-container>
</template>

<script>
import Commit from '@/modules/workflow/view/task/commit.vue'
import * as flowComponent from '@/modules/businessflow/view/dictionary/list'
import { flowMixin } from '@/mixin/flowMixin'
export default {
  name: 'FlowCreate',
  components: { ...flowComponent, Commit },
  mixins: [flowMixin],
  data() {
    return {}
  },
  mounted() {
    this.taskData.processDefinitionKey = this.$route.query.processDefinitionKey
    this.taskData.processDefinitionName = this.$route.query.processDefinitionName
    this.taskData.processDefinitionId = this.$route.query.processDefinitionId
    this.$nextTick(function () {
      this.$refs.flowComponent.add(this.taskData.processDefinitionId)
    })
  },
  methods: {
    saved(processInstanceId) {
      this.taskData.isSaved = true
      this.taskData.processInstanceId = processInstanceId
    }
  }
}
</script>

<style scoped>
.title {
  float: left;
  color: #fff;
  margin: 10px 20px;
}
</style>
