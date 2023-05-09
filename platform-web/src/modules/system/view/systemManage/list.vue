<template>
  <el-container>
    <el-main>
      <el-button type="primary" @click="rebuildSystemCache">重建缓存</el-button>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: ENTITY_TYPE,
  data() {
    return {}
  },
  methods: {
    rebuildSystemCache() {
      this.$api.system.systemManage.rebuildSystemCache()
    }
  }
}
</script>

<style></style>
