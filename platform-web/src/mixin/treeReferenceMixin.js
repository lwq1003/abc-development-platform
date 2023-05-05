/**
 * 树参照页面混入
 */
import { Dialog } from '@/components/abc/Dialog'

export const treeReferenceMixin = {
  emits: ['update:modelValue', 'my-change', 'change-selected', 'change'],
  components: {
    Dialog
  },

  data() {
    return {
      treeData: [],
      cacheTreeExpandedKeys: [],
      currentId: '',
      currentName: '',
      // 显示名称
      displayName: '',
      // 搜索值
      searchValue: '',
      // 可见性
      visible: false
    }
  },
  props: {
    modelValue: {
      type: String,
      default: '',
      required: false
    },
    disabled: {
      type: Boolean,
      required: false,
      default: false
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
      this.currentId = this.modelValue
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
          // 如没有默认选中节点
          if (!this.currentId || this.currentId === '') {
            // 默认设置根节点
            this.currentId = this.treeData[0].id
            this.currentName = this.treeData[0].label

            // 设置根节点默认展开
            this.cacheTreeExpandedKeys.push(this.treeData[0].id)

            // 手工触发选择节点改变
            this.$emit('change-selected', this.treeData[0].id, this.treeData[0].label)
          } else {
            // 默认展开当前节点
            this.cacheTreeExpandedKeys.push(this.currentId)
            // 手工触发选择节点改变
            this.$emit('change-selected', this.currentId, this.currentName)
          }
          resolve()
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
      // 更新父组件绑定值
      this.$emit('update:modelValue', this.currentId)
      this.$emit('my-change', this.currentId)
      this.visible = false
    },
    // 清空选择
    clear() {
      this.displayName = ''
      this.$emit('update:modelValue', '')
      this.$emit('my-change', '')
    },
    // 获取选中的名称
    getSelectedName() {
      if (this.modelValue) {
        this.api.get(this.modelValue).then((res) => {
          this.displayName = res.data[this.nameKey]
          this.currentName = this.displayName
        })
      }
    }
  },
  provide() {
    return {}
  }
}
