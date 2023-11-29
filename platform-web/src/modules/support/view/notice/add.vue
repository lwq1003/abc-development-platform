<template>
  <Dialog title="新增" v-model="visible" fullscreen>
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="标题" prop="title">
        <el-input v-model="entityData.title" style="width: 85%" />
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <Editor v-model="entityData.content" />
      </el-form-item>
      <el-form-item label="发布范围" prop="publishScope">
        <OrganizationMultipleSelect v-model="entityData.publishScope" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <dictionary-radio-group v-model="entityData.status" code="Status" />
      </el-form-item>
      <el-form-item label="是否重要" prop="importantFlag">
        <dictionary-radio-group v-model="entityData.importantFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="是否置顶" prop="topFlag">
        <dictionary-radio-group v-model="entityData.topFlag" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="允许评论" prop="commentFlag">
        <dictionary-radio-group v-model="entityData.commentFlag" code="YesOrNo" />
      </el-form-item>
    </el-form>
    <el-form-item label="附件列表">
      <AttachmentManager ref="attachmentManager" :entity-id="entityData.id" />
    </el-form-item>
    <el-form-item label="附件上传">
      <AttachmentUploader
        entity-type="Notice"
        :entity-id="entityData.id"
        module-code="support"
        :show-success-files="false"
        @file-complete="fileComplete"
      />
    </el-form-item>
    <template #footer>
      <el-button type="primary" @click="save" v-permission="pageCode + 'add'">保存</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { addMixin } from '@/mixin/addMixin.js'
import AttachmentUploader from '@/modules/support/view/attachment/attachmentUploader.vue'
import AttachmentManager from '@/modules/support/view/attachment/attachmentManager.vue'
const MODULE_CODE = 'support'
const ENTITY_TYPE = 'notice'
export default {
  name: ENTITY_TYPE + '-add',
  components: { AttachmentUploader, AttachmentManager },
  mixins: [addMixin],
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
        title: [{ required: true, message: '【标题】不能为空', trigger: 'blur' }],
        content: [{ required: true, message: '【内容】不能为空', trigger: 'blur' }],
        status: [{ required: true, message: '【状态】不能为空', trigger: 'blur' }],
        importantFlag: [{ required: true, message: '【是否重要】不能为空', trigger: 'blur' }],
        topFlag: [{ required: true, message: '【是否置顶】不能为空', trigger: 'blur' }],
        commentFlag: [{ required: true, message: '【允许评论】不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    // 附件上传完成，刷新管理组件
    fileComplete() {
      this.$refs.attachmentManager.list()
    }
  }
}
</script>

<style></style>
