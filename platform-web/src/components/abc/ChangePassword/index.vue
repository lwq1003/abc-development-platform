<template>
  <div style="height: 220px">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0 auto"
      autocomplete="new-password"
    >
      <el-form-item label="账号" prop="account">
        <el-input v-model="entityData.account" disabled />
      </el-form-item>
      <el-form-item label="旧密码" prop="oldPassword">
        <el-input v-model="entityData.oldPassword" show-password autocomplete="new-password" />
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword" class="form-item">
        <el-input v-model="entityData.newPassword" show-password />
      </el-form-item>
      <el-form-item label="重复新密码" prop="newPasswordRepeat">
        <el-input v-model="entityData.newPasswordRepeat" show-password />
      </el-form-item>
    </el-form>
    <el-button style="float: right" type="primary" @click="confirm">确认</el-button>
  </div>
</template>
<script>
import { useCache } from '@/hooks/web/useCache'
import { USER_KEY } from '@/constant/common'
const { wsCache } = useCache()
export default {
  components: {},
  emits: ['hidden'],
  data() {
    return {
      entityData: {
        account: wsCache.get(USER_KEY).account,
        oldPassword: '',
        newPassword: '',
        newPasswordRepeat: ''
      },
      rules: {
        //前端验证规则
        oldPassword: [{ required: true, message: '【旧密码】不能为空', trigger: 'blur' }],
        newPassword: [{ required: true, message: '【新密码】不能为空', trigger: 'blur' }],
        newPasswordRepeat: [{ required: true, message: '【重复新密码】不能为空', trigger: 'blur' }]
      }
    }
  },

  methods: {
    confirm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.entityData.newPassword !== this.entityData.newPasswordRepeat) {
            this.$message({
              type: 'warning',
              message: '两次输入的新密码不一致'
            })
            return
          }

          this.$api.system.user
            .changePassword(
              wsCache.get(USER_KEY).id,
              this.entityData.oldPassword,
              this.entityData.newPassword
            )
            .then(() => {
              let user = wsCache.get(USER_KEY)
              user.forceChangePasswordFlag = 'NO'
              wsCache.set(USER_KEY, user)
              this.$emit('hidden')
            })
        }
      })
    }
  }
}
</script>
<style></style>
