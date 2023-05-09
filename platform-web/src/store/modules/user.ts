import { store } from '../index'
import { defineStore } from 'pinia'
import { useCache } from '@/hooks/web/useCache'
import { USER_KEY } from '@/constant/common'
const { wsCache } = useCache()
import { setToken } from '@/utils/auth'

interface UserState {
  account: string
  name: string
  forceChangePasswordFlag: string
  id: string
  token: string
  buttonPermission: string[]
  menuPermission: string[]
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    account: '',
    name: '',
    forceChangePasswordFlag: '',
    id: '',
    token: '',
    buttonPermission: [],
    menuPermission: []
  }),
  getters: {
    getAccount(): string {
      return this.account
    }
  },
  actions: {
    async setUserAction(user) {
      this.account = user.account
      this.name = user.name
      this.forceChangePasswordFlag = user.forceChangePasswordFlag
      this.id = user.id
      this.token = user.token
      this.buttonPermission = user.buttonPermission
      this.menuPermission = user.menuPermission
      // 保存用户信息
      wsCache.set(USER_KEY, user)
      // 保存令牌
      setToken(user.token)
    },
    async clear() {
      wsCache.clear()
      this.resetState()
    },
    resetState() {
      this.account = ''
      this.name = ''
      this.forceChangePasswordFlag = ''
      this.id = ''
      this.token = ''
      this.buttonPermission = []
      this.menuPermission = []
    }
  }
})

export const useUserStoreWithOut = () => {
  return useUserStore(store)
}
