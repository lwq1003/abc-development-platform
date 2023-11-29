<template>
  <el-card class="box-card">
    <template #header>
      <span>基本信息</span>
    </template>
    <el-form ref="form" :model="entityData" label-width="80px" label-position="right">
      <el-row>
        <el-col :span="12">
          <el-form-item label="单据编号">{{ entityData.billNo }}</el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="发起时间">{{
            $dateFormatter.formatUTCTime(entityData.initiateTime)
          }}</el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="发起人">{{ entityData.initiateUserName }}</el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系方式">{{ entityData.initiateUserPhone }}</el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="发起部门">{{
            entityData.initiateOrganizationFullName
          }}</el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-card>
</template>
<script>
export default {
  props: {
    entityData: {
      type: Object,
      required: false,
      default() {
        return {}
      }
    }
  },
  data() {
    return {}
  }
}
</script>

<style scoped></style>
