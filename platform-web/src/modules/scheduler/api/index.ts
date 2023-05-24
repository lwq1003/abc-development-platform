import { COMMON_METHOD } from '@/constant/common'
import request from '@/config/axios'

const moduleName = 'scheduler'
// 任务
export const job = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'job' + '/'
})

// 任务参数
export const jobParam = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'jobParam' + '/'
})

// 调度任务
export const schedulerJob = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'schedulerJob' + '/',
  pause(id) {
    return request.put({ url: this.serveUrl + id + '/pause' })
  },
  resume(id) {
    return request.put({ url: this.serveUrl + id + '/resume' })
  },
  pauseAll() {
    return request.put({ url: this.serveUrl + 'pauseAll' })
  },
  resumeAll() {
    return request.put({ url: this.serveUrl + 'resumeAll' })
  },
  execute(id) {
    return request.put({ url: this.serveUrl + id + '/execute' })
  }
})

// 调度任务参数
export const schedulerJobParam = Object.assign({}, COMMON_METHOD, {
  serveUrl: '/' + moduleName + '/' + 'schedulerJobParam' + '/'
})
