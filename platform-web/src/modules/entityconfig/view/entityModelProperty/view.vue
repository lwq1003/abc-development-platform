<template>
  <Dialog title="查看" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0 auto"
    >
      <!--表单区域 -->
      <el-form-item label="实体模型" prop="entityModel" v-show="false">
        <el-input v-model="entityData.entityModel" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item label="数据类型" prop="dataType">
        <dictionary-select
          v-model="entityData.dataType"
          code="EntityModelPropertyType"
          @change="dataTypeChange"
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
      <el-form-item label="控件类型" prop="widgetType">
        <dictionary-select v-model="entityData.widgetType" :code="widgetType" />
      </el-form-item>
      <el-form-item
        v-show="entityData.dataType == $constant.DATA_TYPE.DATETIME"
        label="显示格式"
        prop="formatPattern"
      >
        <dictionary-select v-model="entityData.formatPattern" :code="$constant.DATETIME_FORMAT" />
      </el-form-item>
      <el-form-item label="最大长度" prop="maxLength">
        <el-input v-model="entityData.maxLength" />
      </el-form-item>
      <el-form-item
        label="小数位数"
        prop="decimalLength"
        v-show="entityData.dataType == $constant.DATA_TYPE.DECIMAL"
      >
        <el-input v-model="entityData.decimalLength" />
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
      <el-form-item label="是否可为空" prop="nullFlag">
        <dictionary-radio-group v-model="entityData.nullFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="是否唯一" prop="uniqueFlag">
        <dictionary-radio-group v-model="entityData.uniqueFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item
        v-show="entityData.uniqueFlag == $constant.YES"
        label="唯一性参照"
        prop="entitymodelProperty"
      >
        <el-select v-model="entityData.entityModelProperty" class="w-full" clearable>
          <el-option
            v-for="item in propertyList"
            :key="item.code"
            :label="item.name"
            :value="item.code"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否主属性" prop="mainFlag">
        <dictionary-radio-group v-model="entityData.mainFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="是否上级属性" prop="parentPropertyFlag">
        <dictionary-radio-group v-model="entityData.parentPropertyFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="是否库表存储" prop="databaseStoreFlag">
        <dictionary-radio-group v-model="entityData.databaseStoreFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="entityData.remark" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'

const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'entityModelProperty'
export default {
  name: ENTITY_TYPE + '-view',
  mixins: [viewMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      // 实体属性列表
      propertyList: []
    }
  },
  computed: {
    widgetType() {
      // 设置控件类型的数据字典编码
      let code = ''
      switch (this.entityData.dataType) {
        case this.$constant.DATA_TYPE.STRING: {
          code = this.$constant.WIDGET_TYPE.TEXT
          break
        }
        case this.$constant.DATA_TYPE.INTEGER:
        case this.$constant.DATA_TYPE.LONG:
        case this.$constant.DATA_TYPE.DOUBLE:
        case this.$constant.DATA_TYPE.DECIMAL: {
          code = this.$constant.WIDGET_TYPE.NUMBER
          break
        }
        case this.$constant.DATA_TYPE.DATETIME: {
          code = this.$constant.WIDGET_TYPE.DATETIME
          break
        }
        case this.$constant.DATA_TYPE.DATA_DICTIONARY: {
          code = this.$constant.WIDGET_TYPE.DATA_DICTIONARY
          break
        }
        case this.$constant.DATA_TYPE.ENTITY: {
          code = this.$constant.WIDGET_TYPE.ENTITY
          break
        }
        default: {
          code = this.$constant.WIDGET_TYPE.TEXT
          break
        }
      }
      return code
    }
  },
  methods: {
    afterInit() {
      this.loadPropertyList()
    },
    loadPropertyList() {
      this.$api.entityconfig.entityModelProperty
        .getFullPropertyList(this.entityData.entityModel)
        .then((res) => {
          this.propertyList = res.data
        })
    },
    dataTypeChange() {
      // 清空控件类型选中值
      this.entityData.widgetType = ''
      // 清空字典类型选中值
      this.entityData.dictionaryType = ''
    },
    dictionaryTypeChange() {
      this.entityData.defaultValue = ''
    }
  }
}
</script>

<style></style>
