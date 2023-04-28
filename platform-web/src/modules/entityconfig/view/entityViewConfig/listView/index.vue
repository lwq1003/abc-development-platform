<template>
  <el-row :gutter="20" type="flex">
    <el-col :span="5">
      <page-button :button-type="$constant.VIEW_BUTTON_TYPE_ITEM.LIST_PAGE"
    /></el-col>
    <el-col :span="5">
      <property-list
        @get-all-property-data="getAllPropertyData"
        @refresh-query-condition="refreshQueryCondition"
        @refresh-query-result="refreshQueryResult"
    /></el-col>

    <el-col :span="9">
      <el-row :gutter="20" type="flex">
        <el-col :span="24">
          <query-area ref="queryCondition" :property-list-data="allPropertyList"
        /></el-col>
      </el-row>

      <el-row :gutter="20" type="flex">
        <el-col :span="24">
          <result-area ref="queryResult" :property-list-data="allPropertyList"
        /></el-col>
      </el-row>
    </el-col>

    <el-col :span="5">
      <row-button :button-type="$constant.VIEW_BUTTON_TYPE_ITEM.LIST_ROW"
    /></el-col>
  </el-row>
</template>

<script>
import PageButton from '../buttonArea/index.vue'
import QueryArea from './queryArea/index.vue'
import ResultArea from './resultArea/index.vue'
import PropertyList from './propertyList/index.vue'
import RowButton from '../buttonArea/index.vue'
const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'entityViewConfig'
export default {
  name: ENTITY_TYPE,
  components: {
    PageButton,
    PropertyList,
    RowButton,
    QueryArea,
    ResultArea
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
    // 刷新查询条件
    refreshQueryCondition() {
      this.$refs.queryCondition.query()
    },
    // 刷新查询结果
    refreshQueryResult() {
      this.$refs.queryResult.query()
    }
  }
}
</script>

<style></style>
