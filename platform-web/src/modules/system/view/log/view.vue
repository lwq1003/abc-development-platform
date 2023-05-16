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
      <el-form-item label="内容" prop="content">
        <el-input v-model="entityData.content" />
      </el-form-item>
      <el-form-item label="日志类型" prop="logType">
        <dictionary-select v-model="entityData.logType" code="LogType" />
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
      <el-form-item label="请求参数" prop="requestParam">
        <el-input v-model="entityData.requestParam" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item label="请求路径" prop="requestPath">
        <el-input v-model="entityData.requestPath" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item label="请求方法" prop="requestMethod">
        <el-input v-model="entityData.requestMethod" />
      </el-form-item>
      <el-form-item label="操作人标识" prop="operatorId">
        <el-input v-model="entityData.operatorId" />
      </el-form-item>
      <el-form-item label="操作人账号" prop="operatorAccount">
        <el-input v-model="entityData.operatorAccount" />
      </el-form-item>
      <el-form-item label="操作人姓名" prop="operatorName">
        <el-input v-model="entityData.operatorName" />
      </el-form-item>
      <el-form-item label="操作人ip" prop="operatorIp">
        <el-input v-model="entityData.operatorIp" />
      </el-form-item>
      <el-form-item label="响应结果" prop="responseCode">
        <el-input v-model="entityData.responseCode" />
      </el-form-item>
      <el-form-item label="响应数据" prop="responseData">
        <el-input v-model="entityData.responseData" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item label="执行耗时ms" prop="timeConsuming">
        <el-input v-model="entityData.timeConsuming" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'log'
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
