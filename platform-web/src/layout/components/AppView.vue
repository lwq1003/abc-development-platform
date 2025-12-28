<script setup lang="ts">
import { useTagsViewStore } from '@/store/modules/tagsView'
import { useAppStore } from '@/store/modules/app'
import { Footer } from '@/components/Footer'
import { ElDialog } from 'element-plus'
import { useCache } from '@/hooks/web/useCache'
import { ref, computed, onMounted } from 'vue'
import ChangePassword from '@/components/abc/ChangePassword/index.vue'

import { USER_KEY } from '@/constant/common'
const { wsCache } = useCache()
// 修改密码对话框可见性
const visible = ref(false)
// 生命周期钩子
onMounted(() => {
  if (wsCache.get(USER_KEY).forceChangePasswordFlag === 'YES') {
    visible.value = true
  }
})

const beforeClose = (done) => {
  if (wsCache.get(USER_KEY).forceChangePasswordFlag === 'NO') {
    done()
  }
}

const appStore = useAppStore()

const layout = computed(() => appStore.getLayout)

const fixedHeader = computed(() => appStore.getFixedHeader)

const footer = computed(() => appStore.getFooter)

const tagsViewStore = useTagsViewStore()

const getCaches = computed((): string[] => {
  return tagsViewStore.getCachedViews
})
</script>

<template>
  <section
    :class="[
      'p-[var(--app-content-padding)] w-[100%] bg-[var(--app-content-bg-color)] dark:bg-[var(--el-bg-color)]',
      {
        '!min-h-[calc(100%-var(--app-footer-height))]':
          fixedHeader && (layout === 'classic' || layout === 'topLeft') && footer,

        '!min-h-[calc(100%-var(--tags-view-height)-var(--top-tool-height)-var(--app-footer-height))]':
          ((!fixedHeader && layout === 'classic') || layout === 'top') && footer,

        '!min-h-[calc(100%-var(--tags-view-height)-var(--app-footer-height))]':
          !fixedHeader && layout === 'topLeft' && footer,

        '!min-h-[calc(100%-var(--top-tool-height))]': fixedHeader && layout === 'cutMenu' && footer,

        '!min-h-[calc(100%-var(--top-tool-height)-var(--tags-view-height))]':
          !fixedHeader && layout === 'cutMenu' && footer
      }
    ]"
  >
    <router-view>
      <template #default="{ Component, route }">
        <!-- 如果路由配置了外部链接，显示iframe -->
        <template v-if="route.meta && route.meta.externalLink">
          <div class="w-full h-full overflow-hidden">
            <iframe
              :src="route.meta.externalLink"
              frameborder="0"
              class="w-full h-[calc(100vh-var(--tags-view-height)-var(--top-tool-height)-var(--app-footer-height)-40px)]"
              sandbox="allow-scripts allow-same-origin allow-popups allow-forms"
            ></iframe>
          </div>
        </template>
        <!-- 否则显示正常组件 -->
        <template v-else>
          <keep-alive :include="getCaches">
            <component :is="Component" :key="route.fullPath" />
          </keep-alive>
        </template>
      </template>
    </router-view>
  </section>
  <ElDialog
    title="修改密码"
    v-model="visible"
    width="400px"
    destroy-on-close
    lock-scroll
    heigh="500px"
    draggable
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :before-close="beforeClose"
  >
    <ChangePassword @hidden="visible = false" />
  </ElDialog>

  <Footer v-if="footer" />
</template>
