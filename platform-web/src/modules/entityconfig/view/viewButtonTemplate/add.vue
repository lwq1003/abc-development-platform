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
      <el-form-item label="按钮类型" prop="buttonType">
        <dictionary-select v-model="entityData.buttonType" code="ViewButtonType" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input v-model="entityData.content" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <icon-picker v-model="entityData.icon" />
      </el-form-item>
      <el-form-item label="是否需确认" prop="confirmFlag">
        <dictionary-radio-group v-model="entityData.confirmFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item
        label="确认信息"
        prop="confirmMessage"
        v-show="entityData.confirmFlag == $constant.YES"
      >
        <el-input v-model="entityData.confirmMessage" />
      </el-form-item>
      <el-form-item label="是否控制权限" prop="permissionFlag">
        <dictionary-radio-group v-model="entityData.permissionFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item
        label="权限编码"
        prop="permissionCode"
        v-show="entityData.permissionFlag == $constant.YES"
      >
        <el-input v-model="entityData.permissionCode" />
      </el-form-item>
      <el-form-item label="是否用于更多" prop="moreFlag">
        <dictionary-radio-group v-model="entityData.moreFlag" code="YesOrNo" />
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
const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'viewButtonTemplate'
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
        buttonType: [{ required: true, message: '【按钮类型】不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }],
        code: [{ required: true, message: '【编码】不能为空', trigger: 'blur' }],
        confirmFlag: [{ required: true, message: '【是否需确认】不能为空', trigger: 'blur' }],
        permissionFlag: [{ required: true, message: '【是否控制权限】不能为空', trigger: 'blur' }],
        moreFlag: [{ required: true, message: '【是否用于更多】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    validateData() {
      // 需要确认情况下必须输入确认信息
      if (this.entityData.confirmFlag === this.$constant.YES && !this.entityData.confirmMessage) {
        this.$message.warning('请输入确认信息')
        return false
      }
      // 需要控制权限则必须输入权限编码
      if (
        this.entityData.permissionFlag === this.$constant.YES &&
        !this.entityData.permissionCode
      ) {
        this.$message.warning('请输入权限编码')
        return false
      }
      return true
    }
  }
}
</script>

<style></style>
