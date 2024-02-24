<template>
  <el-dialog
    title="按组织机构授权"
    v-model="visible"
    width="800px"
    append-to-body
    :close-on-click-modal="false"
    @close="close"
  >
    <el-row :gutter="20">
      <el-col :span="8">
        <el-input
          v-model="searchValue"
          placeholder="请输入关键字过滤"
          style="margin-bottom: 10px"
        />
        <el-tag>已选择：{{ selectedName }}</el-tag>
        <el-tree
          ref="tree"
          :data="treeData"
          node-key="id"
          :default-expanded-keys="defaultExpandedKeys"
          :expand-on-click-node="false"
          :filter-node-method="filterNode"
          @current-change="treeSelectChange"
        />
      </el-col>
      <el-col :span="16">
        文档权限
        <DictionaryCheckboxGroup
          v-model="entityData.permissionCodeList"
          multiple
          direction="vertical"
          :code="$constant.DOCUMENT_PERMISSION_ITEM"
        />
      </el-col>
    </el-row>
    <template #footer>
      <el-button type="primary" @click="save">保存</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script>
import DictionaryCheckboxGroup from '@/components/abc/DictionarySelect/DictionaryCheckboxGroup.vue'
export default {
  components: {
    DictionaryCheckboxGroup
  },
  data() {
    return {
      visible: false,
      loading: false,
      treeData: [],
      searchValue: '',
      defaultExpandedKeys: [],
      selectedValue: [],
      checkedNodesId: [],
      entityData: {
        objectType: this.$constant.FOLDER_GRANT_PERMISSION_MODE_ORGANIZATION,
        objectId: '',
        folderId: '',
        permissionCodeList: []
      },
      readonly: false,
      selectedName: ''
    }
  },
  watch: {
    searchValue(value) {
      this.$refs.tree.filter(value)
    }
  },

  methods: {
    show(id) {
      // 传递参数处理
      this.entityData.folderId = id

      // 初始化组织机构树
      this.searchValue = ''
      this.defaultExpandedKeys = []
      this.loadTree()

      this.visible = true
    },

    loadTree() {
      this.$api.system.organization.tree().then((res) => {
        this.treeData = res.data
        // 默认展开根节点
        this.defaultExpandedKeys.push(this.treeData[0].id)
      })
    },
    // 树节点更改选中
    treeSelectChange(data) {
      if (!data) {
        return
      }
      this.loading = true

      this.entityData.objectId = data.id
      this.selectedName = data.label
      // 计算权限
      this.$api.edoc.folderPermission
        .getPermission(
          this.entityData.folderId,
          this.entityData.objectId,
          this.entityData.objectType
        )
        .then((res) => {
          this.entityData.permissionCodeList = res.data.permissionCodeList
        })
        .finally(() => {
          this.loading = false
        })
    },
    close() {
      // 清空数据
      this.entityData.permissionCodeList = []
      this.entityData.folderId = ''
      this.objectId = ''
      this.$refs.tree.setCurrentKey(null)
      this.visible = false
    },

    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    save() {
      if (!this.$refs.tree.getCurrentNode()) {
        this.$message.warning('请选择组织机构')
        return
      }
      this.loading = true
      this.$api.edoc.folderPermission.grantPermission(this.entityData).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>

<style scoped></style>
