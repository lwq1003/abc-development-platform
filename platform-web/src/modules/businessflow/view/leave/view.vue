<template>
  <Dialog title="查看" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0 auto"
    >
      <!--表单区域 -->
      <el-form-item label="单据编号" prop="billNo">
        <el-input v-model="entityData.billNo" :readonly="true" />
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker
          v-model="entityData.startTime"
          :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
          :type="$dateFormatter.getDatetimeType('SECOND')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker
          v-model="entityData.endTime"
          :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
          :type="$dateFormatter.getDatetimeType('SECOND')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="总计天数" prop="total">
        <el-input v-model="entityData.total" />
      </el-form-item>
      <el-form-item label="请假类型" prop="leaveType">
        <dictionary-select v-model="entityData.leaveType" code="LeaveType" />
      </el-form-item>
      <el-form-item label="事由" prop="reason">
        <el-input v-model="entityData.reason" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item label="部门审批人" prop="organizationApprovalName">
        <el-input v-model="entityData.organizationApprovalName" />
      </el-form-item>
      <el-form-item label="部门审批时间" prop="organizationApprovalTime">
        <el-date-picker
          v-model="entityData.organizationApprovalTime"
          :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
          :type="$dateFormatter.getDatetimeType('SECOND')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="部门审批意见" prop="organizationApprovalAdvice">
        <el-input v-model="entityData.organizationApprovalAdvice" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item label="人事审批人" prop="hrApprovalName">
        <el-input v-model="entityData.hrApprovalName" />
      </el-form-item>
      <el-form-item label="人事审批时间" prop="hrApprovalTime">
        <el-date-picker
          v-model="entityData.hrApprovalTime"
          :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
          :type="$dateFormatter.getDatetimeType('SECOND')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="人事审批意见" prop="hrApprovalAdvice">
        <el-input v-model="entityData.hrApprovalAdvice" type="textarea" rows="4" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'
const MODULE_CODE = 'businessflow'
const ENTITY_TYPE = 'leave'
export default {
  name: ENTITY_TYPE + '-view',
  components: {},
  mixins: [viewMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {}
    }
  },
  methods: {}
}
</script>

<style></style>
