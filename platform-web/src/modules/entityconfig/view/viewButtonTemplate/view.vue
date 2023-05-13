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
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'
const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'viewButtonTemplate'
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
