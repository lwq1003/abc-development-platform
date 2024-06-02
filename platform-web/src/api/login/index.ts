import request from '@/config/axios'
import type { UserType } from './types'

interface RoleParams {
  roleName: string
}
// 登录
export const loginApi = (data: UserType) => {
  return request.post({
    url: '/system/user/login?username=' + data.username + '&password=' + data.password,
    data
  })
}
// 注册
export const registerApi = (data) => {
  return request.post({
    url: '/system/user/register',
    data
  })
}

export const loginOutApi = (): Promise<IResponse> => {
  return request.get({ url: '/system/user/logout' })
}

export const getUserListApi = ({ params }: AxiosConfig) => {
  return request.get<{
    total: number
    list: UserType[]
  }>({ url: '/user/list', params })
}

export const getAdminRoleApi = (
  params: RoleParams
): Promise<IResponse<AppCustomRouteRecordRaw[]>> => {
  return request.get({ url: '/role/list', params })
}

export const getTestRoleApi = (params: RoleParams): Promise<IResponse<string[]>> => {
  return request.get({ url: '/role/list', params })
}
