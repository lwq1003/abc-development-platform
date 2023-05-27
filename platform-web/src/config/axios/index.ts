import { service } from './service'

import { config } from './config'
import { ElMessage } from 'element-plus'
import { getToken } from '@/utils/auth'
const { default_headers } = config

const request = (option: any) => {
  const { url, method, params, data, headersType, responseType } = option
  return service({
    url: url,
    method,
    params,
    data,
    responseType: responseType,
    headers: {
      'Content-Type': headersType || default_headers
    }
  })
}
// 处理显示信息
const processShowInfo = (option, res) => {
  // 未定义时，默认显示提示；明确设置为false时，不显示提示
  if (
    option.params === undefined ||
    option.params.showInfo === undefined ||
    option.params.showInfo === true
  ) {
    ElMessage.info(res.data.message)
  }
}
export default {
  get: (option: any) => {
    return new Promise((resolve, reject) => {
      request({ method: 'get', ...option })
        .then((res) => {
          // 明确设置为true时，显示提示
          if (option.params && option.params.showInfo === true) {
            ElMessage.info(res.data.message)
          }
          resolve(res.data)
        })
        .catch((err) => {
          reject(err)
        })
    })
  },
  post: (option: any) => {
    return new Promise((resolve, reject) => {
      request({ method: 'post', ...option })
        .then((res) => {
          processShowInfo(option, res)
          resolve(res.data)
        })
        .catch((err) => {
          reject(err)
        })
    })
  },
  delete: (option: any) => {
    return new Promise((resolve, reject) => {
      request({ method: 'delete', ...option })
        .then((res) => {
          processShowInfo(option, res)
          resolve(res.data)
        })
        .catch((err) => {
          reject(err)
        })
    })
  },
  put: (option: any) => {
    return new Promise((resolve, reject) => {
      request({ method: 'put', ...option })
        .then((res) => {
          processShowInfo(option, res)
          resolve(res.data)
        })
        .catch((err) => {
          reject(err)
        })
    })
  },
  // 下载
  download: (option: any) => {
    const link = document.createElement('a')
    link.href = import.meta.env.VITE_BASE_URL + option.url + '?X-Token=' + getToken()
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
  },
  // 上传
  upload: (option: any) => {
    return new Promise((resolve, reject) => {
      option.headersType = 'multipart/form-data'
      request({ method: 'post', ...option })
        .then((res) => {
          processShowInfo(option, res)
          resolve(res.data)
        })
        .catch((err) => {
          reject(err)
        })
    })
  }
}
