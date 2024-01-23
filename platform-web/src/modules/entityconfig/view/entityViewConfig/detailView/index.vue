<template>
  <el-row :gutter="20" type="flex">
    <el-col :span="3">
      <property-list
        @get-all-property-data="getAllPropertyData"
        @refresh-property="refreshProperty"
    /></el-col>

    <el-col :span="3">
      <property-area ref="property" :property-list-data="allPropertyList" />
    </el-col>
    <!-- <el-col :span="18">
      <fc-designer v-model="entityData" ref="designer" />
    </el-col> -->
  </el-row>
</template>

<script>
import PropertyArea from './propertyArea/index.vue'

import PropertyList from './propertyList/index.vue'

const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'entityViewConfig'
export default {
  name: ENTITY_TYPE,
  components: {
    PropertyArea,
    PropertyList
  },

  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // 属性列表
      allPropertyList: [],
      //表单数据
      entityData: {}
    }
  },
  methods: {
    // 获取属性列表数据
    getAllPropertyData(data) {
      this.allPropertyList = data
    },
    // 刷新属性
    refreshProperty() {
      this.$refs.property.query()
    }
  }
}
</script>

<style></style>
