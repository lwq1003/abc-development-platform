<template>
  <Dialog title="新增" v-model="visible" width="40%">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0 auto"
    >
      <!--表单区域 -->
      <el-form-item label="参数名称" prop="paramName">
        <el-input v-model="entityData.paramName" />
      </el-form-item>
      <el-form-item label="参数编码" prop="paramKey">
        <el-input v-model="entityData.paramKey" />
      </el-form-item>
      <el-form-item label="参数值" prop="paramValue">
        <el-input v-model="entityData.paramValue" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="save" v-permission="pageCode + 'add'">保存</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { addMixin } from '@/mixin/addMixin.js'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'param'
export default {
  name: ENTITY_TYPE + '-add',
  components: {},
  mixins: [addMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      rules: {
        //前端验证规则
        paramName: [{ required: true, message: '【参数名称】不能为空', trigger: 'blur' }],
        paramKey: [{ required: true, message: '【参数编码】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {}
}
</script>

<style></style>
