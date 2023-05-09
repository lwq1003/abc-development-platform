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
      <el-form-item label="上级" prop="permissionItem">
        <PermissionItemReference
          v-model="entityData.permissionItem"
          :permissionItem-param="permissionItemParam"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <dictionary-select v-model="entityData.type" code="PermissionType" />
      </el-form-item>
      <el-form-item
        label="视图编码"
        prop="viewCode"
        v-show="entityData.type == 'MENU' || entityData.type == 'PAGE'"
      >
        <el-input v-model="entityData.viewCode" />
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <icon-picker v-model="entityData.icon" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <dictionary-radio-group v-model="entityData.status" code="Status" />
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
import PermissionItemReference from '@/modules/system/view/permissionItem/treeReference.vue'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'permissionItem'
export default {
  name: ENTITY_TYPE + '-add',
  components: {
    PermissionItemReference
  },
  mixins: [addMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      // 上级组件参数，用于传递数据
      permissionItemParam: {},
      rules: {
        //前端验证规则
        permissionItem: [{ required: true, message: '【上级】不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }],
        code: [{ required: true, message: '【编码】不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '【类型】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    afterInit(param) {
      this.entityData.permissionItem = param.id
    },
    validateData() {
      if (
        (this.entityData.type == 'MENU' || this.entityData.type == 'PAGE') &&
        !this.entityData.viewCode
      ) {
        this.$message.warning('【视图编码】不能为空')
        return false
      }
      return true
    }
  }
}
</script>

<style></style>
