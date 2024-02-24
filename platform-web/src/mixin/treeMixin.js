/**
 * 树页面混入
 */
import { ContentWrap } from '@/components/abc/ContentWrap'
export const treeMixin = {
  components: { ContentWrap },
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
    this.init()
  },
  methods: {
    // 初始化树
    init(id) {
      this.load(id)
    },
    load(id) {
      return new Promise((resolve) => {
        this.api.tree().then((res) => {
          this.treeData = res.data
          if (id) {
            this.$refs.tree.setCurrentKey(id)
            this.parentId = id
            this.cacheTreeExpandedKeys.push(id)
            const node = this.$refs.tree.getCurrentNode
            // 手工触发选择节点改变
            this.$emit('change-selected', id, node.label)
          } else {
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
