<template>
  <Dialog title="查看" v-model="visible" width="40%">
    <el-form
      ref="form"
      :model="entityData"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0 auto"
    >
      <!--表单区域 -->
      <el-form-item label="字典类型" prop="dictionaryType">
        <DictionaryTypeReference
          v-model="entityData.dictionaryType"
          :dictionaryType-param="dictionaryTypeParam"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <dictionary-radio-group v-model="entityData.status" code="Status" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'
import DictionaryTypeReference from '@/modules/system/view/dictionaryType/treereference.vue'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'dictionaryItem'
export default {
  name: ENTITY_TYPE + '-view',
  components: {
    DictionaryTypeReference
  },
  mixins: [viewMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 字典类型组件参数，用于传递数据
      dictionaryTypeParam: {},
      entityData: {}
    }
  },
  methods: {}
}
</script>

<style></style>
