<template>
  <div class="div-center">
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
      <el-form-item label="新密码" prop="newPassword" class="form-item">
        <el-input v-model="entityData.newPassword" show-password />
      </el-form-item>
      <el-form-item label="重复新密码" prop="newPasswordRepeat">
        <el-input v-model="entityData.newPasswordRepeat" show-password />
      </el-form-item>
    </el-form>
    <br />
    <el-button style="float: right" type="primary" @click="confirm">确认</el-button>
  </div>
</template>
<script>
export default {
  components: {},
  emits: ['hidden'],
  data() {
    return {
      entityData: {
        account: '',
        newPassword: '',
        newPasswordRepeat: ''
      },
      rules: {
        //前端验证规则
        newPassword: [{ required: true, message: '【新密码】不能为空', trigger: 'blur' }],
        newPasswordRepeat: [{ required: true, message: '【重复新密码】不能为空', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      const code = this.$route.query.code
      this.$api.system.user.getAccoutByCode(code).then((res) => {
        this.entityData.account = res.data
      })
    },
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
          const code = this.$route.query.code
          this.$api.system.user.selfResetPassword(code, this.entityData.newPassword).then(() => {
            // 跳转到登录页面
            this.$router.push({ name: 'Login' })
          })
        }
      })
    }
  }
}
</script>
<style scoped>
.div-center {
  position: absolute;
  top: 20%;
  left: 50%;
  transform: translate(-50%, -20%); /* 偏移元素自身宽度和高度的50% */
}
</style>
