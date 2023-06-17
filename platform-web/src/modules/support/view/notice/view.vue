<template>
  <Dialog title="查看" v-model="visible" fullscreen>
    <el-form
      ref="form"
      :model="entityData"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="标题" prop="title">
        <el-input v-model="entityData.title" style="width: 85%" />
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <Editor v-model="entityData.content" :readonly="true" />
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
      <el-form-item label="阅读次数" prop="readCount">
        <el-input v-model="entityData.readCount" />
      </el-form-item>
      <el-form-item label="发布人" prop="publisher">
        <el-input v-model="entityData.publisher" />
      </el-form-item>
      <el-form-item label="发布时间" prop="publishTime">
        <el-date-picker
          v-model="entityData.publishTime"
          :value-format="$dateFormatter.getDatetimeFormat('SECOND')"
          :type="$dateFormatter.getDatetimeType('SECOND')"
          align="right"
          unlink-panels
          class="form-item"
        />
      </el-form-item>
      <el-form-item label="附件列表">
        <attachment-viewer :entity-id="entityData.id" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'
import AttachmentViewer from '@/modules/support/view/attachment/attachmentViewer.vue'

const MODULE_CODE = 'support'
const ENTITY_TYPE = 'notice'
export default {
  name: ENTITY_TYPE + '-view',
  components: { AttachmentViewer },
  mixins: [viewMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {}
    }
  },
  methods: {}
}
</script>

<style></style>
