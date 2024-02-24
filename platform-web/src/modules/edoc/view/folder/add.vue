<template>
  <Dialog title="新增" v-model="visible" width="300px">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="60px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="名称" prop="name">
        <el-input v-model="entityData.name" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="save" v-permission="pageCode + 'add'">保存</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { addMixin } from '@/mixin/addMixin.js'
import FolderReference from '@/modules/edoc/view/folder/treeReference.vue'
const MODULE_CODE = 'edoc'
const ENTITY_TYPE = 'folder'
export default {
  name: ENTITY_TYPE + '-add',
  components: {
    FolderReference
  },
  mixins: [addMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      // 上级组件参数，用于传递数据
      folderParam: {},
      rules: {
        //前端验证规则
        name: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    afterInit(param) {
      this.entityData.parentId = param.id
    }
  }
}
</script>

<style></style>
