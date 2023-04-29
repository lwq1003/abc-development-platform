/**
 * 树表页面混入
 */
export const treelistMixin = {
  data() {
    return {
      commonParam: {},
      treeNodeId: '',
      treeNodeName: ''
    }
  },

  methods: {
    changeNode(value, name) {
      this.treeNodeId = value
      this.treeNodeName = name
      this.commonParam = Object.assign(this.commonParam, { id: value })
    },
    refresh() {
      this.$refs.treeView.load()
    }
  }
}
