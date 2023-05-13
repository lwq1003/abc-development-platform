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
      <el-form-item label="请求标识" prop="requestId">
        <el-input v-model="entityData.requestId" />
      </el-form-item>
      <el-form-item label="请求应用编码" prop="requestAppCode">
        <el-input v-model="entityData.requestAppCode" />
      </el-form-item>
      <el-form-item label="请求主题编码" prop="requestTopicCode">
        <el-input v-model="entityData.requestTopicCode" />
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
      <el-form-item label="请求数据" prop="requestData">
        <el-input v-model="entityData.requestData" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item label="响应标识" prop="responseId">
        <el-input v-model="entityData.responseId" />
      </el-form-item>
      <el-form-item label="响应应用编码" prop="responseAppCode">
        <el-input v-model="entityData.responseAppCode" />
      </el-form-item>
      <el-form-item label="响应主题编码" prop="responseTopicCode">
        <el-input v-model="entityData.responseTopicCode" />
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
      <el-form-item label="响应数据" prop="responseData">
        <el-input v-model="entityData.responseData" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item label="响应结果" prop="responseResult">
        <el-input v-model="entityData.responseResult" />
      </el-form-item>
      <el-form-item label="错误编码" prop="errorCode">
        <el-input v-model="entityData.errorCode" />
      </el-form-item>
      <el-form-item label="错误信息" prop="errorMessage">
        <el-input v-model="entityData.errorMessage" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <dictionary-select v-model="entityData.status" code="MessageStatus" />
      </el-form-item>
      <el-form-item label="发送次数" prop="sendCount">
        <el-input v-model="entityData.sendCount" />
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
const ENTITY_TYPE = 'messageLog'
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
