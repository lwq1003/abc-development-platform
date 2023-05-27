import { COMMON_METHOD } from '@/constant/common'
import request from '@/config/axios'

const moduleName = 'template'

// 模板
export const notice = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'notice' + '/',
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  }
})
