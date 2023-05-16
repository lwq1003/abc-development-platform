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
      <el-form-item label="应用编码" prop="appCode">
        <el-input v-model="entityData.appCode" />
      </el-form-item>
      <el-form-item label="服务编码" prop="serviceCode">
        <el-input v-model="entityData.serviceCode" />
      </el-form-item>
      <el-form-item label="请求时间" prop="requestTime">
        <el-date-picker
          v-model="entityData.requestTime"
          :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
          :type="$dateFormatter.getDatetimeType('SECOND')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="收到时间" prop="receiveTime">
        <el-date-picker
          v-model="entityData.receiveTime"
          :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
          :type="$dateFormatter.getDatetimeType('SECOND')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="请求业务数据" prop="requestBusinessData">
        <el-input v-model="entityData.requestBusinessData" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item label="执行结果" prop="executeResult">
        <el-input v-model="entityData.executeResult" />
      </el-form-item>
      <el-form-item label="错误编码" prop="errorCode">
        <el-input v-model="entityData.errorCode" />
      </el-form-item>
      <el-form-item label="错误信息" prop="errorMessage">
        <el-input v-model="entityData.errorMessage" />
      </el-form-item>
      <el-form-item label="响应时间" prop="responseTime">
        <el-date-picker
          v-model="entityData.responseTime"
          :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
          :type="$dateFormatter.getDatetimeType('SECOND')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="响应业务数据" prop="responseBusinessData">
        <el-input v-model="entityData.responseBusinessData" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item label="执行耗时ms" prop="executionTime">
        <el-input v-model="entityData.executionTime" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'
const MODULE_CODE = 'cip'
const ENTITY_TYPE = 'apiServiceLog'
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
