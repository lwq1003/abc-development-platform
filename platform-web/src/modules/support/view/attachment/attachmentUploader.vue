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
    @file-error="fileError"
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
import shortid from 'shortid'
export default {
  name: 'AttachmentUploader',
  label: '附件上传',
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
      default: '/support/attachment/uploadChunk',
      required: false
    },
    mergeChunkApi: {
      type: String,
      default: 'this.$api.support.attachment',
      required: false
    },
    appendData: {
      type: Object,
      required: false,
      default() {
        return {}
      }
    }
  },
  data() {
    const token = getToken()
    return {
      defaultOptions: {
        target: import.meta.env.VITE_BASE_URL + this.serverUrl,
        testChunks: false,
        maxChunkRetries: 3,
        simultaneousUploads: 3,
        chunkSize: 10240000,
        query: {
          entityType: this.entityType,
          entityId: this.entityId,
          moduleCode: this.moduleCode
        },
        headers: { 'X-Token': token },
        generateUniqueIdentifier: () => {
          // 获取唯一性标识
          const uniqueId = shortid.generate()
          return uniqueId + '-'
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
      if (file.chunks.length > 1) {
        //构造文件信息
        const param = {
          identifier: file.uniqueIdentifier,
          filename: file.name,
          moduleCode: this.moduleCode,
          entityType: this.entityType,
          entityId: this.entityId,
          type: file.fileType,
          totalSize: file.size,
          ...this.appendData
        }

        // 合并文件块
        // eslint-disable-next-line no-eval
        eval(this.mergeChunkApi)
          .mergeChunks(param)
          .then(() => {
            if (!this.showSuccessFiles) {
              // 移除已上传成功的文件
              this.$refs.uploader.uploader.removeFile(file)
            }
          })
      } else {
        if (!this.showSuccessFiles) {
          // 不分块，移除已上传成功的文件
          this.$refs.uploader.uploader.removeFile(file)
        }
      }
    },
    fileError(rootFile, file, responseData, chunk) {
      const res = JSON.parse(responseData)
      if (res && res.message) {
        this.$message.error(res.message)
      }
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
