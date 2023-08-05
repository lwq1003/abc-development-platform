<template>
  <el-drawer
    :append-to-body="true"
    title="条件设置"
    v-model="visible"
    :show-close="false"
    :size="550"
    :before-close="close"
    destroy-on-close
  >
    <el-form
      ref="form"
      :model="entityData"
      :rules="rules"
      label-width="120px"
      label-position="right"
      style="width: 90%; margin: 0px auto"
    >
      <!--表单区域 -->

      <el-form-item label="表达式" prop="expression">
        <el-input v-model="entityData.expression" type="textarea" rows="4" />
      </el-form-item>
      <el-form-item style="float: right; margin-top: 20px">
        <el-button type="primary" @click="save">确 定</el-button>
        <el-button @click="close">取 消</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>
<script>
import { useStore } from '../../stores/index'
let store = useStore()

export default {
  data() {
    return {
      entityData: {},
      rules: {
        //前端验证规则
      }
    }
  },
  computed: {
    visible() {
      return store.conditionNodeConfigVisible
    },
    conditionNodeConfig() {
      return store.conditionNodeConfig
    }
  },
  watch: {
    conditionNodeConfig(value) {
      this.entityData = value
    }
  },
  methods: {
    close() {
      store.setConditionNodeConfigVisible(false)
    },
    save() {
      const nodeConfig = Object.assign(
        store.conditionNodeConfig,
        { ...this.entityData },
        { flag: true }
      )
      store.setConditionNodeConfig(nodeConfig)
      this.close()
    }
  }
}
</script>
<style scoped></style>
