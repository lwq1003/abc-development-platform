<template>
  <div class="w-full">
    <UserProfile ref="userProfile" @refresh="init" />
    <div id="content">
      <grid-layout
        ref="gridlayout"
        v-model:layout="layout"
        :col-num="12"
        :row-height="30"
        :is-draggable="false"
        :is-resizable="false"
        :vertical-compact="true"
        :use-css-transforms="true"
        style="height: 700px"
      >
        <grid-item
          ref="gridItem"
          :key="item.i"
          v-for="item in layout"
          :x="item.x"
          :y="item.y"
          :w="item.w"
          :h="item.h"
          :i="item.i"
        >
          <PortletContainer :id="item.i" v-model:config="item.config" />
        </grid-item>
      </grid-layout>
    </div>
  </div>
</template>

<script>
import PortletContainer from '@/components/abc/PortletContainer/index.vue'
import UserProfile from '@/modules/system/view/userProfile/modify.vue'
import { GridLayout, GridItem } from 'vue-grid-layout'
export default {
  components: {
    GridLayout,
    GridItem,
    PortletContainer,
    UserProfile
  },
  data() {
    return {
      layout: []
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      // 获取类别
      this.$api.system.userProfile.get().then((res) => {
        if (res.data.desktopConfig) {
          this.layout = JSON.parse(res.data.desktopConfig)
        } else {
          // 提示跳转配置
          this.$confirm('尚未配置个性化桌面, 是否现在进行配置?', '确认', {
            type: 'warning'
          })
            .then(() => {
              this.$refs.userProfile.customInit()
            })
            .catch(() => {
              this.$message.info('已取消')
            })
        }
      })
    }
  }
}
</script>

<style scoped></style>
