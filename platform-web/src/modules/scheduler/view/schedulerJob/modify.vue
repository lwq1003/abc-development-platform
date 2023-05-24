<template>
  <Dialog title="修改" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="任务" prop="job">
        <JobReference v-model="entityData.job" :job-param="jobParam" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="调度触发" prop="cronExpression">
        <el-input v-model="entityData.cronExpression" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <dictionary-radio-group v-model="entityData.status" code="JobStatus" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="entityData.remark" type="textarea" rows="4" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="save" v-permission="pageCode + 'modify'">保存</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { modifyMixin } from '@/mixin/modifyMixin.js'
import JobReference from '@/modules/scheduler/view/job/reference.vue'
const MODULE_CODE = 'scheduler'
const ENTITY_TYPE = 'schedulerJob'
export default {
  name: ENTITY_TYPE + '-modify',
  components: {
    JobReference
  },
  mixins: [modifyMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      // 任务组件参数，用于传递数据
      jobParam: {},
      rules: {
        //前端验证规则
        job: [{ required: true, message: '【任务】不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }],
        cronExpression: [{ required: true, message: '【调度触发】不能为空', trigger: 'blur' }],
        status: [{ required: true, message: '【状态】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {}
}
</script>

<style></style>
