/**
 * 树单选页面混入
 * 通过按钮触发对话框，确认后触发事件返回数据，而不是表单上某个属性触发和回写
 */
import { Dialog } from '@/components/abc/Dialog'

export const treeSingleSelectMixin = {
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
      this.$refs.tree.setCurrentKey(param.data)
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
      // 获取选中值
      const id = this.$refs.tree.getCurrentKey()
      if (id) {
        this.$emit('confirm', id)
        this.visible = false
      } else {
        this.$message.warning('请选择树节点')
        return
      }
    }
  }
}
