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
      <el-collapse-item title="回退环节" name="backNodeListConfig" v-show="false">
        <div class="mb-10px mt-10px">
          <el-button type="primary" icon="plus" @click="addBack">新增</el-button>
        </div>
        <el-table :data="backNodeList" style="width: 100%" highlight-current-row border>
          <el-table-column label="环节名称">
            <template #default="scope">{{ scope.row.name }}</template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="90">
            <template #default="scope">
              <el-button type="primary" @click="removeBack(scope.row)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-collapse-item>
      <el-collapse-item title="跳转环节" name="jumpNodeListConfig">
        <div class="mb-10px mt-10px">
          <el-button type="primary" icon="plus" @click="addJump">新增</el-button>
        </div>
        <el-table :data="jumpNodeList" style="width: 100%" highlight-current-row border>
          <el-table-column label="环节名称">
            <template #default="scope">{{ scope.row.name }}</template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="90">
            <template #default="scope">
              <el-button type="primary" @click="removeJump(scope.row)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-collapse-item>
    </el-collapse>

    <FlowStepSelect ref="flowStepSelectBack" @update="updateBack" />
    <FlowStepSelect ref="flowStepSelectJump" @update="updateJump" />
    <template #footer>
      <el-button type="primary" @click="save">确 定</el-button>
      <el-button @click="close">取 消</el-button>
    </template>
  </el-drawer>
</template>
<script>
import DictionaryRadioGroup from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'
import FlowStepSelect from '../dialog/FlowStepSelect.vue'
import { useStore } from '../../stores/index'
let store = useStore()
export default {
  components: { DictionaryRadioGroup, FlowStepSelect },
  data() {
    return {
      activeName: ['permissionConfig', 'backNodeListConfig', 'jumpNodeListConfig'],
      // 权限数据
      permissionData: [],
      // 回退环节
      backNodeList: [],
      // 跳转环节
      jumpNodeList: []
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
      // 加载回退环节列表
      this.backNodeList = value.config.backNodeList
      // 加载跳转环节列表
      this.jumpNodeList = value.config.jumpNodeList
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
          config: {
            permissionConfig: permissionConfig,
            backNodeList: this.backNodeList,
            jumpNodeList: this.jumpNodeList
          }
        },
        { flag: true }
      )

      store.setRootNodeConfig(nodeConfig)
      this.close()
    },
    addBack() {
      this.$refs.flowStepSelectBack.init(this.rootNodeConfig.model, this.rootNodeConfig.id, true)
    },
    updateBack(backNodeList) {
      if (this.backNodeList) {
        const idList = this.backNodeList.map((item) => item.id)
        backNodeList.forEach((item) => {
          if (!idList.includes(item.id)) {
            this.backNodeList.push(item)
          }
        })
      } else {
        this.backNodeList = backNodeList
      }
    },
    removeBack(row) {
      // 找到要移除的元素的索引
      let index = this.backNodeList.findIndex((item) => item.id === row.id)

      // 使用splice方法移除该元素
      if (index !== -1) {
        this.backNodeList.splice(index, 1)
      }
    },
    addJump() {
      this.$refs.flowStepSelectJump.init(this.rootNodeConfig.model, this.rootNodeConfig.id, false)
    },
    updateJump(jumpNodeList) {
      if (this.jumpNodeList) {
        const idList = this.jumpNodeList.map((item) => item.id)
        jumpNodeList.forEach((item) => {
          if (!idList.includes(item.id)) {
            this.jumpNodeList.push(item)
          }
        })
      } else {
        this.jumpNodeList = jumpNodeList
      }
    },
    removeJump(row) {
      // 找到要移除的元素的索引
      let index = this.jumpNodeList.findIndex((item) => item.id === row.id)

      // 使用splice方法移除该元素
      if (index !== -1) {
        this.jumpNodeList.splice(index, 1)
      }
    }
  }
}
</script>
<style scoped></style>
