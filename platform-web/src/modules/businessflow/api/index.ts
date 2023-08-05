import { COMMON_METHOD } from '@/constant/common'
import request from '@/config/axios'

const moduleName = 'businessflow'

// 请假申请
export const leave = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'leave' + '/',
  getByBillNo(billNo) {
    return request.get({ url: this.serveUrl + 'getByBillNo/' + billNo })
  }
})

export const navigate = Object.assign(
  {},
  {
    serveUrl: '/' + moduleName + '/' + 'navigate' + '/',
    get() {
      return request.get({ url: this.serveUrl })
    },
    // 获取级联框数据
    cascader() {
      return request.get({ url: this.serveUrl + 'cascader' })
    }
  }
)
