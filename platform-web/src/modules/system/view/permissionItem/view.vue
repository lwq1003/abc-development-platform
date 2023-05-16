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
      <el-form-item label="权限编码" prop="permissionCode">
        <el-input v-model="entityData.permissionCode" />
      </el-form-item>
      <el-form-item
        label="视图编码"
        prop="viewCode"
        v-show="entityData.type == 'MENU' || entityData.type == 'PAGE'"
      >
        <el-input v-model="entityData.viewCode" />
      </el-form-item>
      <el-form-item label="组件" prop="component">
        <el-input v-model="entityData.component" />
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
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'
import PermissionItemReference from '@/modules/system/view/permissionItem/treeReference.vue'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'permissionItem'
export default {
  name: ENTITY_TYPE + '-view',
  components: {
    PermissionItemReference
  },
  mixins: [viewMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 上级组件参数，用于传递数据
      permissionItemParam: {},
      entityData: {}
    }
  },
  methods: {}
}
</script>

<style></style>
