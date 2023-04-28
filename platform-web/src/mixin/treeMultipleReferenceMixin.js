/**
 * 树参照页面混入
 */
import { Dialog } from '@/components/abc/Dialog'

export const treeMultipleReferenceMixin = {
  emits: ['confirm'],
  components: {
    Dialog
  },

  data() {
    return {
      treeData: [],
      cacheTreeExpandedKeys: [],
      // 搜索值
      searchValue: '',
      // 可见性
      visible: false,
      checkedNodesId: [],
      selectedValue: []
    }
  },
  watch: {
    searchValue(value) {
      this.$refs.tree.filter(value)
    }
  },
  methods: {
    // 初始化
    init(param) {
      if (this.beforeInit != null) {
        this.beforeInit(param)
      }
      this.searchValue = ''
      this.cacheTreeExpandedKeys = []
      this.selectedValue = param.data
      this.loadData().then((res) => {
        if (this.afterInit) {
          this.afterInit(param)
        }
        this.visible = true
      })
    },
    loadData() {
      return new Promise((resolve) => {
        this.api.tree().then((res) => {
          this.treeData = res.data
          // 默认展开根节点
          this.cacheTreeExpandedKeys.push(this.treeData[0].id)
          this.checkedNodesId = []
          this.getLeafNodeChecked(this.treeData)
          // this.$refs.tree.setCheckedKeys(this.checkedNodesId)
          resolve()
        })
      })
    },

    // 根据名称查询树节点
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    close() {
      this.visible = false
    },
    confirm() {
      this.$emit('confirm', this.$refs.tree.getCheckedKeys())
      this.visible = false
    },
    getLeafNodeChecked(node) {
      // 遍历树节点，设置
      for (const treeNode of node) {
        // 如果节点有子节点，那他的选中状态不被考虑，继续往下找
        if (treeNode.children && treeNode.children.length > 0) {
          this.getLeafNodeChecked(treeNode.children)
        } else {
          // 是叶子节点，如果是check状态就记录
          if (this.selectedValue.includes(treeNode.id)) {
            this.checkedNodesId.push(treeNode.id)
          }
        }
      }
    }
  }
}
