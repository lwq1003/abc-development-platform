import { getToken } from '@/utils/auth'
import { ElNotification } from 'element-plus'
import { useNotificationStore } from '@/store/modules/notification'
const notificationStore = useNotificationStore()
class GlobalSSE {
  /**
   * 初始化全局SSE管理器
   * @param {Object} options 配置项
   * @param {string} [options.url] SSE后端连接地址（默认读取环境变量）
   * @param {number} [options.retryInterval=3000] 重连间隔（毫秒）
   * @param {number} [options.maxRetry=10] 最大重连次数（-1表示无限）
   */
  constructor(options = {}) {
    // 配置参数（优先使用options，其次默认值）
    this.url = options.url || import.meta.env.VITE_NOTIFICATION_SSE_URL
    this.retryInterval = options.retryInterval ?? 3000
    this.maxRetry = options.maxRetry ?? 10

    // 状态变量（实例属性，初始化）
    // 0-未连接，1-连接中，2-已连接，3-已关闭
    this.status = 0
    // 初始token
    this.token = getToken()
    // EventSource实例
    this.eventSource = null
    // 当前重连次数
    this.retryCount = 0
    // 事件监听器映射
    this.eventListeners = new Map()

    // 检查浏览器兼容性
    if (!window.EventSource) {
      console.error('当前浏览器不支持SSE（EventSource），无法建立连接')
    }
  }

  /**
   * 建立SSE连接
   */
  connect() {
    // 检查浏览器支持
    if (!window.EventSource) return

    // 避免重复连接
    if (this.status === 1 || this.status === 2) {
      console.warn('SSE连接已存在或正在建立中')
      return
    }

    // 更新token（防止token过期）
    this.token = getToken()
    if (!this.token) {
      console.error('未获取到用户token，无法建立SSE连接')
      return
    }

    // 标记为连接中
    this.status = 1

    // 处理URL（携带token）
    try {
      const url = new URL(this.url)
      url.searchParams.append('token', this.token)

      this.eventSource = new EventSource(url.toString())

      // 连接建立成功
      this.eventSource.onopen = (event) => {
        this.status = 2
        this.retryCount = 0
        this.triggerEvent('open', event)
        console.log('SSE连接已建立')
      }

      // 接收通用消息（后端未指定事件类型）
      this.eventSource.onmessage = (event) => {
        try {
          const data = JSON.parse(event.data)
          console.log('SSE接收到消息：', data)
          // 未读消息加1
          notificationStore.increaseUnreadMessageCount()
          // 弹出消息提示
          ElNotification({
            title: '收到新消息',
            message: data.title,
            position: 'bottom-right'
          })
          this.triggerEvent('message', data, event.lastEventId)
        } catch (e) {
          this.triggerEvent('message', event.data, event.lastEventId)
        }
      }

      // 接收后端自定义事件（不覆盖原生方法，而是主动监听并转发）
      // 注：后端自定义事件需通过addEventListener注册，这里无需重写，外部通过类的addListener监听
      // 例如：后端发送event: notify，则外部调用sse.addListener('notify', callback)

      // 连接错误/断开（含重连逻辑）
      this.eventSource.onerror = (error) => {
        this.triggerEvent('error', error)
        console.error('SSE连接错误/断开，准备重连', error)

        // 关闭当前无效连接
        this.close(false)

        // 重连逻辑
        if (this.maxRetry === -1 || this.retryCount < this.maxRetry) {
          this.retryCount++
          setTimeout(() => {
            console.log(`第${this.retryCount}次重连...`)
            this.connect()
          }, this.retryInterval)
        } else {
          console.error(`已达到最大重连次数（${this.maxRetry}次），停止重连`)
          this.status = 0
          this.triggerEvent('maxRetryReached')
        }
      }
    } catch (e) {
      console.error('SSE连接初始化失败', e)
      this.status = 0
      this.triggerEvent('error', e)
    }
  }

  /**
   * 关闭SSE连接
   * @param {boolean} [isManual=true] 是否手动关闭（手动关闭不触发重连）
   */
  close(isManual = true) {
    if (this.eventSource) {
      this.eventSource.close()
      // 清理引用
      this.eventSource = null
    }
    // 手动关闭标记为"已关闭"，否则标记为"未连接"
    this.status = isManual ? 3 : 0
    this.triggerEvent('close')
    console.log('SSE连接已关闭')
  }

  /**
   * 添加事件监听器（支持原生事件和后端自定义事件）
   * @param {string} eventName 事件名（如"open"、"message"、"notify"等）
   * @param {Function} callback 回调函数：(data, lastEventId) => {}
   */
  addListener(eventName, callback) {
    if (!this.eventListeners.has(eventName)) {
      this.eventListeners.set(eventName, [])
    }
    this.eventListeners.get(eventName).push(callback)

    // 对后端自定义事件，需注册到eventSource（原生方法）
    if (this.eventSource && ['open', 'message', 'error'].indexOf(eventName) === -1) {
      this.eventSource.addEventListener(eventName, (event) => {
        try {
          const data = JSON.parse(event.data)
          this.triggerEvent(eventName, data, event.lastEventId)
        } catch (e) {
          this.triggerEvent(eventName, event.data, event.lastEventId)
        }
      })
    }
  }

  /**
   * 移除事件监听器
   * @param {string} eventName 事件名
   * @param {Function} [callback] 特定回调（不传则移除所有）
   */
  removeListener(eventName, callback) {
    if (!this.eventListeners.has(eventName)) return
    if (callback) {
      const listeners = this.eventListeners.get(eventName)
      this.eventListeners.set(
        eventName,
        listeners.filter((fn) => fn !== callback)
      )
    } else {
      this.eventListeners.delete(eventName)
    }
  }

  /**
   * 触发事件（通知所有监听器）
   * @param {string} eventName 事件名
   * @param {any} data 事件数据
   * @param {string} [lastEventId] 消息ID
   */
  triggerEvent(eventName, data, lastEventId) {
    const listeners = this.eventListeners.get(eventName)
    if (listeners && listeners.length) {
      listeners.forEach((callback) => {
        try {
          callback(data, lastEventId)
        } catch (e) {
          console.error(`SSE事件${eventName}回调执行失败`, e)
        }
      })
    }
  }

  /**
   * 获取当前连接状态
   * @return {number} 0-未连接，1-连接中，2-已连接，3-已关闭
   */
  getStatus() {
    return this.status
  }

  /**
   * 更新token并重建连接（如登录后）
   * @param {string} newToken 新的令牌
   */
  updateToken(newToken) {
    this.token = newToken
    if (this.status === 2) {
      this.close(false)
      setTimeout(() => this.connect(), 1000)
    }
  }
}

// 全局单例实例
const sseInstance = (() => {
  let instance = null
  return {
    /**
     * 获取单例实例
     * @param {Object} options 配置项（仅首次创建时生效）
     * @returns {GlobalSSE} 单例实例
     */
    getInstance: (options = {}) => {
      if (!instance) {
        instance = new GlobalSSE(options)
      }
      return instance
    },
    /**
     * 销毁单例（登出时调用）
     */
    destroy: () => {
      if (instance) {
        instance.close()
        instance = null
      }
    }
  }
})()

export default sseInstance
