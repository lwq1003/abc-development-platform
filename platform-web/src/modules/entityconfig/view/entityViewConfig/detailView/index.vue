<template>
  <el-row :gutter="20" type="flex">
    <el-col :span="5">
      <property-list
        @get-all-property-data="getAllPropertyData"
        @refresh-property="refreshProperty"
    /></el-col>

    <el-col :span="9">
      <property-area ref="property" :property-list-data="allPropertyList" />
    </el-col>
    <!-- <el-col :span="5">
      <page-button :button-type="$constant.VIEW_BUTTON_TYPE_ITEM.MODIFY_PAGE"
    /></el-col> -->
  </el-row>
</template>

<script>
import PropertyArea from './propertyArea/index.vue'
// import PageButton from '../buttonArea/index.vue'
import PropertyList from './propertyList/index.vue'

const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'entityViewConfig'
export default {
  name: ENTITY_TYPE,
  components: {
    PropertyArea,
    PropertyList
    // , PageButton
  },

  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // 属性列表
      allPropertyList: []
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
