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
      <el-form-item label="姓名" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="账号" prop="account">
        <el-input v-model="entityData.account" />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <dictionary-radio-group v-model="entityData.gender" code="Gender" />
      </el-form-item>
      <el-form-item label="出生日期" prop="birthday">
        <el-date-picker
          v-model="entityData.birthday"
          :value-format="$dateFormatter.getDatetimeFormat('DAY')"
          :type="$dateFormatter.getDatetimeType('DAY')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="telephone">
        <el-input v-model="entityData.telephone" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="entityData.email" />
      </el-form-item>
      <el-form-item label="职位" prop="position">
        <el-input v-model="entityData.position" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <dictionary-radio-group v-model="entityData.status" code="UserStatus" />
      </el-form-item>
      <el-form-item label="强制修改密码" prop="forceChangePasswordFlag">
        <dictionary-radio-group v-model="entityData.forceChangePasswordFlag" code="YesOrNo" />
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
import OrganizationReference from '@/modules/system/view/organization/treeReference.vue'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'user'
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
