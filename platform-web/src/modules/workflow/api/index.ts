import { COMMON_METHOD } from '@/constant/common'
import request from '@/config/axios'
import qs from 'qs'

const moduleName = 'workflow'

// 流程模板
export const workflowTemplate = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'workflowTemplate' + '/',
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  },
  publish(id) {
    return request.put({ url: this.serveUrl + id + '/publish' })
  },
  upgrade(id) {
    return request.put({ url: this.serveUrl + id + '/upgrade' })
  },
  valid(id) {
    return request.put({ url: this.serveUrl + id + '/valid' })
  },
  // 生成临时版本
  generateTemporaryVersion(processDefinitionKey) {
    return request.get({
      url: this.serveUrl + 'generateTemporaryVersion',
      params: { processDefinitionKey }
    })
  },
  // 获取模型
  getModelByProcessDefinitionId(processDefinitionId) {
    return request.get({
      url: this.serveUrl + 'getModelByProcessDefinitionId',
      params: { processDefinitionId }
    })
  },
  // 获取所有用户类型节点
  getUserTaskNodeByProcessDefinitionId(processDefinitionId) {
    return request.get({
      url: this.serveUrl + 'getUserTaskNodeByProcessDefinitionId',
      params: { processDefinitionId }
    })
  }
})

// 我的申请
export const apply = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'apply' + '/',
  portlet(params) {
    return request.get({ url: this.serveUrl + 'portlet', params })
  }
})

// 我的待办
export const todo = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'todo' + '/',
  portlet(params) {
    return request.get({ url: this.serveUrl + 'portlet', params })
  }
})

// 我的已办
export const done = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'done' + '/'
})

// 流程
export const processInstance = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'processInstance' + '/',
  // 获取流程当前任务标识，通常用于流程启动首环节处理
  getTaskId(processInstanceId) {
    return request.get({ url: this.serveUrl + processInstanceId + '/getTaskId' })
  },
  // 获取流程节点处理人
  getNodeHandler(processInstanceId, nodeId) {
    return request.get({
      url: this.serveUrl + processInstanceId + '/' + nodeId + '/getNodeHandler'
    })
  },
  // 获取某环节最后一次处理人
  getLastCommitter(processInstanceId, nodeId) {
    return request.get({
      url: this.serveUrl + processInstanceId + '/' + nodeId + '/getLastCommitter'
    })
  }
})

// 任务
export const task = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'task' + '/',
  // 提交
  commit(taskData) {
    return request.put({ url: this.serveUrl + taskData.taskId + '/commit', data: taskData })
  },
  // 驳回
  reject(taskData) {
    return request.put({ url: this.serveUrl + taskData.taskId + '/reject', data: taskData })
  },
  // 跳转
  jump(taskData) {
    return request.put({ url: this.serveUrl + taskData.taskId + '/jump', data: taskData })
  },
  // 转办
  transfer(taskId, assignee, comment) {
    return request.put({ url: this.serveUrl + taskId + '/transfer', params: { assignee, comment } })
  },
  // 委派
  delegate(taskId, assignee, comment) {
    return request.put({ url: this.serveUrl + taskId + '/delegate', params: { assignee, comment } })
  },
  // 签收
  signIn(taskId) {
    return request.put({ url: this.serveUrl + taskId + '/signIn' })
  },
  // 撤销签收
  cancelSignIn(taskId) {
    return request.put({ url: this.serveUrl + taskId + '/cancelSignIn' })
  },
  // 获取后续环节列表
  getForwardNodeList(id) {
    return request.get({ url: this.serveUrl + id + '/getForwardNodeList' })
  },
  // 获取回退环节列表
  getBackNodeList(id) {
    return request.get({ url: this.serveUrl + id + '/getBackNodeList' })
  },
  // 获取跳转环节列表
  getJumpNodeList(id) {
    return request.get({ url: this.serveUrl + id + '/getJumpNodeList' })
  }
})

// 处理意见
export const workflowComment = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'workflowComment' + '/'
})

// 环节设置
export const workflowNodeConfig = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'workflowNodeConfig' + '/',
  // 获取环节设置
  getNodeConfig(processDefinitionId, definitionKey) {
    return request.get({
      url: this.serveUrl + 'getNodeConfig?' + qs.stringify({ processDefinitionId, definitionKey })
    })
  }
})

// 环节权限设置
export const workflowNodePermissionConfig = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'workflowNodePermissionConfig' + '/',
  // 获取流程环节权限设置
  getNodePermissionConfig(processDefinitionId, definitionKey) {
    return request.get({
      url:
        this.serveUrl +
        'getNodePermissionConfig?' +
        qs.stringify({ processDefinitionId, definitionKey })
    })
  },
  // 获取浏览模式环节权限设置(查询所有区域，将权限设置为只读)
  getNodePermissionConfigForView(processDefinitionId) {
    return request.get({
      url: this.serveUrl + 'getNodePermissionConfigForView?' + qs.stringify({ processDefinitionId })
    })
  }
})
