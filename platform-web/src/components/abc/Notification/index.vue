<template>
  <div @click="showMessageList" style="margin-top: 8px; margin-right: 10px; cursor: pointer">
    <el-badge :value="messageCount">
      <el-icon :size="18">
        <Bell />
      </el-icon>
    </el-badge>
  </div>
</template>

<script>
import { useNotificationStore } from '@/store/modules/notification'
const notificationStore = useNotificationStore()
export default {
  data() {
    return {}
  },
  computed: {
    messageCount() {
      return notificationStore.getUnreadMessageCount
    }
  },
  mounted() {
    this.initUnreadMessageCount()
  },
  methods: {
    showMessageList() {
      this.$router.push({ path: '/notification/systemMessage', query: {} })
    },
    initUnreadMessageCount() {
      this.$api.notification.systemMessage.getUnreadMessageCount().then((res) => {
        notificationStore.setUnreadMessageCount(res.data)
      })
    }
  }
}
</script>

<style scoped></style>
