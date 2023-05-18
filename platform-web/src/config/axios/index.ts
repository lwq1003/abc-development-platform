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
  },
  download: (option: any) => {
    request({ method: 'get', responseType: 'blob', ...option })
      .then((res) => {
        const { data, headers } = res
        const fileName = headers['content-disposition'].replace(/\w+;filename=(.*)/, '$1')
        // 此处当返回json文件时需要先对data进行JSON.stringify处理，其他类型文件不用做处理
        const blob = new Blob([data], { type: headers['content-type'] })
        const dom = document.createElement('a')
        const url = window.URL.createObjectURL(blob)
        dom.href = url
        dom.download = decodeURI(fileName)
        dom.style.display = 'none'
        document.body.appendChild(dom)
        dom.click()
        dom.parentNode.removeChild(dom)
        window.URL.revokeObjectURL(url)
      })
      .catch((err) => {
        reject(err)
      })
  },
  upload: (option: any) => {
    return new Promise((resolve, reject) => {
      option.headersType = 'multipart/form-data'
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
  }
}
