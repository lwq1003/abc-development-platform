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
      <el-form-item label="组织机构" prop="organization">
        <OrganizationReference
          v-model="entityData.organization"
          :organization-param="organizationParam"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <dictionary-select v-model="entityData.type" code="OrganizationType" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <dictionary-select v-model="entityData.status" code="Status" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="entityData.remark" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'
import OrganizationReference from '@/modules/system/view/organization/treeReference.vue'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'organization'
export default {
  name: ENTITY_TYPE + '-view',
  components: {
    OrganizationReference
  },
  mixins: [viewMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 组织机构组件参数，用于传递数据
      organizationParam: {},
      entityData: {}
    }
  },
  methods: {}
}
</script>

<style></style>
