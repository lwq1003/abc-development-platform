import { getToken } from '@/utils/auth'

import { ElNotification } from 'element-plus'
import { useNotificationStore } from '@/store/modules/notification'
const notificationStore = useNotificationStore()
// 重连标志位，防止重连过程中反复新建连接
let lockReconnect = false
// 心跳请求发送间隔时间
const heartbeatRequestInteval = 5 * 1000
// 心跳请求计时器对象
let heartbeatRequestTimer = null

// 心跳响应超时时间
const heartbeatResponseTimeout = 30 * 1000

// 心跳响应计时器对象
let heartbeatResponseTimer = null

// 重连计时器
let reconnectTimer = null

// 消息服务地址
const NOTIFICATION_SERVER_URL = import.meta.env.VITE_NOTIFICATION_SERVER_URL

let that
let webSocketInstance
class MyWebSocket {
  constructor() {
    // eslint-disable-next-line @typescript-eslint/no-this-alias
    that = this
  }

  // 开启心跳
  startHeartbeat() {
    // 清理计时器
    that.clearTimer()

    // 设置定时发送心跳请求
    heartbeatRequestTimer = setInterval(() => {
      that.sendMessage(JSON.stringify({ type: 'HEARTBEAT_REQUEST' }))
      // 设置服务器端心跳响应计时器
      if (heartbeatResponseTimer) {
        clearTimeout(heartbeatResponseTimer)
      }
      heartbeatResponseTimer = setTimeout(() => {
        that.reconnect()
      }, heartbeatResponseTimeout)
    }, heartbeatRequestInteval)
  }

  // 初始化weosocket
  init() {
    // 建立连接
    if (!webSocketInstance) {
      webSocketInstance = new WebSocket(NOTIFICATION_SERVER_URL)
      // 连接成功
      webSocketInstance.onopen = this.onopen
      // 连接错误
      webSocketInstance.onerror = this.onerror
      // 连接关闭
      webSocketInstance.onclose = this.onclose
      // 接收信息
      webSocketInstance.onmessage = this.onmessage
    }
  }

  // 重新连接
  reconnect() {
    // 获取token
    const token = getToken()
    // 如token为空，即未登录，则停止重新连接
    if (!token) {
      return
    }

    if (lockReconnect) {
      return
    }
    lockReconnect = true
    // 清理计时器
    this.clearTimer()
    // 没连接上会一直重连，设置延迟避免请求过多
    if (reconnectTimer) {
      clearTimeout(reconnectTimer)
    }
    reconnectTimer = setTimeout(function () {
      // 新连接
      that.init()
      lockReconnect = false
    }, 5000)
  }

  // 清理计时器
  clearTimer() {
    // 清理心跳请求计时器
    if (heartbeatRequestTimer) {
      clearInterval(heartbeatRequestTimer)
    }
    // 清理心跳响应计时器
    if (heartbeatResponseTimer) {
      clearTimeout(heartbeatResponseTimer)
    }
  }

  // 向服务器发送信息
  sendMessage(msg) {
    // 数据发送
    if (webSocketInstance.readyState === 1) {
      webSocketInstance.send(msg)
    } else {
      this.reconnect()
    }
  }

  // 关闭websocket
  close() {
    this.clearTimer()
    if (reconnectTimer) {
      clearTimeout(reconnectTimer)
    }
    // 发送关闭消息
    this.sendMessage(JSON.stringify({ type: 'LOGOUT_REQUEST' }))
    webSocketInstance = null
  }

  // 连接成功事件
  onopen() {
    // 获取token
    const token = getToken()
    // 发送token进行身份认证
    if (token) {
      that.sendMessage(JSON.stringify({ type: 'LOGIN_REQUEST', content: token }))
    }
  }
  // 接收服务器返回的数据
  onmessage(e) {
    // 解析数据
    const message = JSON.parse(e.data)
    if (message.type === 'LOGIN_RESPONSE') {
      // 开启心跳
      that.startHeartbeat()
    } else if (message.type === 'HEARTBEAT_RESPONSE') {
      // 收到心跳响应，重置消息响应定时器
      clearTimeout(heartbeatResponseTimer)
    } else if (message.type === 'BUSINESS_MESSAGE') {
      // console.log('收到业务消息', message)
      that.messageReceive(message)
    }
  }

  // 收到新消息
  messageReceive(message) {
    // 未读消息加1
    notificationStore.increaseUnreadMessageCount()
    // 弹出消息提示
    ElNotification({
      title: '收到新消息',
      message: message.title,
      position: 'bottom-right',
      onClick: that.showMessage
    })
  }

  // 发生错误
  onerror(e) {
    // 重连
    lockReconnect = false
    that.reconnect()
  }

  // 连接关闭事件
  onclose() {
    // 重连
    that.reconnect()
  }
  // 显示消息
  showMessage() {
    // TODO：如何调用消息查看组件
  }
}

const messageSocket = new MyWebSocket()
export default messageSocket
