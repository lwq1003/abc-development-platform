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
  }
})

// 我的申请
export const apply = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'apply' + '/'
})

// 我的待办
export const todo = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'todo' + '/'
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
  },
  // 重置环节设置
  resetNodeConfig(processDefinitionId, tempProcessDefinitionId, definitionKey) {
    return request.put({
      url: this.serveUrl + 'resetNodeConfig',
      data: qs.stringify({ processDefinitionId, tempProcessDefinitionId, definitionKey })
    })
  },
  // 保存环节设置
  saveNodeConfig(params) {
    return request.post({ url: this.serveUrl + 'saveNodeConfig', data: params })
  }
})

// // 环节权限设置
// export const workflowRightConfig = Object.assign({}, COMMON_METHOD, {
//   serveUrl: '/' + moduleName + '/' + 'workflowRightConfig' + '/',
//   // 获取首环节权限设置
//   getFirstNodeConfig(processDefinitionId) {
//     return request.get({ url: this.serveUrl + 'getFirstNodeConfig?' + qs.stringify({ processDefinitionId }))
//   },
//   // 获取环节权限设置
//   getNodeConfig(processDefinitionId, taskDefinitionKey) {
//     return request.get({ url: this.serveUrl + 'getNodeConfig?' + qs.stringify({ processDefinitionId, taskDefinitionKey }))
//   },
//   // 获取浏览模式环节权限设置
//   getNodeConfigForView(processDefinitionId) {
//     return request.get({ url: this.serveUrl + 'getNodeConfigForView?' + qs.stringify({ processDefinitionId }))
//   },

//   // 获取设置
//   getConfig(processDefinitionId, processDefinitionKey, definitionKey) {
//     return request.get({ url: this.serveUrl + 'getConfig?' + qs.stringify({ processDefinitionId, processDefinitionKey, definitionKey }))
//   },
//   // 保存设置
//   saveConfig(params) {
//     return request.post({ url: this.serveUrl + 'saveConfig', params)
//   }
// }
// )

// // 监听器
// export const workflowListener = Object.assign({}, COMMON_METHOD, {
//   serveUrl: '/' + moduleName + '/' + 'workflowListener' + '/',
//   enable(id) {
//     return request.put({ url: this.serveUrl + id + '/enable')
//   },
//   disable(id) {
//     return request.put({ url: this.serveUrl + id + '/disable')
//   }

// }
// )

// // 监听器设置
// export const workflowListenerConfig = Object.assign({}, COMMON_METHOD, {
//   serveUrl: '/' + moduleName + '/' + 'workflowListenerConfig' + '/'
//   // // 获取环节设置
//   // getNodeConfig(processDefinitionId, definitionKey) {
//   //   return request.get({ url: this.serveUrl + 'getNodeConfig?' + qs.stringify({ processDefinitionId, definitionKey }))
//   // },
//   // // 重置环节设置
//   // resetNodeConfig(processDefinitionId, tempProcessDefinitionId, definitionKey) {
//   //   return request.put({ url: this.serveUrl + 'resetNodeConfig', qs.stringify({ processDefinitionId, tempProcessDefinitionId, definitionKey }))
//   // },
//   // // 保存环节设置
//   // saveNodeConfig(params) {
//   //   return request.post({ url: this.serveUrl + 'saveNodeConfig', params)
//   // }
// }
// )
