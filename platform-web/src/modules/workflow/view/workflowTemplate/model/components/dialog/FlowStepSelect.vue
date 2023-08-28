<template>
  <el-dialog title="环节选择" v-model="visible" style="width: 400px">
    <el-checkbox-group v-model="checkedFlowStepList" style="margin-left: 20px">
      <el-checkbox v-for="item in flowStepList" :key="item.id" :label="item.id"
        >{{ item.name }}
      </el-checkbox>
    </el-checkbox-group>

    <template #footer>
      <el-button type="primary" @click="save">确认</el-button>
    </template>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      flowStepList: [],
      checkedFlowStepList: []
    }
  },

  methods: {
    init(model, selfId, containRootNodeFlag) {
      let result = []
      if (containRootNodeFlag) {
        result.push({ id: 'root', name: '填报' })
      }
      // 遍历后续环节
      let childNode = model.child
      while (true) {
        if (!childNode || !childNode.name) {
          break
        }
        // 只处理办理环节
        if (childNode.type == 'HANDLE') {
          // 处理模式为普通（即不允许回退或跳转至会签环节）
          if (
            childNode.config &&
            childNode.config.personConfig &&
            childNode.config.personConfig.mode == 'NORMAL'
          ) {
            result.push({ id: childNode.id, name: childNode.name })
          }
        }
        childNode = childNode.child
      }
      // 移除掉自身
      // 找到要移除的元素的索引
      let index = result.findIndex((item) => item.id === selfId)

      // 使用splice方法移除该元素
      if (index !== -1) {
        result.splice(index, 1)
      }

      this.flowStepList = result

      // 重置选中项
      this.checkedFlowStepList = []
      this.visible = true
    },
    save() {
      const data = this.flowStepList
        .filter((x) => this.checkedFlowStepList.includes(x.id))
        .map((x) => {
          return {
            id: x.id,
            name: x.name
          }
        })
      this.$emit('update', data)
      this.visible = false
    }
  }
}
</script>

<style scoped></style>
