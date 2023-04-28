<template>
  <Dialog title="选择数据字典" v-model="visible" width="400px">
    <el-input
      v-model="searchValue"
      placeholder="请输入字典名称按回车进行搜索"
      @keyup.enter="query"
    />

    <el-tree
      ref="tree"
      :data="treeData"
      node-key="code"
      :default-expanded-keys="defaultExpandedKeys"
      :filter-node-method="filterNode"
      @current-change="handleTreeSelectChange"
    />

    <span>
      已选中：
      <el-input v-model="selectedName" readonly style="color: mediumblue" />
    </span>
    <template #footer>
      <el-button type="primary" @click="confirm">确定</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { Dialog } from '@/components/abc/Dialog'

export default {
  components: {
    Dialog
  },
  data() {
    return {
      visible: false,
      treeData: [],
      searchValue: '',
      defaultExpandedKeys: [],
      selectedValue: '',
      selectedName: ''
    }
  },
  created() {
    this.loadTree()
  },
  methods: {
    init() {
      // 此处强制调用重新加载数据，否则新添加或修改的数据不会生效
      this.loadTree()
      this.searchValue = ''
      this.visible = true
    },
    loadTree() {
      this.$api.system.dictionaryType.tree().then((res) => {
        this.treeData = res.data
        // 默认展开根节点
        this.defaultExpandedKeys.push(this.treeData[0].code)
      })
    },
    close() {
      this.visible = false
    },
    confirm() {
      console.log(this.selectedValue)
      this.$emit('ok', this.selectedValue)
      this.visible = false
    },
    // 树节点选中改变
    handleTreeSelectChange(data) {
      this.selectedValue = data.code
      this.selectedName = data.label
    },
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    // 树节点过滤
    query() {
      this.$refs.tree.filter(this.searchValue)
    }
  }
}
</script>

<style></style>
