import { service } from './service'

import { config } from './config'
import { ElMessage } from 'element-plus'

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
  //   .then((res) => {
  //   // 未定义时，默认显示提示；明确设置为false时，不显示提示
  //   if (showInfo === undefined || showInfo === true) {
  //     ElMessage.info(res.data.message)
  //   }
  // })
}
export default {
  get: (option: any) => {
    return new Promise((resolve, reject) => {
      request({ method: 'get', ...option })
        .then((res) => {
          // 明确设置为true时，显示提示
          if (option.showInfo === true) {
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
          // 未定义时，默认显示提示；明确设置为false时，不显示提示
          if (option.showInfo === undefined || option.showInfo === true) {
            ElMessage.info(res.data.message)
          }
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
          // 未定义时，默认显示提示；明确设置为false时，不显示提示
          if (option.showInfo === undefined || option.showInfo === true) {
            ElMessage.info(res.data.message)
          }
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
          // 未定义时，默认显示提示；明确设置为false时，不显示提示
          if (option.showInfo === undefined || option.showInfo === true) {
            ElMessage.info(res.data.message)
          }
          resolve(res.data)
        })
        .catch((err) => {
          reject(err)
        })
    })
  }
}
