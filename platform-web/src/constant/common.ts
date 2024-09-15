// 网络请求状态码
// 请求成功
export const REQUEST_SUCCESS = 200
// 请求参数错误
export const BAD_REQUEST = 400
// 未授权状态码
export const UNAUTHORIZED = 401
// 无权访问
export const FORBIDDEN = 403
// 未找到资源
export const NOT_FOUND = 404
// 请求方法不支持
export const METHOD_NOT_ALLOWED = 405
// 服务器内部错误
export const INTERNAL_SERVER_ERROR = 500

// token键值
export const TOKEN_KEY = 'AbcPlatformToken'
// 用户键值
export const USER_KEY = 'AbcPlatformUser'

// 默认的当前页码
export const DEFAULT_PAGE_NUM = 1
// 默认的分页显示行数
export const DEFAULT_PAGE_SIZE = 10
// 分页功能默认的可选的page sizes
export const DEFAULT_PAGE_SIZE_ARR = [10, 20, 50, 100]
// 没有数据项目被选中时的标志
export const NO_ITEM_SELECTED = '-1'

// api公用方法

import request from '@/config/axios'

export const COMMON_METHOD = {
  serveUrl: '',
  init() {
    return request.get({ url: this.serveUrl + 'init' })
  },
  get(id) {
    return request.get({ url: this.serveUrl + id })
  },
  add(params) {
    return request.post({ url: this.serveUrl, data: params })
  },
  modify(params) {
    return request.put({ url: this.serveUrl, data: params })
  },
  remove(id) {
    return request.delete({ url: this.serveUrl + id })
  },
  page(params) {
    return request.get({ url: this.serveUrl + 'page', params })
  },
  list(params) {
    return request.get({ url: this.serveUrl + 'list', params })
  },
  // 批量复制新增
  addByCopy(ids) {
    return request.post({ url: this.serveUrl + ids })
  },
  // 单条复制新增
  addSingleByCopy(id) {
    return request.post({ url: this.serveUrl + id + '/addSingleByCopy' })
  },
  // 自定义查询
  customQuery(customQueryString, queryParams) {
    return request.post({
      url: this.serveUrl + 'customQuery',
      params: queryParams,
      data: customQueryString
    })
  }
}

// 数据字典常量
// 是/否
export const YES_OR_NO_CODE = 'YesOrNo'
export const YES = 'YES'
export const NO = 'NO'

// 日期时间格式
export const DATETIME_FORMAT = 'DatetimeFormat'

// 时间格式
export const TIME_FORMAT = 'TimeFormat'
