<template>
  <Dialog title="修改" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0 auto"
    >
      <!--表单区域 -->
      <el-form-item label="视图" prop="view" v-show="false">
        <el-input v-model="entityData.view" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item label="数据类型" prop="dataType">
        <dictionary-select v-model="entityData.dataType" code="EntityModelPropertyType" />
      </el-form-item>
      <el-form-item
        v-show="entityData.dataType == $constant.DATA_TYPE.DATA_DICTIONARY"
        label="字典类型"
        prop="dictionaryType"
      >
        <data-dictionary-select v-model="entityData.dictionaryType" />
      </el-form-item>
      <el-form-item label="宽度" prop="width">
        <el-input v-model="entityData.width" />
      </el-form-item>
      <el-form-item label="是否支持排序" prop="sortableFlag">
        <dictionary-radio-group v-model="entityData.sortableFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="格式化方法" prop="formatFunction">
        <dictionary-select v-model="entityData.formatFunction" code="TableColumnFormatMethod" />
      </el-form-item>
      <el-form-item label="是否悬浮显示" prop="showOverflowTooltipFlag">
        <dictionary-radio-group v-model="entityData.showOverflowTooltipFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="是否显示" prop="showFlag">
        <dictionary-radio-group v-model="entityData.showFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="save" v-permission="pageCode + 'modify'">保存</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { modifyMixin } from '@/mixin/modifyMixin.js'

const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'viewQueryResult'
export default {
  name: ENTITY_TYPE + '-modify',
  components: {},
  mixins: [modifyMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      rules: {
        //前端验证规则
        view: [{ required: true, message: '【视图】不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }],
        code: [{ required: true, message: '【编码】不能为空', trigger: 'blur' }],
        dataType: [{ required: true, message: '【数据类型】不能为空', trigger: 'blur' }],
        sortableFlag: [{ required: true, message: '【是否支持排序】不能为空', trigger: 'blur' }],
        showOverflowTooltipFlag: [
          { required: true, message: '【是否悬浮显示】不能为空', trigger: 'blur' }
        ],
        showFlag: [{ required: true, message: '【是否显示】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {}
}
</script>

<style></style>
