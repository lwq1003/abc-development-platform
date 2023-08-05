<template>
  <el-drawer
    :append-to-body="true"
    title="处理人设置"
    v-model="visible"
    :show-close="false"
    :size="550"
    :before-close="close"
    destroy-on-close
  >
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="模式" prop="mode">
        <dictionary-radio-group v-model="entityData.mode" code="NodeMode" />
      </el-form-item>
      <el-form-item label="指定处理人" prop="setAssigneeFlag">
        <dictionary-radio-group v-model="entityData.setAssigneeFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="用户组" prop="userGroup">
        <UserGroupReference v-model="entityData.userGroup" @my-change="userGroupchange" />
      </el-form-item>
      <el-form-item label="用户组名称" prop="userGroupName" v-show="false">
        <el-input v-model="entityData.userGroupName" />
      </el-form-item>
      <el-form-item style="float: right; margin-top: 20px">
        <el-button type="primary" @click="save">确 定</el-button>
        <el-button @click="close">取 消</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>
<script>
import DictionaryRadioGroup from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'
import UserGroupReference from '@/modules/system/view/userGroup/treeReference.vue'
import { useStore } from '../../stores/index'
let store = useStore()
const MODULE_CODE = 'workflow'
const ENTITY_TYPE = 'workflowNodeConfig'
export default {
  name: ENTITY_TYPE + '-modify',
  components: { DictionaryRadioGroup, UserGroupReference },
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
        mode: [{ required: true, message: '【模式】不能为空', trigger: 'blur' }],
        setAssigneeFlag: [{ required: true, message: '【指定处理人】不能为空', trigger: 'blur' }],
        userGroup: [{ required: true, message: '【用户组】不能为空', trigger: 'blur' }]
      }
    }
  },
  computed: {
    visible() {
      return store.handleNodeConfigVisible
    },
    handleNodeConfig() {
      return store.handleNodeConfig
    }
  },
  watch: {
    handleNodeConfig(value) {
      console.log('watch', value)
      this.entityData = value
    }
  },
  methods: {
    close() {
      store.setHandleNodeConfigVisible(false)
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          const nodeConfig = Object.assign(
            store.handleNodeConfig,
            { ...this.entityData },
            { flag: true }
          )
          store.setHandleNodeConfig(nodeConfig)
          this.close()
        }
      })
    },
    userGroupchange(id, name) {
      this.entityData.userGroupName = name
    }
  }
}
</script>
<style scoped></style>
