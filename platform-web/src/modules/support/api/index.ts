import { COMMON_METHOD } from '@/constant/common'
import request from '@/config/axios'

const moduleName = 'support'

// 通知公告
export const notice = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'notice' + '/',
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  },
  setTop(id) {
    return request.put({ url: this.serveUrl + id + '/setTop' })
  },
  cancelTop(id) {
    return request.put({ url: this.serveUrl + id + '/cancelTop' })
  },

  portlet(params) {
    return request.get({ url: this.serveUrl + 'portlet', params })
  },
  // 阅读量加1
  updateReadCount(id) {
    return request.put({
      url: this.serveUrl + id + '/updateReadCount',
      params: { showInfo: false }
    })
  },
  // 浏览列表
  viewList(params) {
    return request.get({ url: this.serveUrl + 'viewList', params })
  }
})

// 附件
export const attachment = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'attachment' + '/',
  // 上传
  upload() {
    return request.post({ url: this.serveUrl + 'upload' })
  },
  // 下载
  download(id) {
    return request.download({ url: this.serveUrl + id + '/download' })
  },
  // 上传图片
  uploadImage(param) {
    return request.upload({ url: this.serveUrl + 'uploadImage', data: param })
  }
})

// 流水号
export const serialNo = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'serialNo' + '/'
})

// 内容模板
export const contentTemplate = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'contentTemplate' + '/'
})

// 组件
export const portlet = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'portlet' + '/',
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  },
  getPortletList() {
    return request.get({ url: this.serveUrl + 'getPortletList' })
  }
})

// 组件参数
export const portletParam = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'portletParam' + '/'
})

// 桌面模板
export const desktopTemplate = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'desktopTemplate' + '/',
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  }
})
