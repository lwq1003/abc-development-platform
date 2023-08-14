<template>
  <Dialog title="新增" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="类别" prop="category">
        <dictionary-select v-model="entityData.category" code="ProcessDefinitionCategory" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item label="模板版本" prop="templateVersion">
        <el-input v-model="entityData.templateVersion" />
      </el-form-item>
      <el-form-item label="启用状态" prop="status">
        <dictionary-select v-model="entityData.status" code="Status" />
      </el-form-item>
      <el-form-item label="模型" prop="model">
        <FlowDesign
          v-model="entityData.model"
          :workflowTemplateName="entityData.name"
          :workflowTemplateCode="entityData.code"
        />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
      <el-form-item label="模板状态" prop="templateStatus">
        <dictionary-select
          v-model="entityData.templateStatus"
          code="WorkflowTemplateStatus"
          readonly="readonly"
        />
      </el-form-item>
      <el-form-item label="流程定义" prop="processDefinitionId" v-show="false">
        <el-input v-model="entityData.processDefinitionId" />
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
import FlowDesign from './model/components/flowDesign.vue'
const MODULE_CODE = 'workflow'
const ENTITY_TYPE = 'workflowTemplate'
export default {
  name: ENTITY_TYPE + '-add',
  components: { FlowDesign },
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
        category: [{ required: true, message: '【类别】不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }],
        code: [{ required: true, message: '【编码】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {}
}
</script>

<style></style>
