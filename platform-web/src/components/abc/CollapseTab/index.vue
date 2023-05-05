<template>
  <el-card class="collapse-tab">
    <div v-show="contentVisible">
      <div class="content-slot">
        <slot></slot>
      </div>
    </div>
    <div class="toggle-button" @click="toggleTab">
      <el-icon><CaretTop v-show="expend" /></el-icon>
      <el-icon><CaretBottom v-show="!expend" /></el-icon>
    </div>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      contentVisible: true,
      expend: true
    }
  },
  methods: {
    toggleTab() {
      this.contentVisible = !this.contentVisible
      this.expend = !this.expend
    }
  }
}
</script>

<style>
.collapse-tab > .el-card__body {
  padding: 0;
}

.content-slot {
  padding: 20px 20px 0;
}

.toggle-button {
  height: 24px;
  font-size: 24px;
  line-height: 24px;
  color: #d3dce6;
  text-align: center;
  cursor: pointer;
}
</style>
