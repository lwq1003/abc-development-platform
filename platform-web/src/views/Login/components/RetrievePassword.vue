<template>
  <Dialog title="找回密码" v-model="visible" width="300px">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="70px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
      v-loading="loading"
    >
      <!--表单区域 -->

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="entityData.email" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="confirm">确定</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { Dialog } from '@/components/abc/Dialog'

export default {
  name: 'RetrievePassword',
  components: { Dialog },
  data() {
    return {
      // 加载中
      loading: false,
      // 可见性
      visible: false,
      entityData: {},
      rules: {
        //前端验证规则
        email: [{ required: true, message: '【邮箱】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    init() {
      this.visible = true
    },
    confirm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true
          this.$api.system.user
            .retrievePassword(this.entityData.email)
            .then((res) => {
              this.$message.info('密码重置邮件已发送，请查收')
              this.visible = false
            })
            .finally(() => {
              this.loading = false
            })
        } else {
          return false
        }
      })
    },
    close() {
      this.visible = false
    }
  }
}
</script>

<style></style>
