<script setup lang="ts">
// 外部链接处理组件，用于处理外部链接的路由匹配
// 这个组件不需要实际渲染内容，因为外部链接的内容是在AppView.vue中通过iframe渲染的
</script>

<template>
  <div class="external-link-container">
    <!-- 外部链接内容由AppView.vue中的iframe渲染 -->
  </div>
</template>

<style scoped>
.external-link-container {
  width: 100%;
  height: 100%;
}
</style>
