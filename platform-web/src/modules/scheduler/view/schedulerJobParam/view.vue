<template>
  <Dialog title="查看" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="调度任务" prop="schedulerJob">
        <SchedulerJobReference
          v-model="entityData.schedulerJob"
          :schedulerJob-param="schedulerJobParam"
        />
      </el-form-item>
      <el-form-item label="参数名称" prop="paramName">
        <el-input v-model="entityData.paramName" />
      </el-form-item>
      <el-form-item label="参数编码" prop="paramCode">
        <el-input v-model="entityData.paramCode" />
      </el-form-item>
      <el-form-item label="参数值" prop="paramValue">
        <el-input v-model="entityData.paramValue" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'
import SchedulerJobReference from '@/modules/scheduler/view/schedulerJob/reference.vue'
const MODULE_CODE = 'scheduler'
const ENTITY_TYPE = 'schedulerJobParam'
export default {
  name: ENTITY_TYPE + '-view',
  components: {
    SchedulerJobReference
  },
  mixins: [viewMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 调度任务组件参数，用于传递数据
      schedulerJobParam: {},
      entityData: {}
    }
  },
  methods: {}
}
</script>

<style></style>
