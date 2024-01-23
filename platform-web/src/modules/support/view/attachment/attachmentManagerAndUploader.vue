<template>
  <AttachmentManager ref="attachmentManager" :entity-id="entityId" />
  <AttachmentUploader
    :entity-type="entityType"
    :entity-id="entityId"
    :module-code="moduleCode"
    @file-complete="fileComplete"
  />
</template>

<script>
import AttachmentManager from '@/modules/support/view/attachment/attachmentManager.vue'
import AttachmentUploader from '@/modules/support/view/attachment/attachmentUploader.vue'
export default {
  name: 'AttachmentManagerAndUploader',
  label: '附件管理上传',
  components: { AttachmentManager, AttachmentUploader },
  props: {
    entityId: {
      type: String,
      default: '',
      required: true
    },
    entityType: {
      type: String,
      required: true
    },
    moduleCode: {
      type: String,
      required: true
    }
  },
  data() {
    return {}
  },
  mounted: function () {},
  methods: {
    fileComplete() {
      this.$refs.attachmentManager.list()
    }
  }
}
</script>
