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
    <el-collapse v-model="activeName" style="padding: 0">
      <el-collapse-item title="人员设置" name="personConfig">
        <el-form
          ref="form"
          :model="entityData"
          :rules="rules"
          label-width="120px"
          label-position="right"
          style="width: 90%; margin: 0px auto"
        >
          <!--表单区域 -->
          <el-form-item label="模式" prop="mode">
            <dictionary-radio-group v-model="entityData.mode" code="NodeMode" />
          </el-form-item>
          <el-form-item label="指定处理人" prop="setAssigneeFlag">
            <dictionary-radio-group v-model="entityData.setAssigneeFlag" code="YesOrNo" />
          </el-form-item>
          <el-form-item label="用户组" prop="userGroup">
            <UserGroupReference v-model="entityData.userGroup" @my-change="userGroupchange" />
          </el-form-item>
          <el-form-item label="用户组名称" prop="userGroupName" v-show="false">
            <el-input v-model="entityData.userGroupName" />
          </el-form-item>
        </el-form>
      </el-collapse-item>

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
      <el-collapse-item title="回退环节" name="backNodeListConfig">
        <div class="mb-10px mt-10px">
          <el-button type="primary" icon="plus" @click="addBack">新增</el-button>
        </div>
        <el-table :data="backNodeList" style="width: 100%" highlight-current-row border>
          <el-table-column label="环节名称">
            <template #default="scope">{{ scope.row.name }}</template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" align="center" width="90">
            <template #default="scope">
              <el-button type="primary" text @click="removeBack(scope.row)">移除</el-button>
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
          <el-table-column fixed="right" label="操作" align="center" width="90">
            <template #default="scope">
              <el-button type="primary" text @click="removeJump(scope.row)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-collapse-item>
      <el-collapse-item title="监听器" name="listenerListConfig">
        <div class="mb-10px mt-10px">
          <el-button type="primary" icon="plus" @click="addListener">新增</el-button>
        </div>
        <el-table :data="listenerList" style="width: 100%" highlight-current-row border>
          <el-table-column label="事件">
            <template #default="scope">{{ scope.row.eventName }}</template>
          </el-table-column>
          <el-table-column label="名称">
            <template #default="scope">{{ scope.row.name }}</template>
          </el-table-column>

          <el-table-column fixed="right" label="操作" align="center" width="90">
            <template #default="scope">
              <el-button type="primary" text @click="removeListener(scope.row)">移除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-collapse-item>
    </el-collapse>
    <FlowStepSelect ref="flowStepSelectBack" @update="updateBack" />
    <FlowStepSelect ref="flowStepSelectJump" @update="updateJump" />
    <FlowListenerSelect ref="flowListenerSelect" @update="updateListener" />

    <template #footer>
      <el-button type="primary" @click="save">确 定</el-button>
      <el-button @click="close">取 消</el-button>
    </template>
  </el-drawer>
</template>
<script>
import DictionaryRadioGroup from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'
import UserGroupReference from '@/modules/system/view/userGroup/treeReference.vue'
import FlowStepSelect from '../dialog/FlowStepSelect.vue'
import FlowListenerSelect from '../dialog/FlowListenerSelect.vue'
import { useStore } from '../../stores/index'
let store = useStore()
const MODULE_CODE = 'workflow'
const ENTITY_TYPE = 'workflowNodeConfig'
export default {
  name: ENTITY_TYPE + '-modify',
  components: { DictionaryRadioGroup, UserGroupReference, FlowStepSelect, FlowListenerSelect },
  props: {
    modelData: {
      type: Object
    }
  },
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
        mode: [{ required: true, message: '【模式】不能为空', trigger: 'blur' }],
        setAssigneeFlag: [{ required: true, message: '【指定处理人】不能为空', trigger: 'blur' }],
        userGroup: [{ required: true, message: '【用户组】不能为空', trigger: 'blur' }]
      },
      activeName: [
        'personConfig',
        'permissionConfig',
        'backNodeListConfig',
        'jumpNodeListConfig',
        'listenerListConfig'
      ],
      // 权限数据
      permissionData: [],
      // 回退环节
      backNodeList: [],
      // 跳转环节
      jumpNodeList: [],
      // 监听器
      listenerList: []
    }
  },
  computed: {
    visible() {
      return store.handleNodeConfigVisible
    },
    handleNodeConfig() {
      return store.handleNodeConfig
    }
  },
  watch: {
    handleNodeConfig(value) {
      // 加载人员设置
      if (value.config.personConfig) {
        this.entityData = value.config.personConfig
      }

      // 加载权限设置
      const processDefinitionId = store.processDefinitionId
      this.$api.workflow.workflowNodePermissionConfig
        .getNodePermissionConfig(processDefinitionId, value.id)
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
      // 加载监听器列表
      this.listenerList = value.config.listenerList || []
    }
  },
  methods: {
    close() {
      store.setHandleNodeConfigVisible(false)
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          const permissionConfig = this.permissionData.map((item) => {
            return {
              areaCode: item.areaCode,
              permission: item.permission
            }
          })
          const nodeConfig = Object.assign(
            store.handleNodeConfig,
            {
              config: {
                personConfig: this.entityData,
                permissionConfig: permissionConfig,
                backNodeList: this.backNodeList,
                jumpNodeList: this.jumpNodeList,
                listenerList: this.listenerList
              }
            },
            { flag: true }
          )
          store.setHandleNodeConfig(nodeConfig)
          this.close()
        }
      })
    },
    userGroupchange(id, name) {
      this.entityData.userGroupName = name
    },
    addBack() {
      this.$refs.flowStepSelectBack.init(this.modelData, this.handleNodeConfig.id, true)
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
      this.$refs.flowStepSelectJump.init(this.modelData, this.handleNodeConfig.id, false)
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
    },
    addListener() {
      // 限定只能是任务监听器
      const category = ['TASK']
      this.$refs.flowListenerSelect.init(category)
    },
    updateListener(listener) {
      this.listenerList.push(listener)
    },
    removeListener(row) {
      // 找到要移除的元素的索引
      let index = this.listenerList.findIndex((item) => item.id === row.id)

      // 使用splice方法移除该元素
      if (index !== -1) {
        this.listenerList.splice(index, 1)
      }
    }
  }
}
</script>
<style></style>
