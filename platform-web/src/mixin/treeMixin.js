/**
 * 树页面混入
 */
export const treeMixin = {
  components: {},
  data() {
    return {
      treeData: [],
      cacheTreeExpandedKeys: [],
      currentId: '',
      currentName: '',
      // 数据更新是否需要刷新树
      isRefreshTree: false
    }
  },
  mounted() {
    this.initTree()
  },
  methods: {
    // 初始化树
    initTree() {
      this.load()
    },
    load() {
      return new Promise((resolve) => {
        this.api.tree().then((res) => {
          this.treeData = res.data
          // 如没有默认选中节点
          if (!this.currentId || this.currentId === '') {
            // 默认设置根节点
            this.currentId = this.treeData[0].id
            this.currentName = this.treeData[0].label

            // 设置根节点默认展开
            this.cacheTreeExpandedKeys.push(this.treeData[0].id)
            // 手工触发选择节点改变
            this.$emit('change-selected', this.treeData[0].id, this.treeData[0].label)
          }
        })
      })
    },
    // 树表相关操作
    handleTreeSelectChange(data) {
      // 保存标识及名称用于新增操作
      this.currentId = data.id
      this.currentName = data.label
      this.$emit('change-selected', this.currentId, this.currentName)
    },

    // 展开树节点
    handleNodeExpand(data) {
      this.cacheTreeExpandedKeys.push(data.id)
    },
    // 折叠树节点
    handleNodeCollapse(data) {
      const index = this.cacheTreeExpandedKeys.findIndex((item) => item === data.id)
      this.cacheTreeExpandedKeys.splice(index, 1)
    }
  },
  provide() {
    return {}
  }
}
