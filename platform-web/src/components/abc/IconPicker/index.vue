<script setup lang="ts">
import { ComponentInternalInstance, getCurrentInstance } from 'vue'

const {
  appContext: {
    app: {
      config: { globalProperties }
    }
  }
} = getCurrentInstance() as ComponentInternalInstance

interface Props {
  modelValue?: string
}
defineProps<Props>()

const emits = defineEmits(['update:modelValue'])
</script>

<template>
  <el-popover trigger="focus" :width="256">
    <template #reference>
      <el-button :icon="modelValue">{{ modelValue }}</el-button>
    </template>
    <div class="el-icon-picker">
      <component
        v-for="icon in globalProperties.$icons"
        :key="icon"
        :class="[icon, 'icon', { 'icon-active': icon == modelValue }]"
        :is="icon"
        @click="emits('update:modelValue', icon)"
      />
    </div>
  </el-popover>
</template>

<style scoped>
.el-icon-picker {
  display: flex;
  height: 256px;
  overflow-y: scroll;
  justify-content: space-around;
  flex-wrap: wrap;
}

.icon {
  display: inline-block;
  width: 24px;
  height: 24px;
  margin: 5px;
  font-size: 20px;
  line-height: 45px;
  color: var(--el-text-color-regular);
  text-align: center;
  cursor: pointer;
  border-radius: 4px;
}

.icon:hover {
  color: var(--el-color-primary);
}

.icon-active {
  color: var(--el-color-primary);
}
</style>
