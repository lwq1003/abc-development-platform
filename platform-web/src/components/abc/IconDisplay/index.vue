<script setup lang="ts">
interface Props {
  modelValue?: string
}
defineProps<Props>()
</script>

<template>
  <component :is="modelValue" />
</template>

<style></style>
