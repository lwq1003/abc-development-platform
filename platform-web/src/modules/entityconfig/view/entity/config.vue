<template>
  <el-container class="layout-container-demo" style="height: 100%">
    <el-aside width="200px">
      <el-menu default-active="2" class="el-menu-vertical-demo" @select="menuClick">
        <el-menu-item index="1" v-show="false">
          <el-icon><Tickets /></el-icon>
          <span>基本信息</span>
        </el-menu-item>
        <el-menu-item index="2">
          <el-icon><Coin /></el-icon>
          <span>数据模型</span>
        </el-menu-item>
        <el-menu-item index="3">
          <el-icon><Postcard /></el-icon>
          <span>视图</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main
      ><EntityModel v-show="entityModelVisible" /> <EntityView v-show="entityViewVisible"
    /></el-main>
  </el-container>
</template>

<script>
import EntityModel from '@/modules/entityconfig/view/entityModel/list.vue'
import EntityView from '@/modules/entityconfig/view/entityView/list.vue'
export default {
  name: 'c' + 'onfigEntity',
  components: {
    EntityModel,
    EntityView
  },
  data() {
    return {
      entityModelVisible: true,
      entityViewVisible: false
    }
  },
  methods: {
    menuClick(index) {
      if (index === '2') {
        this.entityModelVisible = true
        this.entityViewVisible = false
      } else if (index == '3') {
        this.entityModelVisible = false
        this.entityViewVisible = true
      }
    }
  }
}
</script>

<style scoped>
.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: white;
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  right: 20px;
  display: inline-flex;
  height: 100%;
  align-items: center;
  justify-content: center;
}
</style>
