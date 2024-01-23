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
      <el-form-item label="视图类型" prop="entityViewType">
        <dictionary-select
          v-model="entityData.entityViewType"
          code="EntityViewType"
          @my-change="entityViewTypeChange"
        />
      </el-form-item>
      <el-form-item label="实体模型" prop="entityModel">
        <EntityModelReference
          v-model="entityData.entityModel"
          :entityModel-param="entityModelParam"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="entityData.code" />
      </el-form-item>
      <el-form-item
        label="主视图"
        prop="mainViewFlag"
        v-show="entityData.entityViewType === 'LIST' || entityData.entityViewType === 'TREE_LIST'"
      >
        <dictionary-radio-group v-model="entityData.mainViewFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item
        label="主参照视图"
        prop="mainReferenceViewFlag"
        v-show="
          entityData.entityViewType === 'REFERENCE' ||
          entityData.entityViewType === 'TREE_REFERENCE' ||
          entityData.entityViewType === 'TREE_MULTIPLE_REFERENCE' ||
          entityData.entityViewType === 'TREE_LIST_REFERENCE' ||
          entityData.entityViewType === 'TREE_LIST_MULTIPLE_REFERENCE'
        "
      >
        <dictionary-radio-group v-model="entityData.mainReferenceViewFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="排序" prop="orderNo">
        <el-input v-model="entityData.orderNo" />
      </el-form-item>
      <el-form-item label="初始化前" prop="beforeInit">
        <el-input v-model="entityData.beforeInit" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item label="初始化后" prop="afterInit">
        <el-input v-model="entityData.afterInit" type="textarea" rows="4" />
      </el-form-item>
      <span v-show="entityData.entityViewType === 'ADD' || entityData.entityViewType === 'MODIFY'">
        <el-form-item label="验证数据" prop="validateData">
          <el-input v-model="entityData.validateData" type="textarea" rows="4" />
        </el-form-item>
        <el-form-item label="保存前" prop="beforeSave">
          <el-input v-model="entityData.beforeSave" type="textarea" rows="4" />
        </el-form-item>
        <el-form-item label="保存后" prop="afterSave">
          <el-input v-model="entityData.afterSave" type="textarea" rows="4" />
        </el-form-item>
      </span>
      <el-form-item
        label="通用参数变更"
        prop="commonParamChange"
        v-show="
          entityData.entityViewType === 'LIST' ||
          entityData.entityViewType === 'REFERENCE' ||
          entityData.entityViewType === 'TREE_LIST_REFERENCE'
        "
      >
        <el-input v-model="entityData.commonParamChange" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item
        label="树路径"
        prop="treePath"
        v-show="
          entityData.entityViewType === 'TREE_LIST' ||
          entityData.entityViewType === 'TREE_LIST_REFERENCE' ||
          entityData.entityViewType === 'TREE_LIST_MULTIPLE_REFERENCE'
        "
      >
        <el-input v-model="entityData.treePath" />
      </el-form-item>
      <el-form-item
        label="启用高级配置"
        prop="enableAdvanceConfig"
        v-show="
          entityData.entityViewType === 'ADD' ||
          entityData.entityViewType === 'MODIFY' ||
          entityData.entityViewType === 'VIEW'
        "
      >
        <dictionary-radio-group v-model="entityData.enableAdvanceConfig" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="entityData.remark" />
      </el-form-item>
      <el-form-item label="实体" prop="entity" v-show="false">
        <el-input v-model="entityData.entity" />
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
import EntityModelReference from '@/modules/entityconfig/view/entityModel/reference.vue'

const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'entityView'
export default {
  name: ENTITY_TYPE + '-modify',
  components: {
    EntityModelReference
  },
  mixins: [modifyMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      // 实体模型组件参数，用于传递数据
      entityModelParam: {},
      rules: {
        //前端验证规则
        entityViewType: [{ required: true, message: '【视图类型】不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }],
        code: [{ required: true, message: '【编码】不能为空', trigger: 'blur' }],
        entityModel: [{ required: true, message: '【实体模型】不能为空', trigger: 'blur' }],
        entity: [{ required: true, message: '【实体】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    afterInit(param) {
      this.entityModelParam = { entity: this.entityData.entity }
    },
    entityViewTypeChange(value, name) {
      this.entityData.code = this.$StringUtil.toCamelCase(value)
      this.entityData.name = name
    }
  }
}
</script>

<style></style>
