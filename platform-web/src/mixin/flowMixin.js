/**
 * 流程页面混入
 */

import DictionaryRadioGroup from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'
import DictionarySelect from '@/components/abc/DictionarySelect/DictionarySelect.vue'
import DataDictionarySelect from '@/modules/system/view/dictionaryType/treeReferenceUseCode.vue'
import OrganizationSingleSelect from '@/modules/system/view/organization/treeReference.vue'
import OrganizationMultipleSelect from '@/modules/system/view/organization/treeMultipleSelect.vue'
import { closeCurrentTab } from '@/utils'
import BasicInfo from '@/modules/workflow/view/task/basicInfo.vue'
import FlowPreview from '@/modules/workflow/view/workflowTemplate/model/components/flowPreview.vue'
export const flowMixin = {
  components: {
    DictionarySelect,
    DictionaryRadioGroup,
    OrganizationSingleSelect,
    OrganizationMultipleSelect,
    DataDictionarySelect,
    BasicInfo,
    FlowPreview
  },
  data() {
    return {
      // 可见性
      visible: false,
      // 加载中
      loading: false,
      // 模式
      mode: '',
      // 只读
      readonly: false,
      // // 流程实例标识
      // flowInstanceId: '',
      // // 任务标识
      // taskId: '',
      // 任务数据
      taskData: {
        // 任务标识
        taskId: '',
        // 环节名称
        stepName: '',
        // 流程实例标识
        processInstanceId: '',
        // 办理意见
        comment: '发起申请',
        // 流程模板标识
        processDefinitionId: '',
        // 流程模板类型
        processDefinitionKey: '',
        // 流程模板名称
        processDefinitionName: '',
        // 是否已保存
        isSaved: false,
        // 处理人
        assignee: ''
      },
      // 权限配置
      permissionConfigData: {}
    }
  },
  computed: {},
  methods: {
    // 新增
    add(processDefinitionId) {
      if (this.beforeAdd) {
        this.beforeAdd()
      }
      this.mode = 'add'

      this.api.init().then((res) => {
        this.entityData = res.data
        // 获取发起环节权限设置
        this.getNodePermissionConfig(processDefinitionId, 'root')

        // TODO：为测试方便，填充值，需过后删除
        this.entityData = Object.assign({}, this.entityData, {
          leaveType: 'PERSONAL',
          startTime: '2020-10-06 00:00:00',
          endTime: '2020-10-07 00:00:00',
          total: 3,
          reason: '家中有事处理'
        })
      })
    },
    // 修改
    modify(billNo, processDefinitionId, taskDefinitionKey) {
      if (this.beforeModify) {
        this.beforeModify()
      }
      this.mode = 'modify'
      this.api.getByBillNo(billNo).then((res) => {
        this.entityData = res.data
        this.getNodePermissionConfig(processDefinitionId, taskDefinitionKey)
      })
    },
    // 查看
    view(billNo, processDefinitionId) {
      this.mode = 'view'
      this.api.getByBillNo(billNo).then((res) => {
        this.entityData = res.data
        // 获取环节权限设置
        this.$api.workflow.workflowNodePermissionConfig
          .getNodePermissionConfigForView(processDefinitionId)
          .then((res) => {
            this.permissionConfigData = {}
            res.data.forEach((item) => {
              this.permissionConfigData[item.areaCode] = item.permission
            })
          })
        if (this.afterView) {
          this.afterView()
        }
      })
      this.readonly = true
      this.visible = true
    },
    getNodePermissionConfig(processDefinitionId, nodeId) {
      // 获取发起环节权限设置
      this.$api.workflow.workflowNodePermissionConfig
        .getNodePermissionConfig(processDefinitionId, nodeId)
        .then((res) => {
          this.permissionConfigData = {}
          res.data.forEach((item) => {
            this.permissionConfigData[item.areaCode] = item.permission
          })
        })
    },
    // 获取流程类型
    getProcessType() {
      return this.entityType
    },
    // 获取单号
    getBillNo() {
      return this.entityData.billNo
    },
    // 获取流程实例标识
    getProcessInstanceId() {
      return this.entityData.flowInstanceId
    },
    // 获取保存状态
    getSaveStatus() {
      return this.taskData.isSaved
    },
    // 提交
    commit() {
      if (this.taskData.taskId === '') {
        // 流程创建时保存后，无任务标识，通过流程标识获取任务标识
        this.$api.workflow.processInstance
          .getTaskId(this.taskData.processInstanceId)
          .then((res) => {
            this.taskData.taskId = res.data
            this.$refs.commit.show(this.taskData.processInstanceId, this.taskData.taskId)
          })
      } else {
        this.$refs.commit.show(
          this.taskData.processInstanceId,
          this.taskData.taskId,
          this.taskData.delegation
        )
      }
    },
    // 驳回
    reject() {
      this.$refs.reject.show(this.taskData.processInstanceId, this.taskData.taskId)
    },
    // 跳转
    jump() {
      if (this.taskData.taskId === '') {
        // 流程创建时保存后，无任务标识，通过流程标识获取任务标识
        this.$api.workflow.processInstance
          .getTaskId(this.taskData.processInstanceId)
          .then((res) => {
            this.taskData.taskId = res.data
            this.$refs.jump.show(this.taskData.processInstanceId, this.taskData.taskId)
          })
      } else {
        this.$refs.jump.show(this.taskData.processInstanceId, this.taskData.taskId)
      }
    },
    // 保存
    save() {
      this.$refs.flowComponent.saveBusinessData()
    },
    // 转办
    transfer() {
      this.$refs.transfer.show()
    },
    // 委派
    delegate() {
      this.$refs.delegate.show()
    },
    // 关闭tab页
    close() {
      closeCurrentTab()
    },
    // 关闭对话框
    closeDialog() {
      this.visible = false
    },
    // 保存业务数据
    saveBusinessData() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.mode === 'add') {
            this.api
              .add(this.entityData)
              .then((res) => {
                this.entityData = res.data
                if (this.afterAdd) {
                  this.afterAdd()
                }
                this.mode = 'modify'
                this.$emit('save', this.entityData.flowInstanceId)
              })
              .finally(() => {
                this.loading = false
              })
          } else {
            this.api
              .modify(this.entityData)
              .then((res) => {
                this.entityData = res.data
                if (this.afterModify) {
                  this.afterModify()
                }
              })
              .finally(() => {
                this.loading = false
              })
          }
        }
      })
    }
  }
}
