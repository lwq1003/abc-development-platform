import { COMMON_METHOD } from '@/constant/common'
import request from '@/config/axios'

const moduleName = 'system'

// 系统参数
export const param = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'param' + '/'
})

// 系统日志
export const log = {
  serveUrl: '/' + moduleName + '/' + 'log' + '/',

  page(params) {
    return request.get({ url: this.serveUrl + 'page', params })
  },
  get(id) {
    return request.get({ url: this.serveUrl + id })
  }
}

// 组织机构
export const organization = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'organization' + '/',
  tree() {
    return request.get({ url: this.serveUrl + 'tree' })
  },
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  },
  // 下载导入模板
  downloadImportTemplate() {
    return request.download({ url: this.serveUrl + 'downloadImportTemplate' })
  },
  // 导入
  import(formData) {
    return request.upload({ url: this.serveUrl + 'importExcel', data: formData })
  },
  // 导出
  export(params) {
    return request.download({ url: this.serveUrl + 'exportExcel', params })
  }
  // // 根据id列表查询信息
  // getOrganization(idList) {
  //   return request.get({url:this.serveUrl + 'getOrganization?' + qs.stringify(idList, { arrayFormat: 'repeat' }))
  // },
})

// 用户
export const user = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'user' + '/',
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  },
  resetPassword(id) {
    return request.put({ url: this.serveUrl + id + '/resetPassword' })
  },
  unlock(id) {
    return request.put({ url: this.serveUrl + id + '/unlock' })
  },
  // 用户修改密码
  changePassword(id, oldPassword, newPassword) {
    return request.put({
      url: this.serveUrl + 'changePassword',
      data: { userId: id, oldPassword, newPassword }
    })
  },
  // 获取用户组对应的用户
  getUserByUserGroupId(userGroupId, params) {
    return request.get({ url: this.serveUrl + 'userGroup/' + userGroupId, params })
  },
  // 获取用户对应的用户组
  getUserGroup(id) {
    return request.get({ url: this.serveUrl + id + '/getUserGroup' })
  },
  // 保存用户对应的用户组列表
  saveUserGroup(id, userGroupIdList) {
    return request.put({
      url: this.serveUrl + id + '/saveUserGroup',
      data: userGroupIdList
    })
  },

  // 登录
  login(username, password) {
    return request.post({ url: this.serveUrl + 'login', params: { username, password } })
  },
  // 注销
  logout() {
    return request.post({ url: this.serveUrl + 'logout' })
  },
  // 根据id列表查询用户信息
  getUser(idList) {
    return request.get({ url: this.serveUrl + 'getUser', params: idList })
  },
  // 下载导入模板
  downloadImportTemplate() {
    return request.download({ url: this.serveUrl + 'downloadImportTemplate' })
  },
  // 导入
  import(formData) {
    return request.upload({ url: this.serveUrl + 'importExcel', data: formData })
  },
  // 导出
  export(params) {
    return request.download({ url: this.serveUrl + 'exportExcel', params })
  }
  // // 高级查询初始化
  // initAdvanceQuery() {
  //   return request.get({ url: this.serveUrl + 'initAdvanceQuery' })
  // },
  // // 高级查询-分页
  // advanceQueryPage(params) {
  //   return request.get({ url: this.serveUrl + 'advanceQueryPage', params)
  // }
})

// 权限项
export const permissionItem = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'permissionItem' + '/',
  tree() {
    return request.get({ url: this.serveUrl + 'tree' })
  },
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  }
})

// 用户组
export const userGroup = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'userGroup' + '/',
  tree() {
    return request.get({ url: this.serveUrl + 'tree' })
  },
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  },

  // 批量移除用户
  batchRemoveUser(id, idList) {
    return request.put({ url: this.serveUrl + id + '/user', data: idList })
  },
  // 新增用户
  addUser(id, idList) {
    return request.post({ url: this.serveUrl + id + '/user', data: idList })
  },
  // 获取权限
  getPermission(id) {
    return request.get({ url: this.serveUrl + id + '/getPermission' })
  },
  // 保存权限
  savePermission(id, permissionIdList) {
    return request.put({ url: this.serveUrl + id + '/savePermission', data: permissionIdList })
  },
  // 根据id列表查询
  getByIdList(idList) {
    return request.get({ url: this.serveUrl + 'getByIdList', params: idList })
  }
})

// 字典类型
export const dictionaryType = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'dictionaryType' + '/',
  tree() {
    return request.get({ url: this.serveUrl + 'tree' })
  },

  // 通过编码获取数据
  getByCode(param) {
    return request.get({ url: this.serveUrl + 'getByCode/' + param })
  },
  // 批量保存
  saveItem(id, itemList) {
    return request.post({ url: this.serveUrl + id + '/item', data: itemList })
  },
  // 通过编码获取字典项，转换为列表项数据结构，用于公用选择控件
  getItem(code) {
    return request.get({ url: this.serveUrl + 'getItem?code=' + code })
  }
})

// 字典项
export const dictionaryItem = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'dictionaryItem' + '/',
  enable(id) {
    return request.put({ url: this.serveUrl + id + '/enable' })
  },
  disable(id) {
    return request.put({ url: this.serveUrl + id + '/disable' })
  }
})
// 用户设置
export const userProfile = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'userProfile' + '/',
  get() {
    return request.get({ url: this.serveUrl })
  }
})

// 系统管理
export const systemManage = Object.assign(
  {},
  {
    serveUrl: '/' + moduleName + '/' + 'systemManage' + '/',
    // 重建系统缓存
    rebuildSystemCache() {
      return request.put({ url: this.serveUrl + 'rebuildSystemCache' })
    }
  }
)

// 模块
export const module = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'module' + '/'
})
