<template>
  <el-drawer
    :append-to-body="true"
    title="环节设置"
    v-model="visible"
    :show-close="false"
    :size="550"
    :before-close="close"
    destroy-on-close
  >
    <el-collapse v-model="activeName">
      <el-collapse-item title="权限设置" name="permissionConfig">
        <el-table :data="permissionData" style="width: 100%" highlight-current-row border>
          <el-table-column label="区域" width="120">
            <template #default="scope">{{ scope.row.areaName }}</template>
          </el-table-column>
          <el-table-column label="权限">
            <template #default="scope">
              <dictionary-radio-group
                v-model="scope.row.permission"
                code="NodePermissionCode"
                class="form-item"
              />
            </template>
          </el-table-column>
        </el-table>
      </el-collapse-item>
    </el-collapse>
    <template #footer>
      <el-button type="primary" @click="save">确 定</el-button>
      <el-button @click="close">取 消</el-button>
    </template>
  </el-drawer>
</template>
<script>
import DictionaryRadioGroup from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'

import { useStore } from '../../stores/index'
let store = useStore()
export default {
  components: { DictionaryRadioGroup },
  data() {
    return {
      activeName: ['permissionConfig'],
      // 权限数据
      permissionData: []
    }
  },
  computed: {
    visible() {
      return store.rootNodeConfigVisible
    },
    rootNodeConfig() {
      return store.rootNodeConfig
    },
    processDefinitionId() {
      return store.processDefinitionId
    }
  },
  watch: {
    rootNodeConfig(value) {
      // 加载权限设置
      this.$api.workflow.workflowNodePermissionConfig
        .getNodePermissionConfig(this.processDefinitionId, value.id)
        .then((res) => {
          if (res.data) {
            this.permissionData = res.data
            // 根据配置更新
            const permissionConfig = value.config.permissionConfig
            if (permissionConfig && permissionConfig.length > 0) {
              this.permissionData.forEach((item) => {
                permissionConfig.forEach((config) => {
                  if (config.areaCode == item.areaCode) {
                    item.permission = config.permission
                    return
                  }
                })
              })
            }
          }
        })
    }
  },
  methods: {
    close() {
      store.setRootNodeConfigVisible(false)
    },
    save() {
      const permissionConfig = this.permissionData.map((item) => {
        return {
          areaCode: item.areaCode,
          permission: item.permission
        }
      })

      const nodeConfig = Object.assign(
        store.rootNodeConfig,
        {
          config: { permissionConfig: permissionConfig }
        },
        { flag: true }
      )

      store.setRootNodeConfig(nodeConfig)
      this.close()
    }
  }
}
</script>
<style scoped></style>
