import { defineStore } from 'pinia'
import { store } from '../index'

interface NotificationState {
  // 未读消息数量
  unreadMessageCount: number
}

export const useNotificationStore = defineStore('notification', {
  state: (): NotificationState => {
    return {
      unreadMessageCount: 0
    }
  },
  getters: {
    getUnreadMessageCount(): number {
      return this.unreadMessageCount
    }
  },
  actions: {
    setUnreadMessageCount(unreadMessageCount: number) {
      this.unreadMessageCount = unreadMessageCount
    },
    increaseUnreadMessageCount() {
      this.unreadMessageCount = this.unreadMessageCount + 1
    },
    decreaseUnreadMessageCount() {
      this.unreadMessageCount = this.unreadMessageCount - 1
    }
  }
})

export const useNotificationStoreWithOut = () => {
  return useNotificationStore(store)
}
