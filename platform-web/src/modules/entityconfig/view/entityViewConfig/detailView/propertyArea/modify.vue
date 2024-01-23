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
        <dictionary-select
          @change="dataTypeChange"
          v-model="entityData.dataType"
          code="EntityModelPropertyType"
        />
      </el-form-item>
      <el-form-item
        v-show="entityData.dataType == $constant.DATA_TYPE.DATA_DICTIONARY"
        label="字典类型"
        prop="dictionaryType"
      >
        <data-dictionary-select
          v-model="entityData.dictionaryType"
          @change="dictionaryTypeChange"
        />
      </el-form-item>
      <el-form-item
        label="控件类型"
        prop="widgetType"
        v-show="entityData.dataType != 'ICON' && entityData.dataType != 'SERIAL_NO'"
      >
        <dictionary-select v-model="entityData.widgetType" :code="widgetType" />
      </el-form-item>
      <el-form-item
        v-show="entityData.dataType == $constant.DATA_TYPE.DATETIME"
        label="显示格式"
        prop="formatPattern"
      >
        <dictionary-select v-model="entityData.formatPattern" :code="$constant.DATETIME_FORMAT" />
      </el-form-item>
      <el-form-item label="是否只读" prop="readonlyFlag">
        <dictionary-radio-group v-model="entityData.readonlyFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="默认值" prop="defaultValue">
        <el-input
          v-show="entityData.dataType != $constant.DATA_TYPE.DATA_DICTIONARY"
          v-model="entityData.defaultValue"
        />
        <dictionary-select
          v-show="entityData.dataType == $constant.DATA_TYPE.DATA_DICTIONARY"
          v-model="entityData.defaultValue"
          :code="entityData.dictionaryType"
        />
      </el-form-item>
      <el-form-item label="是否显示" prop="showFlag">
        <dictionary-radio-group v-model="entityData.showFlag" code="ShowControl" />
      </el-form-item>
      <el-form-item
        label="显示表达式"
        prop="showExpression"
        v-show="entityData.showFlag == 'CUSTOM'"
      >
        <el-input v-model="entityData.showExpression" />
      </el-form-item>
      <el-form-item label="是否必填" prop="requireFlag">
        <dictionary-radio-group v-model="entityData.requireFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="save" v-permission="pageCode + 'add'">保存</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { modifyMixin } from '@/mixin/modifyMixin.js'
import util from '@/modules/entityconfig/util/util.js'
const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'viewProperty'
export default {
  name: ENTITY_TYPE + '-modify',
  mixins: [modifyMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      widgetType: 'TextDisplayComponent',
      rules: {
        //前端验证规则
        view: [{ required: true, message: '【视图】不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }],
        code: [{ required: true, message: '【编码】不能为空', trigger: 'blur' }],
        dataType: [{ required: true, message: '【数据类型】不能为空', trigger: 'blur' }],
        widgetType: [{ required: true, message: '【控件类型】不能为空', trigger: 'blur' }],
        readonlyFlag: [{ required: true, message: '【是否只读】不能为空', trigger: 'blur' }],
        showFlag: [{ required: true, message: '【是否显示】不能为空', trigger: 'blur' }],
        requireFlag: [{ required: true, message: '【是否必填】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    afterInit() {
      const widgetType = util.getWidgetType(this.entityData.dataType)
      // 数值类公用控件类型，不相等时再置空
      if (widgetType != this.widgetType) {
        this.entityData.widgetType = ''
        //根据数据类型获取控件类型列表
        this.widgetType = widgetType
      }
    },
    dictionaryTypeChange() {
      this.entityData.defaultValue = ''
    },
    dataTypeChange() {
      // 清空字典类型选中值
      this.entityData.dictionaryType = ''
      // 清空默认值
      this.entityData.defaultValue = ''
      // 获取数据类型对应的控件类型
      const widgetType = util.getWidgetType(this.entityData.dataType)
      // 数值类公用控件类型，不相等时再置空
      if (widgetType != this.widgetType) {
        this.entityData.widgetType = ''
        //根据数据类型获取控件类型列表
        this.widgetType = widgetType
      }
    },
    validateData() {
      if (
        this.entityData.dataType === this.$constant.DATA_TYPE.DATA_DICTIONARY &&
        !this.entityData.dictionaryType
      ) {
        this.$message.warning('请选择字典类型')
        return false
      }

      if (
        this.entityData.dataType != 'ICON' &&
        this.entityData.dataType != 'SERIAL_NO' &&
        !this.entityData.widgetType
      ) {
        this.$message.warning('请选择控件类型')
        return false
      }

      if (
        this.entityData.dataType === this.$constant.DATA_TYPE.DATETIME &&
        !this.entityData.formatPattern
      ) {
        this.$message.warning('请选择显示格式')
        return false
      }

      if (this.entityData.showFlag === 'CUSTOM' && !this.entityData.showExpression) {
        this.$message.warning('【显示表达式】不能为空')
        return false
      }

      return true
    }
  }
}
</script>

<style></style>
