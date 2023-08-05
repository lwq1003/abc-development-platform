<template>
  <el-card>
    <template #header>
      <span>历史</span>
    </template>
    <el-timeline>
      <el-timeline-item v-for="(step, index) in steps" :key="index" type="success" size="large">
        <div style="font-weight: bold">{{ step.nodeName }}</div>
        <div>{{ step.assigneeName }}</div>
        <div>{{ step.commitTypeName }}</div>
        <div :title="step.comment">{{ abbreviate(step.comment, 20) }}</div>
        <div>{{ $dateFormatter.formatUTCTime(step.commitTime) }}</div>
      </el-timeline-item>
    </el-timeline>
  </el-card>
</template>
<script>
import { abbreviate } from '@/utils'
export default {
  data() {
    return {
      // 历史信息
      steps: [],
      queryParams: {
        processInstanceId: '',
        sortInfo: {
          sort_field: 'commitTime',
          sort_sortType: 'ascending'
        }
      }
    }
  },
  methods: {
    abbreviate,
    view(processInstanceId) {
      this.queryParams.processInstanceId = processInstanceId
      this.$api.workflow.workflowComment.list(this.queryParams).then((res) => {
        this.steps = res.data
      })
    }
  }
}
</script>

<style scoped></style>
