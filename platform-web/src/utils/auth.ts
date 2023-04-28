import { useCache } from '@/hooks/web/useCache'

import { TOKEN_KEY } from '@/constant/common'

const { wsCache } = useCache()

// 获取token
export const getToken = () => {
  return wsCache.get(TOKEN_KEY) ? wsCache.get(TOKEN_KEY) : ''
}

// 设置token
export const setToken = (token) => {
  wsCache.set(TOKEN_KEY, token)
}

// 删除token
export const removeToken = () => {
  wsCache.delete(TOKEN_KEY)
}
