import { COMMON_METHOD } from '@/constant/common'
import request from '@/config/axios'

const moduleName = 'notification'

// 系统消息
export const systemMessage = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'systemMessage' + '/',

  getUnreadMessageCount() {
    return request.get({ url: this.serveUrl + 'getUnreadMessageCount' })
  },
  read(id) {
    return request.put({ url: this.serveUrl + id + '/read', params: { showInfo: false } })
  },
  setAllRead() {
    return request.put({ url: this.serveUrl + 'setAllRead' })
  }
})
