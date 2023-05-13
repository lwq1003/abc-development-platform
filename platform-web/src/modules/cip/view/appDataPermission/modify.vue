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
      <el-form-item label="应用" prop="app">
        <AppReference v-model="entityData.app" :app-param="appParam" />
      </el-form-item>
      <el-form-item label="角色编码" prop="roleCode">
        <dictionary-select v-model="entityData.roleCode" code="DataRole" />
      </el-form-item>
      <el-form-item label="业务编码" prop="businessCode">
        <el-input v-model="entityData.businessCode" />
      </el-form-item>
      <el-form-item label="二级业务编码" prop="secondBusinessCode">
        <el-input v-model="entityData.secondBusinessCode" />
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
import AppReference from '@/modules/cip/view/app/reference.vue'
const MODULE_CODE = 'cip'
const ENTITY_TYPE = 'appDataPermission'
export default {
  name: ENTITY_TYPE + '-modify',
  components: {
    AppReference
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
      // 应用组件参数，用于传递数据
      appParam: {},
      rules: {
        //前端验证规则
        app: [{ required: true, message: '【应用】不能为空', trigger: 'blur' }],
        roleCode: [{ required: true, message: '【角色编码】不能为空', trigger: 'blur' }],
        businessCode: [{ required: true, message: '【业务编码】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {}
}
</script>

<style></style>
