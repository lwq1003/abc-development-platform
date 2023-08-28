<template>
  <el-dialog
    title="任务跳转"
    v-model="visible"
    width="350"
    append-to-body
    destroy-on-close
    @close="close"
  >
    <el-form ref="form" :model="taskData" :rules="rules" label-width="80px" label-position="right">
      <el-row v-if="showCommonComment">
        <el-col :span="24">
          <el-form-item label="常用意见">
            <dictionary-select
              ref="commonComment"
              v-model="commonComment"
              code="CommonAdvice"
              class="form-item"
              @change="commonCommentChange"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="showCommonComment">
        <el-col :span="24">
          <el-form-item label="审批意见" prop="comment">
            <el-input v-model="taskData.comment" type="textarea" :rows="3" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="下一环节" prop="nextStepId">
            <el-select v-model="taskData.nextStepId" placeholder="请选择">
              <el-option
                v-for="item in nextStep"
                :key="item.nodeId"
                :label="item.name"
                :value="item.nodeId"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col v-show="handlerVisible" :span="24">
          <el-form-item label="处理人" prop="assignee">
            <el-select v-model="assigneeList" placeholder="请选择" :multiple="selectMode">
              <el-option
                v-for="item in handlerList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="jump">确定</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </el-dialog>
</template>
<script>
import DictionarySelect from '@/components/abc/DictionarySelect/DictionarySelect.vue'
export default {
  components: { DictionarySelect },
  props: {
    // 默认审批意见
    defaultComment: {
      type: String,
      required: false,
      default: ''
    },
    showCommonComment: {
      type: Boolean,
      required: false,
      default: true
    }
  },
  data() {
    return {
      // 可见性
      visible: false,
      // 常用审批意见
      commonComment: '',
      // 任务数据
      taskData: {
        // 任务标识
        taskId: this.taskId,
        // 办理意见
        comment: this.defaultComment,
        // 下一环节标识
        nextStepId: '',
        // 下一环节处理人
        assigneeList: []
      },
      // 环节列表
      nextStep: [],
      // 处理人选择区域可见性
      handlerVisible: false,
      // 处理人
      assigneeList: '',
      // 处理人列表
      handlerList: [],
      // 选择类型 单选false、多选true
      selectMode: false,
      rules: {
        comment: [{ required: true, message: '请填写审批意见', trigger: 'blur' }],
        nextStepId: [{ required: true, message: '请选择下一处理环节', trigger: 'blur' }]
      }
    }
  },
  watch: {
    'taskData.nextStepId'() {
      this.getNodeHandler()
    }
  },
  methods: {
    show(processInstanceId, taskId) {
      this.taskData.taskId = taskId
      this.taskData.processInstanceId = processInstanceId
      this.getJumpNodeList()
      this.visible = true
    },
    jump() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 提交前将处理人员统一设置为数组
          if (Array.isArray(this.assigneeList)) {
            this.taskData.assigneeList = this.assigneeList
          } else {
            this.taskData.assigneeList = []
            this.taskData.assigneeList.push(this.assigneeList)
          }
          // 执行流程提交操作
          this.$api.workflow.task.jump(this.taskData).then(() => {
            this.$emit('success')
            this.close()
          })
        }
      })
    },
    commonCommentChange(value) {
      this.taskData.comment = this.$refs.commonComment.getLabel(value)
    },
    getJumpNodeList() {
      this.$api.workflow.task.getJumpNodeList(this.taskData.taskId).then((res) => {
        this.nextStep = res.data
        // 如只有一条记录，默认选中
        if (this.nextStep && this.nextStep.length === 1) {
          this.taskData.nextStepId = this.nextStep[0].nodeId
        }
      })
    },
    getNodeHandler() {
      if (this.taskData.nextStepId) {
        // 获取选中的环节配置信息
        const nodeConfig = this.nextStep.filter((x) => x.nodeId === this.taskData.nextStepId)[0]
        if (nodeConfig && nodeConfig.setAssigneeFlag === 'YES') {
          // 需要指定人员
          this.handlerVisible = true
          // 根据节点类型是普通还是会签，设置控件多选还是单选
          if (nodeConfig.mode === 'NORMAL') {
            this.selectMode = false
          } else {
            this.selectMode = true
          }
        } else {
          this.handlerVisible = false
        }
        // 优先获取环节最后一次提交人
        this.$api.workflow.processInstance
          .getLastCommitter(this.taskData.processInstanceId, this.taskData.nextStepId)
          .then((res) => {
            // 返回不为空
            if (res.data && res.data.length > 0) {
              // 如只有一条记录，默认选中
              this.handlerList = res.data
              if (res.data.length === 1) {
                if (this.selectMode === true) {
                  this.assigneeList = []
                  this.assigneeList.push(this.handlerList[0].id)
                } else {
                  this.assigneeList = this.handlerList[0].id
                }
              }
            } else {
              // 返回为空，该环节从未经过，根据该环节设置处理
              // 加载处理人员
              this.$api.workflow.processInstance
                .getNodeHandler(this.taskData.processInstanceId, this.taskData.nextStepId)
                .then((res) => {
                  // 返回不为空
                  if (res.data && res.data.length > 0) {
                    // 如只有一条记录，默认选中
                    this.handlerList = res.data
                    if (res.data.length === 1) {
                      if (this.selectMode === true) {
                        this.assigneeList = []
                        this.assigneeList.push(this.handlerList[0].id)
                      } else {
                        this.assigneeList = this.handlerList[0].id
                      }
                    }
                  }
                })
            }
          })
      }
    },
    // 关闭对话框
    close() {
      this.visible = false
    }
  }
}
</script>

<style scoped></style>
