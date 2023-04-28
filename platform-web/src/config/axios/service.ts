import axios, {
  AxiosInstance,
  InternalAxiosRequestConfig,
  AxiosRequestHeaders,
  AxiosResponse,
  AxiosError
} from 'axios'

import qs from 'qs'

import { config } from './config'

import { ElMessage } from 'element-plus'

import { REQUEST_SUCCESS, UNAUTHORIZED, NOT_FOUND, METHOD_NOT_ALLOWED } from '@/constant/common'

const { base_url } = config

export const PATH_URL = base_url[import.meta.env.VITE_API_BASEPATH]

// 创建axios实例
const service: AxiosInstance = axios.create({
  baseURL: PATH_URL, // api 的 base_url
  timeout: config.request_timeout // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const urlencoded = 'application/x-www-form-urlencoded'
    if (
      config.method === 'post' &&
      (config.headers as AxiosRequestHeaders)['Content-Type'] === urlencoded
    ) {
      config.data = qs.stringify(config.data)
    }
    // ;(config.headers as AxiosRequestHeaders)['Token'] = 'test test'
    // get参数编码
    if (config.method === 'get' && config.params) {
      let url = config.url as string
      url += '?'
      const keys = Object.keys(config.params)
      for (const key of keys) {
        if (config.params[key] !== void 0 && config.params[key] !== null) {
          url += `${key}=${encodeURIComponent(config.params[key])}&`
        }
      }
      url = url.substring(0, url.length - 1)
      config.params = {}
      config.url = url
    }
    return config
  },
  (error: AxiosError) => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  (response: AxiosResponse<any>) => {
    if (response.config.responseType === 'blob') {
      // 如果是文件流，直接过
      return response
    } else if (response.status === REQUEST_SUCCESS) {
      return new Promise((resolve) => {
        // 若为成功请求，直接返回业务数据
        if (response.status === REQUEST_SUCCESS) {
          resolve(response)
        }
      })
      return response.data
    } else {
      ElMessage.error(response.data.message)
    }
  },
  (error: AxiosError) => {
    if (error.response) {
      if (error.response.status === UNAUTHORIZED) {
        ElMessage.warning('未登录或会话超时，请重新登录')
      } else if (error.response.status === NOT_FOUND) {
        ElMessage.error('未找到服务，请确认')
      } else if (error.response.status === METHOD_NOT_ALLOWED) {
        ElMessage.error('请求的方法不支持，请确认')
      } else {
        ElMessage.error(error.response.data.message)
      }
      return Promise.reject(error)
    } else {
      ElMessage.error('请求远程服务器失败')
    }
  }
)

export { service }
