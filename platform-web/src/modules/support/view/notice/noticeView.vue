<template>
  <div
    v-loading="loading"
    style="
      border: 1px solid #ebeef5;
      padding-left: 5px;
      width: 100%;
      display: flex;
      justify-content: center;
    "
  >
    <el-scrollbar style="width: 60%">
      <el-row style="width: 99%">
        <el-col :span="24">
          <div
            style="
              width: 99%;
              line-height: 28px;
              padding: 15px;
              text-align: center;
              font-size: 24px;
            "
            >{{ entityData.title }}</div
          >
          <div style="width: 99%; text-align: center; font-size: 14px; line-height: 28px"
            >发布时间： {{ $dateFormatter.formatUTCDate(entityData.publishTime) }} &nbsp;&nbsp;
            发布人： {{ entityData.publisher }} &nbsp;&nbsp; 访问量：
            {{ entityData.readCount }}</div
          >
          <div style="width: 99%; border: 1px solid #ebeef5; margin: 0px 0px 20px 0px">
            <div style="padding: 10px 10px 10px 10px" v-html="entityData.content"></div>
          </div>
          <div>
            <AttachmentViewer
              ref="attachmentViewer"
              :entity-id="entityData.id"
              :show-delete="false"
            />
          </div>
        </el-col>
      </el-row>
    </el-scrollbar>
  </div>
</template>

<script>
import AttachmentViewer from '@/modules/support/view/attachment/attachmentViewer.vue'
export default {
  name: 'NoticeView',
  components: { AttachmentViewer },
  data() {
    return {
      entityData: {},
      loading: false,
      visible: false
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      const id = this.$route.query.id
      this.$api.support.notice.view(id).then((res) => {
        this.entityData = res.data
        this.visible = true
        // 更新阅读次数
        this.$api.support.notice.updateReadCount(id)
      })
    }
  }
}
</script>

<style scoped></style>
