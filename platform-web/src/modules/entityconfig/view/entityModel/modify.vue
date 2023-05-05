<template>
  <Dialog title="修改" v-model="visible" width="40%">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0 auto"
    >
      <!--表单区域 -->
      <el-form-item label="父级模型" prop="parentModel">
        <dictionary-radio-group v-model="entityData.parentModel" code="BaseModel" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item label="是否主模型" prop="mainModelFlag">
        <dictionary-radio-group v-model="entityData.mainModelFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="是否自关联" prop="selfReferenceFlag">
        <dictionary-radio-group v-model="entityData.selfReferenceFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="entityData.remark" />
      </el-form-item>
      <el-form-item label="实体" prop="entity" v-show="false">
        <el-input v-model="entityData.entity" />
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

const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'entityModel'
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
        parentModel: [{ required: true, message: '【父级模型】不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }],
        code: [{ required: true, message: '【编码】不能为空', trigger: 'blur' }],
        mainModelFlag: [{ required: true, message: '【是否主模型】不能为空', trigger: 'blur' }],
        selfReferenceFlag: [{ required: true, message: '【是否自关联】不能为空', trigger: 'blur' }],
        entity: [{ required: true, message: '【实体】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {}
}
</script>

<style></style>
