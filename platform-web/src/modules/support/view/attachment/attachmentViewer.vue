<template>
  <el-row>
    <ul style="margin: 0px; padding: 0px">
      <li
        v-for="item in entityData"
        :key="item.id"
        style="
          line-height: 30px;
          width: 100%;
          overflow: hidden;
          word-break: keep-all;
          white-space: nowrap;
          text-overflow: ellipsis;
        "
      >
        <a :title="item.name" @click="download(item)" class="cursor-pointer">
          <span>&nbsp;&nbsp;{{ item.name }}</span>
          <span>({{ item.size }} )</span>
        </a>
      </li>
    </ul>
  </el-row>
</template>
<script>
export default {
  name: 'AttachmentViewer',
  label: '附件查看',
  props: {
    entityId: {
      type: String,
      default: '',
      required: true
    }
  },
  data() {
    return {
      entityData: []
    }
  },
  watch: {
    entityId: function () {
      this.list()
    }
  },
  mounted: function () {
    this.list()
  },
  methods: {
    list() {
      if (this.entityId) {
        // 只有当entityId不为空时才发起查询
        this.$api.support.attachment.list({ entityId: this.entityId }).then((res) => {
          this.entityData = res.data
        })
      }
    },
    download(item) {
      this.$api.support.attachment.download(item.id)
    }
  }
}
</script>
