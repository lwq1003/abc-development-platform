<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useAppStore } from '@/store/modules/app'
import { ConfigGlobal } from '@/components/ConfigGlobal'
import { isDark } from '@/utils/is'
import { useDesign } from '@/hooks/web/useDesign'
import { useCache } from '@/hooks/web/useCache'
import useGetGlobalProperties from '@/hooks/useGlobal'
const globalProperties = useGetGlobalProperties()
const { getPrefixCls } = useDesign()

const prefixCls = getPrefixCls('app')

const appStore = useAppStore()

const currentSize = computed(() => appStore.getCurrentSize)

const greyMode = computed(() => appStore.getGreyMode)

const { wsCache } = useCache()

// 根据浏览器当前主题设置系统主题色
const setDefaultTheme = () => {
  if (wsCache.get('isDark') !== null) {
    appStore.setIsDark(wsCache.get('isDark'))
    return
  }
  const isDarkTheme = isDark()
  appStore.setIsDark(isDarkTheme)
}

setDefaultTheme()

onMounted(() => {
  if (import.meta.env.VITE_NOTIFICATION_TYPE === 'WebSocket') {
    globalProperties.$webSocket.init()
  } else {
    globalProperties.$sse.connect()
  }
})
</script>

<template>
  <ConfigGlobal :size="currentSize">
    <RouterView :class="greyMode ? `${prefixCls}-grey-mode` : ''" />
  </ConfigGlobal>
</template>

<style lang="less">
@prefix-cls: ~'@{namespace}-app';

.size {
  width: 100%;
  height: 100%;
}

html,
body {
  padding: 0 !important;
  margin: 0;
  overflow: hidden;
  .size;

  #app {
    .size;
  }
}

.@{prefix-cls}-grey-mode {
  filter: grayscale(100%);
}

// el-card样式
.el-card {
  overflow: hidden;
  color: #303133;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 0;
  transition: 0.3s;

  .el-card__header {
    padding: 9px 10px;
    font-size: 14px;
    color: #006eff;
    border-bottom: 1px solid #ddd;
    box-sizing: border-box;
  }
}

.el-form .el-form-item {
  margin-bottom: 20px;
}

.content-slot .el-form .el-form-item {
  margin-bottom: 20px;
}

.el-form-item .el-input {
  width: 220px;
}
.el-form-item .el-input-number {
  width: 220px;
}
</style>
