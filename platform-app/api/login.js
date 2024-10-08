import request from '@/utils/request'

// 登录方法
export function login(username, password) {

  return request({
    'url': '/system/user/login?username='+username+'&password='+password,  
    'method': 'post'
  })
}

// 退出方法
export function logout() {
  return request({
    'url': '/system/user/logout',
    'method': 'get'
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/system/user/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    'url': '/getInfo',
    'method': 'get'
  })
}



