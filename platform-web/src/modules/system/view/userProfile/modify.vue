<template>
  <Dialog title="修改" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="用户" prop="user" v-show="false">
        <el-input v-model="entityData.user" />
      </el-form-item>
      <el-form-item label="语种" prop="language">
        <dictionary-select v-model="entityData.language" code="Language" />
      </el-form-item>
      <el-form-item label="时区" prop="timeZone">
        <dictionary-select v-model="entityData.timeZone" code="TimeZone" />
      </el-form-item>
      <el-form-item label="桌面配置" prop="desktopConfig">
        <ConfigDesktop v-model="entityData.desktopConfig" />
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
import ConfigDesktop from '@/modules/support/view/desktopTemplate/configDesktop.vue'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'userProfile'
export default {
  name: ENTITY_TYPE + '-modify',
  components: { ConfigDesktop },
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
        language: [{ required: true, message: '【语种】不能为空', trigger: 'blur' }],
        timeZone: [{ required: true, message: '【时区】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    // 初始化
    customInit() {
      this.api.get().then((res) => {
        this.entityData = res.data
        this.visible = true
      })
    }
  }
}
</script>

<style></style>
