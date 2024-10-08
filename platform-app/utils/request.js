import store from '@/store'
import config from '@/config'
import {
	getToken,
	removeToken
} from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import {
	toast,
	showConfirm,
	tansParams
} from '@/utils/common'
import {
	METHOD_NOT_ALLOWED,
	NOT_FOUND,
	REQUEST_SUCCESS,
	UNAUTHORIZED
} from '@/constant/index.ts'

let timeout = 10000
const baseUrl = config.baseUrl

const request = config => {
	// 是否需要设置 token
	const isToken = (config.headers || {}).isToken === false
	config.header = config.header || {}
	const token = getToken()
	if (token && !isToken) {
		config.header['X-Token'] = token
	}
	// get请求映射params参数
	if (config.params) {
		let url = config.url + '?' + tansParams(config.params)
		url = url.slice(0, -1)
		config.url = url
	}
	return new Promise((resolve, reject) => {
		uni.request({
				method: config.method || 'get',
				timeout: config.timeout || timeout,
				url: config.baseUrl || baseUrl + config.url,
				data: config.data,
				header: config.header,
				dataType: 'json'
			}).then(response => {			
				let [error, res] = response
				if (error) {
					toast('后端接口连接异常')
					reject('后端接口连接异常')
					return
				}
				const statusCode = res.statusCode
				if (statusCode === REQUEST_SUCCESS) {
					resolve(res.data)
				} else if (statusCode === UNAUTHORIZED) {
					showConfirm('登录状态已过期，您可以继续留在该页面，或者重新登录?').then(res => {
						if (res.confirm) {
							store.dispatch('LogOut').then(res => {
								uni.reLaunch({
									url: '/pages/login'
								})
							})
						}
					})
					reject('无效的会话，或者会话已过期，请重新登录。')
				} else if (statusCode === NOT_FOUND) {
					toast('未找到服务，请确认')
					reject('404')
				} else if (statusCode === METHOD_NOT_ALLOWED) {
					toast('请求的方法不支持，请确认')
					reject('405')
				} else {
					if (res.data.message) {
						toast(res.data.message)
						reject('500')
					} else {
						toast('请求远程服务器失败')
						reject('500')
					}
				}
				
			})
			.catch(error => {				
				let {
					message
				} = error
				if (message === 'Network Error') {
					message = '后端接口连接异常'
				} else if (message.includes('timeout')) {
					message = '系统接口请求超时'
				} else if (message.includes('Request failed with status code')) {
					message = '系统接口' + message.substr(message.length - 3) + '异常'
				}
				toast(message)
				reject(error)
			})
	})
}

export default request