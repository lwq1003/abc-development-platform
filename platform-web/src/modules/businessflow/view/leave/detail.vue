<template>
  <div>
    <basic-info :entity-data="entityData" />
    <el-card>
      <template #header>
        <span>申请信息</span>
      </template>

      <el-form
        ref="form"
        :model="entityData"
        :rules="rules"
        label-width="80px"
        label-position="right"
        :disabled="permissionConfigData.applyArea == 'READONLY'"
      >
        <el-row>
          <el-col :span="12">
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
          </el-col>
          <el-col :span="12">
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
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="总计天数" prop="total">
              <el-input v-model="entityData.total" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请假类型" prop="leaveType">
              <dictionary-select v-model="entityData.leaveType" code="LeaveType" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="原因" prop="reason">
              <el-input v-model="entityData.reason" type="textarea" :rows="3" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <el-card v-show="permissionConfigData.organizationApproval != 'INVISIBLE'">
      <template #header>
        <span>部门审批</span>
      </template>

      <el-form
        ref="form"
        :model="entityData"
        :rules="rules"
        label-width="80px"
        label-position="right"
        :disabled="permissionConfigData.organizationApproval == 'READONLY'"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="审批人" prop="organizationApprovalName">
              <el-input v-model="entityData.organizationApprovalName" :readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批时间" prop="organizationApprovalTime">
              <el-input v-model="entityData.organizationApprovalTime" :readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="审批意见" prop="organizationApprovalAdvice">
              <el-input
                v-model="entityData.organizationApprovalAdvice"
                :readonly="readonly"
                type="textarea"
                :rows="3"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <el-card v-show="permissionConfigData.hrApproval != 'INVISIBLE'">
      <template #header>
        <span>人事审批</span>
      </template>
      <el-form
        ref="form"
        :model="entityData"
        :rules="rules"
        label-width="80px"
        label-position="right"
        :disabled="permissionConfigData.hrApproval == 'READONLY'"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item label="审批人" prop="hrApprovalName">
              <el-input v-model="entityData.hrApprovalName" :readonly="readonly" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="审批时间" prop="hrApprovalTime">
              <el-input v-model="entityData.hrApprovalTime" :readonly="readonly" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="审批意见" prop="hrApprovalAdvice">
              <el-input
                v-model="entityData.hrApprovalAdvice"
                :readonly="readonly"
                type="textarea"
                :rows="3"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { flowMixin } from '@/mixin/flowMixin'
const MODULE_CODE = 'businessflow'
const ENTITY_TYPE = 'leave'
export default {
  name: ENTITY_TYPE,
  mixins: [flowMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      rules: {
        startTime: [{ required: true, message: '【开始时间】不能为空', trigger: 'blur' }],
        endTime: [{ required: true, message: '【结束时间】不能为空', trigger: 'blur' }],
        total: [{ required: true, message: '【总计天数】不能为空', trigger: 'blur' }],
        leaveType: [{ required: true, message: '【请假类型】不能为空', trigger: 'blur' }],
        reason: [{ required: true, message: '【原因】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {}
}
</script>

<style scoped></style>
