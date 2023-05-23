<template>
  <uploader
    ref="uploader"
    :options="finalOptions"
    :file-status-text="statusText"
    :show-success-files="showSuccessFiles"
    :single-flag="singleFlag"
    :auto-start="autoStart"
    :show-list-flag="showListFlag"
    @file-complete="fileComplete"
    @file-success="fileSuccess"
    @file-added="fileAdded"
    class="w-full"
  >
    <uploader-drop class="uploader-dragover">
      <div ref="drop" class="uploader-drop">
        <el-icon size="30">
          <UploadFilled />
        </el-icon>
        <div class="uploader-drop_text">
          将文件拖拽至此处，或
          <em>
            <uploader-btn :single="singleFlag">点击上传</uploader-btn>
          </em>
        </div>
      </div>
    </uploader-drop>
    <uploader-list v-show="showListFlag" />
  </uploader>
</template>

<script>
import { getToken } from '@/utils/auth'

export default {
  components: {},
  props: {
    options: {
      type: Object,
      required: false,
      default() {
        return {}
      }
    },
    singleFlag: {
      type: Boolean,
      default: false
    },
    autoStart: {
      type: Boolean,
      default: true
    },
    // 是否显示文件列表
    showListFlag: {
      type: Boolean,
      default: true
    },
    entityType: {
      type: String,
      required: true
    },
    entityId: {
      type: String,
      default: '',
      required: true
    },
    moduleCode: {
      type: String,
      required: true
    },
    showSuccessFiles: {
      type: Boolean,
      default: false,
      required: false
    },
    serverUrl: {
      type: String,
      default: '',
      required: true
    }
  },
  data() {
    const token = getToken()
    return {
      defaultOptions: {
        target: import.meta.env.VITE_BASE_URL + this.serverUrl,
        testChunks: false,
        maxChunkRetries: 3,
        chunkSize: 10485760,
        query: {
          entityType: this.entityType,
          entityId: this.entityId,
          moduleCode: this.moduleCode
        },
        headers: { 'X-Token': token },
        generateUniqueIdentifier: () => {
          // 业务主键+时间戳最大限度降低并发冲突发生的概率
          return this.entityId + new Date().getTime()
        },
        parseTimeRemaining(timeRemaining, parsedTimeRemaining) {
          return parsedTimeRemaining
            .replace(/\syears?/, '年')
            .replace(/\days?/, '天')
            .replace(/\shours?/, '时')
            .replace(/\sminutes?/, '分')
            .replace(/\sseconds?/, '秒')
        }
      },
      statusText: {
        success: '100%',
        error: '失败',
        uploading: '上传中',
        paused: '暂停中',
        waiting: '等待中'
      }
    }
  },
  computed: {
    finalOptions: function () {
      const opts = Object.assign(this.defaultOptions, this.options)
      return opts
    }
  },
  watch: {
    entityId: function () {
      this.$refs.uploader.options.query.entityId = this.entityId
    }
  },
  methods: {
    fileComplete(file) {
      this.$emit('file-complete', file)
    },
    fileAdded(file) {
      this.$emit('file-added', file)
    },
    fileSuccess(rootFile, file) {
      //  文件上传成功后从列表中移除
      // TODO:以下代码未调试成功
      // let index = this.$refs.uploader.fileList.findIndex(
      //   (successFile) => successFile.id === file.id
      // )
      // if (index !== -1) {
      //   console.log(this.$refs.uploader.fileList)
      //   this.$refs.uploader.fileList.splice(index, 1)
      //   console.log(this.$refs.uploader.fileList)
      // }
    }
  }
}
</script>

<style scoped>
.uploader-drop {
  background-color: #f7f7f7;
  box-sizing: border-box;
  height: 120px;
  text-align: center;
  position: relative;
  overflow: hidden;
}
.uploader-drop_text {
  color: #606266;
  font-size: 14px;
  text-align: center;
  line-height: 50px;
}
em label {
  color: #1890ff;
  font-style: normal;
  cursor: pointer;
}

.uploader-dragover {
  border-color: #999;
  background-color: #f7f7f7;
}
</style>
