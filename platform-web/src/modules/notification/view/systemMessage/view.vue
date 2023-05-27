<template>
  <Dialog title="查看" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="标题" prop="title">
        <el-input v-model="entityData.title" />
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input v-model="entityData.content" type="textarea" rows="4" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { viewMixin } from '@/mixin/viewMixin.js'
import { useNotificationStore } from '@/store/modules/notification'
const notificationStore = useNotificationStore()
const MODULE_CODE = 'notification'
const ENTITY_TYPE = 'systemMessage'
export default {
  name: ENTITY_TYPE + '-view',
  components: {},
  mixins: [viewMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      // 更新过已读状态
      changeSatusFlag: false
    }
  },
  methods: {
    afterInit() {
      // 默认设置为false，否则多次打开的情况下，会保留上次状态
      // 而上次状态可能为true，导致查看已读的消息，关闭窗口仍然会刷新父页面
      this.changeSatusFlag = false
      // 查看时如状态为未读，设置已读
      if (this.entityData.readFlag === 'NO') {
        this.api.read(this.entityData.id).then((res) => {
          this.changeSatusFlag = true
          // 未读消息减1
          notificationStore.decreaseUnreadMessageCount()
        })
      }
    },
    // 关闭
    close() {
      if (this.changeSatusFlag) {
        this.$emit('refresh')
      }
      this.visible = false
    }
  }
}
</script>
<style></style>
