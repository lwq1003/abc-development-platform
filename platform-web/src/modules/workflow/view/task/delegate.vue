<template>
  <el-dialog
    title="任务委派"
    v-model="visible"
    width="350"
    append-to-body
    destroy-on-close
    @close="close"
  >
    <el-form ref="form" :model="formData" :rules="rules" label-width="80px" label-position="right">
      <el-row>
        <el-col :span="24">
          <el-form-item label="委派人" prop="assignee">
            <user-single-select v-model="formData.assignee" @change-select="changeSelect" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="委派说明" prop="comment">
            <el-input v-model="formData.comment" type="textarea" :rows="3" style="width: 220px" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="confirm">确定</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script>
import UserSingleSelect from '@/modules/system/view/user/treeListReference.vue'
export default {
  components: { UserSingleSelect },
  props: {
    taskId: {
      type: String,
      required: false,
      default: ''
    }
  },
  data() {
    return {
      visible: false,
      formData: {
        assignee: '',
        comment: ''
      },
      rules: {
        assignee: [{ required: true, message: '请选择处理人员', trigger: 'blur' }]
      }
    }
  },

  methods: {
    show() {
      this.visible = true
    },
    confirm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$api.workflow.task
            .delegate(this.taskId, this.formData.assignee, this.formData.comment)
            .then(() => {
              this.$emit('success')
              this.close()
            })
        }
      })
    },
    close() {
      this.visible = false
    },
    changeSelect(value) {
      this.formData.assignee = value.id
    }
  }
}
</script>
<style scoped></style>
