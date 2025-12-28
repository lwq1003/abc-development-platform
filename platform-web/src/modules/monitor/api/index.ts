import { COMMON_METHOD } from '@/constant/common'
import request from '@/config/axios'

const moduleName = 'monitor'

// 模板
export const monitor = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'systemMonitor' + '/',
  getInfo() {
    return request.get({ url: this.serveUrl + 'info' })
  }
})
