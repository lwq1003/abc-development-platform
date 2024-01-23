import { COMMON_METHOD } from '@/constant/common'
import request from '@/config/axios'

const moduleName = 'entityconfig'

// 实体
export const entity = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'entity' + '/',
  // 生成库表
  generateTable(idList) {
    return request.post({ url: this.serveUrl + 'generateTable/' + idList })
  },
  // 生成代码
  generateCode(idList) {
    return request.post({ url: this.serveUrl + 'generateCode/' + idList })
  }
})

// 实体模型
export const entityModel = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'entityModel' + '/'
})

// 实体模型属性
export const entityModelProperty = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'entityModelProperty' + '/',
  // 获取实体模型完整属性列表
  getFullPropertyList(entityModelId) {
    return request.get({ url: this.serveUrl + entityModelId + '/list' })
  }
})

// 实体视图
export const entityView = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'entityView' + '/',
  // 更新高级配置
  updateAdvanceConfig(params) {
    return request.put({ url: this.serveUrl + 'updateAdvanceConfig', data: params })
  }
})

// 视图按钮模板
export const viewButtonTemplate = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'viewButtonTemplate' + '/'
})

// 视图按钮
export const viewButton = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'viewButton' + '/',

  // 获取视图对应的按钮列表,对按钮类型进行过滤
  listByViewAndType(viewId, buttonType) {
    return request.get({
      url: this.serveUrl + viewId + '/listByViewAndType',
      params: {
        buttonType
      }
    })
  },
  // 更新按钮顺序
  updateButtonSort(viewId, params) {
    return request.put({ url: this.serveUrl + viewId + '/updateButtonSort', data: params })
  },
  // 清空
  clear(viewId, buttonType) {
    return request.delete({ url: this.serveUrl + viewId + '/' + buttonType + '/clear' })
  },
  // 通过模板添加
  addFromTemplate(viewId, buttonType, params) {
    return request.post({
      url: this.serveUrl + viewId + '/' + buttonType + '/addFromTemplate/',
      data: params
    })
  }
})

// 列表视图配置
export const listViewConfig = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'entityViewConfig' + '/'
})

// 修改视图配置
export const modifyViewConfig = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'modifyViewConfig' + '/'
})

// 视图查询条件
export const viewQueryCondition = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'viewQueryCondition' + '/',
  // 获取视图对应的列表
  listByView(viewId) {
    return request.get({ url: this.serveUrl + viewId + '/list' })
  },
  // 更新顺序
  updateSort(viewId, params) {
    return request.put({
      url: this.serveUrl + viewId + '/updateSort',
      data: params,
      showInfo: false
    })
  },
  // 通过模型属性添加单个
  addFromModelProperty(viewId, code) {
    return request.post({ url: this.serveUrl + viewId + '/addFromModelProperty/' + code })
  },
  // 清空
  clear(viewId) {
    return request.delete({ url: this.serveUrl + viewId + '/clear' })
  },
  // 通过模型属性批量添加
  addBatchFromModelProperty(viewId) {
    return request.post({ url: this.serveUrl + viewId + '/addBatchFromModelProperty' })
  }
})

// 视图查询结果
export const viewQueryResult = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'viewQueryResult' + '/',
  // 获取视图对应的列表
  listByView(viewId) {
    return request.get({ url: this.serveUrl + viewId + '/list' })
  },
  // 更新顺序
  updateSort(viewId, params) {
    return request.put({
      url: this.serveUrl + viewId + '/updateSort',
      data: params,
      showInfo: false
    })
  },
  // 通过模型属性添加单个
  addFromModelProperty(viewId, code) {
    return request.post({ url: this.serveUrl + viewId + '/addFromModelProperty/' + code })
  },
  // 清空
  clear(viewId) {
    return request.delete({ url: this.serveUrl + viewId + '/clear' })
  },
  // 通过模型属性批量添加
  addBatchFromModelProperty(viewId) {
    return request.post({ url: this.serveUrl + viewId + '/addBatchFromModelProperty' })
  }
})

// 视图属性
export const viewProperty = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'viewProperty' + '/',
  // 获取视图对应的列表
  listByView(viewId) {
    return request.get({ url: this.serveUrl + viewId + '/list' })
  },
  // 更新顺序
  updateSort(viewId, params) {
    return request.put({
      url: this.serveUrl + viewId + '/updateSort',
      data: params,
      showInfo: false
    })
  },
  // 通过模型属性添加单个
  addFromModelProperty(viewId, code) {
    return request.post({ url: this.serveUrl + viewId + '/addFromModelProperty/' + code })
  },
  // 清空
  clear(viewId) {
    return request.delete({ url: this.serveUrl + viewId + '/clear' })
  },
  // 通过模型属性批量添加
  addBatchFromModelProperty(viewId) {
    return request.post({ url: this.serveUrl + viewId + '/addBatchFromModelProperty' })
  }
})

// 模板
export const template = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'template' + '/'
})
