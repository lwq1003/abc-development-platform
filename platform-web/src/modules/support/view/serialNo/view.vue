<template>
  <Dialog title="查看" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="模块" prop="module">
        <ModuleReference v-model="entityData.module" :module-param="moduleParam" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item label="前缀" prop="prefix">
        <el-input v-model="entityData.prefix" />
      </el-form-item>
      <el-form-item label="日期格式" prop="dateFormat">
        <el-input v-model="entityData.dateFormat" />
      </el-form-item>
      <el-form-item label="长度" prop="length">
        <el-input v-model="entityData.length" />
      </el-form-item>
      <el-form-item label="连接符" prop="connector">
        <el-input v-model="entityData.connector" />
      </el-form-item>
      <el-form-item label="当前值" prop="currentValue">
        <el-input v-model="entityData.currentValue" />
      </el-form-item>
      <el-form-item label="重置策略" prop="resetStrategy">
        <dictionary-select v-model="entityData.resetStrategy" code="SerialNoResetStrategy" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="entityData.remark" type="textarea" rows="4" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'
import ModuleReference from '@/modules/entityConfig/view/module/reference.vue'
const MODULE_CODE = 'support'
const ENTITY_TYPE = 'serialNo'
export default {
  name: ENTITY_TYPE + '-view',
  components: {
    ModuleReference
  },
  mixins: [viewMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 模块组件参数，用于传递数据
      moduleParam: {},
      entityData: {}
    }
  },
  methods: {}
}
</script>

<style></style>
