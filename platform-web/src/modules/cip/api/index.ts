import { COMMON_METHOD } from '@/constant/common'
import request from '@/config/axios'

const moduleName = 'cip'

// 应用
export const app = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'app' + '/',
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  },
  // 重置密钥
  resetSecret(id) {
    return request.put({ url: this.serveUrl + id + '/resetSecret' })
  },
  // 授予接口服务权限
  grantApiPermission(id, apiServiceIdList) {
    return request.put({ url: this.serveUrl + id + '/grantApiPermission', data: apiServiceIdList })
  },
  // 收回接口服务权限
  withdrawApiPermission(id, apiServiceIdList) {
    return request.put({
      url: this.serveUrl + id + '/withdrawApiPermission',
      data: apiServiceIdList
    })
  },
  // 授予消息主题权限
  grantMessagePermission(id, messageTopicIdList) {
    return request.put({
      url: this.serveUrl + id + '/grantMessagePermission',
      data: messageTopicIdList
    })
  },
  // 收回消息主题权限
  withdrawMessagePermission(id, messageTopicIdList) {
    return request.put({
      url: this.serveUrl + id + '/withdrawMessagePermission',
      data: messageTopicIdList
    })
  },
  // 订阅消息
  subscribeMessage(messageTopicIdList) {
    return request.put({
      url: this.serveUrl + 'subscribeMessage',
      data: messageTopicIdList
    })
  },
  // 取消订阅消息
  unsubscribeMessage(messageTopicIdList) {
    return request.put({
      url: this.serveUrl + 'unsubscribeMessage',
      data: messageTopicIdList
    })
  }
})

// 接口服务
export const apiService = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'apiService' + '/',
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  },
  queryApiPermission(params) {
    return request.get({ url: this.serveUrl + 'queryApiPermission', params })
  }
})

// 接口服务日志
export const apiServiceLog = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'apiServiceLog' + '/'
})

// 消息主题
export const messageTopic = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'messageTopic' + '/',
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  },
  queryMessagePermission(params) {
    return request.get({ url: this.serveUrl + 'queryMessagePermission', params })
  },
  queryMessageSubscription(params) {
    return request.get({ url: this.serveUrl + 'queryMessageSubscription', params })
  }
})

// 消息服务日志
export const messageLog = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'messageLog' + '/'
})

// 数据权限
export const appDataPermission = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'appDataPermission' + '/'
})

// 活跃消息
export const activeMessage = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'activeMessage' + '/',
  resend(id) {
    return request.put({ url: this.serveUrl + 'resend' + '/' + id })
  }
})
