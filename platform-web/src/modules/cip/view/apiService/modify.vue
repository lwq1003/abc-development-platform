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
      <el-form-item label="分类" prop="category">
        <dictionary-select v-model="entityData.category" code="ApiServiceCategory" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item label="处理器" prop="handler">
        <el-input v-model="entityData.handler" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <dictionary-radio-group v-model="entityData.status" code="Status" />
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
const MODULE_CODE = 'cip'
const ENTITY_TYPE = 'apiService'
export default {
  name: ENTITY_TYPE + '-modify',
  components: {},
  mixins: [modifyMixin],
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
        name: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }],
        code: [{ required: true, message: '【编码】不能为空', trigger: 'blur' }],
        handler: [{ required: true, message: '【处理器】不能为空', trigger: 'blur' }],
        status: [{ required: true, message: '【状态】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {}
}
</script>

<style></style>
