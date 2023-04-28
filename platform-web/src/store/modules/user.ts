import { store } from '../index'
import { defineStore } from 'pinia'
import { useCache } from '@/hooks/web/useCache'
import { USER_KEY } from '@/constant/common'
const { wsCache } = useCache()

interface UserState {
  account: string
  name: string
  forceChangePassword: string
  id: string
  token: string
  buttonPermission: string[]
  menuPermission: string[]
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    account: '',
    name: '',
    forceChangePassword: '',
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
      this.forceChangePassword = user.forceChangePassword
      this.id = user.id
      this.token = user.token
      this.buttonPermission = user.buttonPermission
      this.menuPermission = user.menuPermission
      wsCache.set(USER_KEY, user)
    },
    async clear() {
      wsCache.clear()
      this.resetState()
    },
    resetState() {
      this.account = ''
      this.name = ''
      this.forceChangePassword = ''
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
