/**
 * 树多选页面混入
 * 表单上某个属性触发和回写，值为字符串（元素以逗号间隔）
 */
import { Dialog } from '@/components/abc/Dialog'

export const treeMultipleSelectMixin = {
  emits: ['confirm'],
  components: {
    Dialog
  },
  props: {
    modelValue: {
      type: String,
      default: '',
      required: false
    }
  },
  data() {
    return {
      treeData: [],
      cacheTreeExpandedKeys: [],
      // 显示名称
      displayName: '',
      // 搜索值
      searchValue: '',
      // 可见性
      visible: false,
      checkedNodesId: [],
      selectedValue: []
    }
  },
  watch: {
    modelValue: {
      immediate: true,
      handler: 'getSelectedName'
    },
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
      if (this.modelValue) {
        this.selectedValue = this.modelValue.split(',')
      }

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
    // 清空选择
    clear() {
      this.displayName = ''
      this.$emit('update:modelValue', '')
    },
    confirm() {
      // 获取半选节点ID
      const halfCheckedKeys = this.$refs.tree.getHalfCheckedKeys()
      // 拼接全选节点ID
      const idList = halfCheckedKeys.concat(this.$refs.tree.getCheckedKeys())
      this.$emit('update:modelValue', idList.join(','))
      this.visible = false
    },
    getLeafNodeChecked(node) {
      if (this.selectedValue) {
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
    },
    // 获取选中的名称
    getSelectedName() {
      let length = 0
      if (this.modelValue) {
        length = this.modelValue.split(',').length
      }
      this.displayName = '已选择[ ' + length + ' ]条'
    }
  }
}
