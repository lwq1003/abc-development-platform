<template>
  <Dialog title="请选择目标文件夹" v-model="visible" width="500px">
    <el-input v-model="searchValue" placeholder="请输入关键词" style="margin-bottom: 10px" />
    <el-tag>当前选中：{{ currentName }}</el-tag>
    <el-tree
      ref="tree"
      :filter-node-method="filterNode"
      :default-expanded-keys="cacheTreeExpandedKeys"
      node-key="id"
      :data="treeData"
      highlight-current
      :expand-on-click-node="false"
      @current-change="treeNodeChange"
    >
      <template #default="{ node, data }">
        <span class="el-tree-node__label">
          <el-icon v-if="node.expanded"><FolderOpened /></el-icon>
          <el-icon v-else><Folder /></el-icon>
          {{ data.label }}
        </span>
      </template>
    </el-tree>
    保留权限：
    <dictionary-radio-group v-model="retainPermission" code="YesOrNo" />
    <template #footer>
      <el-button type="primary" @click="confirm">确定</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { Dialog } from '@/components/abc/Dialog'
import DictionaryRadioGroup from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'
export default {
  components: { Dialog, DictionaryRadioGroup },

  data() {
    return {
      visible: false,
      treeData: [],
      currentId: '',
      currentName: '',
      cacheTreeExpandedKeys: [],
      // 搜索值
      searchValue: '',
      //是否保留权限
      retainPermission: 'YES'
    }
  },
  watch: {
    searchValue(value) {
      this.$refs.tree.filter(value)
    }
  },
  methods: {
    show(id) {
      this.$api.edoc.folder.tree().then((res) => {
        this.treeData = res.data
        this.$api.edoc.folder.get(id).then((res) => {
          this.cacheTreeExpandedKeys.push(res.data.parentId)
          this.currentId = res.data.id
          this.currentName = res.data.name
          this.visible = true
        })
      })
    },
    treeNodeChange(data) {
      this.currentId = data.id
      this.currentName = data.label
    },
    close() {
      // 清空数据
      this.searchValue = ''
      this.currentId = ''
      this.currentName = ''
      this.visible = false
    },
    // 根据名称查询树节点
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    confirm() {
      if (!this.currentId || this.currentId == '') {
        this.$message.warning('请选择目标文件夹')
        return
      }
      // 更新父组件绑定值
      this.$emit('confirm', this.currentId, this.retainPermission)
      this.close()
    }
  }
}
</script>

<style lang="scss"></style>
