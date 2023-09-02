<template>
  <Dialog title="新增监听器" v-model="visible" width="500px">
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->
      <el-form-item label="事件" prop="event">
        <dictionary-select
          v-model="entityData.event"
          code="TaskListenerEventCategory"
          @my-change="eventChange"
        />
      </el-form-item>
      <el-form-item label="监听器" prop="listener">
        <ListenerSelect v-model="entityData.listener" @my-change="updateData" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="confirm">确定</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { addMixin } from '@/mixin/addMixin.js'
import ListenerSelect from '@/modules/workflow/view/workflowListener/reference.vue'
const MODULE_CODE = 'workflow'
const ENTITY_TYPE = 'workflowListenerConfig'
export default {
  name: ENTITY_TYPE + '-add',
  components: { ListenerSelect },
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
        event: [{ required: true, message: '【事件】不能为空', trigger: 'blur' }],
        listener: [{ required: true, message: '【监听器】不能为空', trigger: 'blur' }]
      },
      listenerData: {}
    }
  },
  methods: {
    updateData(id, data) {
      this.listenerData = data
    },
    eventChange(id, name) {
      this.entityData.eventName = name
    },
    confirm() {
      const data = {
        category: this.listenerData.category,
        type: this.listenerData.type,
        name: this.listenerData.name,
        code: this.listenerData.content,
        event: this.entityData.event,
        eventName: this.entityData.eventName
      }

      this.$emit('update', data)
      this.visible = false
    }
  }
}
</script>

<style></style>
