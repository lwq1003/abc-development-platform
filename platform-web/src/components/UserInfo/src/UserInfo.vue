<template>
  <div>
    <Dialog title="修改密码" v-model="visible" width="400px">
      <ChangePassword @hidden="visible = false" />
    </Dialog>
    <UserProfile ref="userProfile" />
    <ElDropdown :class="prefixCls" trigger="click">
      <div class="flex items-center">
        <el-avatar :icon="UserFilled" :size="30" />

        <span class="<lg:hidden text-14px pl-[5px] text-[var(--top-header-text-color)]">{{
          wsCache.get(USER_KEY).name
        }}</span>
      </div>

      <template #dropdown>
        <ElDropdownMenu>
          <ElDropdownItem>
            <div @click="changePassword">{{ t('common.changePassword') }}</div>
          </ElDropdownItem>
          <ElDropdownItem>
            <div @click="userSet">用户设置</div>
          </ElDropdownItem>
          <ElDropdownItem divided>
            <div @click="loginOut">{{ t('common.loginOut') }}</div>
          </ElDropdownItem>
        </ElDropdownMenu>
      </template>
    </ElDropdown>
  </div>
</template>

<script setup lang="ts">
import { ElDropdown, ElDropdownMenu, ElDropdownItem, ElMessageBox } from 'element-plus'
import { useI18n } from '@/hooks/web/useI18n'
import { useCache } from '@/hooks/web/useCache'
import { resetRouter } from '@/router'
import { useRouter } from 'vue-router'
import { loginOutApi } from '@/api/login'
import { useDesign } from '@/hooks/web/useDesign'
import { useTagsViewStore } from '@/store/modules/tagsView'
import { Dialog } from '@/components/abc/Dialog'
import ChangePassword from '@/components/abc/ChangePassword/index.vue'
import UserProfile from '@/modules/system/view/userProfile/modify.vue'
import { ref } from 'vue'
import { UserFilled } from '@element-plus/icons-vue'
import { USER_KEY } from '@/constant/common'
import useGetGlobalProperties from '@/hooks/useGlobal'
const globalProperties = useGetGlobalProperties()
const tagsViewStore = useTagsViewStore()

const { getPrefixCls } = useDesign()

const prefixCls = getPrefixCls('user-info')

const { t } = useI18n()

const { wsCache } = useCache()

const { replace } = useRouter()

const visible = ref(false)
const loginOut = () => {
  ElMessageBox.confirm(t('common.loginOutMessage'), t('common.reminder'), {
    confirmButtonText: t('common.ok'),
    cancelButtonText: t('common.cancel'),
    type: 'warning'
  })
    .then(async () => {
      const res = await loginOutApi().catch(() => {})
      if (res) {
        wsCache.clear()
        tagsViewStore.delAllViews()
        // 重置静态路由表
        resetRouter()
        // 关闭通知连接
        if (import.meta.env.VITE_NOTIFICATION_TYPE === 'WebSocket') {
          globalProperties.$webSocket.close()
        } else {
          globalProperties.$sse.close()
        }
        // 调整登录页
        replace('/login')
      }
    })
    .catch(() => {})
}

const changePassword = () => {
  visible.value = true
}

const userProfile = ref(null)
const userSet = () => {
  userProfile.value.customInit()
}
</script>
