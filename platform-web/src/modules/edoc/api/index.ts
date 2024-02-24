import { COMMON_METHOD } from '@/constant/common'
import request from '@/config/axios'
import qs from 'qs'

const moduleName = 'edoc'

// 文件夹
export const folder = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'folder' + '/',
  tree() {
    return request.get({ url: this.serveUrl + 'tree' })
  },
  // 更名
  rename(id, name) {
    const data = { name }
    return request.put({ url: this.serveUrl + id + '/rename?' + qs.stringify(data) })
  },
  // 复制
  copy(srcId, targetId) {
    const data = { srcId, targetId }
    return request.post({ url: this.serveUrl + 'copy?' + qs.stringify(data) })
  },
  // 移动
  move(srcId, targetId, retainPermission) {
    const params = { srcId, targetId, retainPermission }
    return request.put({ url: this.serveUrl + 'move?' + qs.stringify(params) })
  },
  // 获取子文件夹及文档
  getChildren(id, name, ignoreParent, sortInfo) {
    const data = Object.assign({ name, ignoreParent }, sortInfo)
    return request.get({
      url: this.serveUrl + id + '/getChildren',
      params: data
    })
  },
  // 混合删除
  mixRemove(data) {
    return request.post({ url: this.serveUrl + 'mixRemove', data })
  },
  // 混合复制
  mixCopy(documentList, targetFolderId) {
    const data = { data: documentList, targetFolderId }
    return request.post({
      url: this.serveUrl + 'mixCopy',
      data: data
    })
  },
  // 混合移动
  mixMove(documentList, targetFolderId, retainPermission) {
    return request.put({
      url: this.serveUrl + 'mixMove',
      data: {
        data: documentList,
        targetFolderId,
        retainPermission
      }
    })
  }
})

// 文档
export const document = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'document' + '/',
  // 上传操作内置于vue-simple-uploader中，下面操作当前未使用
  upload() {
    return request.post({ url: this.serveUrl + 'upload' })
  },
  // 合并文件块
  mergeChunks(param) {
    return request.post({ url: this.serveUrl + 'mergeChunks', data: param })
  },
  // 下载
  download(id) {
    return request.download({ url: this.serveUrl + id + '/download' })
  },
  // 更名
  rename(id, name) {
    const data = { name }
    return request.put({ url: this.serveUrl + id + '/rename?' + qs.stringify(data) })
  },
  // 更新,内置于vue-simple-uploader中，下面操作当前未使
  update(params) {
    return request.put({ url: this.serveUrl, data: params })
  },
  // 复制
  copy(documentId, targetFolderId) {
    const data = { documentId, targetFolderId }
    return request.post({ url: this.serveUrl + 'copy?' + qs.stringify(data) })
  },
  // 移动
  move(documentId, targetFolderId) {
    const params = { documentId, targetFolderId }
    return request.put({ url: this.serveUrl + 'move?' + qs.stringify(params) })
  },
  // 锁定
  lock(id) {
    return request.put({ url: this.serveUrl + id + '/lock' })
  },
  // 解锁
  unlock(id) {
    return request.put({ url: this.serveUrl + id + '/unlock' })
  },
  // 获取访问令牌
  getToken(id) {
    return request.get({ url: this.serveUrl + id + '/getToken' })
  },
  // 获取带时效的访问令牌
  getTokenWithTime(id, validHours) {
    return request.get({ url: this.serveUrl + id + '/getTokenWithTime', params: { validHours } })
  },
  // 下载某一版本
  downloadVersion(id, version) {
    return request.download({ url: this.serveUrl + id + '/downloadVersion?version=' + version })
  },
  // 恢复版本
  restore(id, version) {
    return request.post({ url: this.serveUrl + id + '/restore?' + qs.stringify({ version }) })
  }
})

// 文件夹权限
export const folderPermission = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'folderPermission' + '/',
  // 授权
  grantPermission(params) {
    return request.put({ url: this.serveUrl + 'grantPermission', data: params })
  },
  // 获取某文件夹对应的某授权类型的权限
  getPermission(folderId, objectId, objectType) {
    return request.get({
      url: this.serveUrl + folderId + '/getPermission',
      params: {
        objectId,
        objectType
      }
    })
  },
  // 获取当前用户对某文件夹的权限
  getFolderPermissionForUser(id) {
    return request.get({ url: this.serveUrl + 'getFolderPermissionForUser', params: { id } })
  },
  // 获取当前用户对某文档的权限
  getDocumentPermissionForUser(id) {
    return request.get({ url: this.serveUrl + 'getDocumentPermissionForUser', params: { id } })
  }
})

// 文档版本
export const documentVersion = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'documentVersion' + '/',
  // 获取最新版本信息
  getNewestVersion(documentId) {
    return request.get({ url: this.serveUrl + 'getNewestVersion', params: { documentId } })
  },
  // 获取列表
  getList(documentId) {
    return request.get({ url: this.serveUrl + 'getList', params: { documentId } })
  }
})

// 文档收藏
export const documentFavorite = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'documentFavorite' + '/',
  // 获取收藏列表
  getFavoriteList(name) {
    return request.get({ url: this.serveUrl + 'getFavoriteList?name=' + name })
  }
})

// 全文搜索
export const fullTextSearch = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'fullTextSearch' + '/',
  // 获取收藏列表
  query(searchText, scrollId) {
    return request.get({ url: this.serveUrl + 'query', params: { searchText, scrollId } })
  }
})
