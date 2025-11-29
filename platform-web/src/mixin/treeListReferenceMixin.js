/**
 * 树表参照页面混入
 */
import { Dialog } from '@/components/abc/Dialog'
import CollapseTab from '@/components/abc/CollapseTab/index.vue'
import QueryText from '@/components/abc/QueryText/index.vue'
import QueryButton from '@/components/abc/QueryButton/index.vue'
import { ContentWrap } from '@/components/abc/ContentWrap'
import ListPager from '@/components/abc/ListPager/index.vue'
import ColumnsController from '@/components/abc/ColumnsController/index.vue'
import DictionarySelect from '@/components/abc/DictionarySelect/DictionarySelect.vue'
import DictionaryRadioGroup from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'
import OrganizationSingleSelect from '@/modules/system/view/organization/treeReference.vue'
import OrganizationMultipleSelect from '@/modules/system/view/organization/treeMultipleSelect.vue'
import IconPicker from '@/components/abc/IconPicker/index.vue'

export const treeListReferenceMixin = {
  emits: ['confirm'],
  components: {
    ContentWrap,
    CollapseTab,
    QueryText,
    QueryButton,
    ListPager,
    ColumnsController,
    DictionarySelect,
    DictionaryRadioGroup,
    Dialog,
    OrganizationSingleSelect,
    OrganizationMultipleSelect,
    IconPicker
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
      // 表格数据
      tableData: [],
      // 加载中
      loading: false,
      // 当前行
      currentId: this.$constant.NO_ITEM_SELECTED,
      // 复选框选中项
      multipleSelection: [],
      // 分页信息
      pageInfo: {
        // 页码
        pageNum: this.$constant.DEFAULT_PAGE_NUM,
        // 页码大小
        pageSize: this.$constant.DEFAULT_PAGE_SIZE
      },
      // 排序信息
      sortInfo: {
        sort_field: 'orderNo',
        sort_sortType: 'ascending'
      },
      // 总页数
      pageTotal: 0,
      queryCondition: {
        // 默认值处理
      },
      // 名称键值
      nameKey: 'name',
      // 显示名称
      displayName: '',
      // 可见性
      visible: false,
      commonParam: {}
    }
  },
  computed: {
    showCols() {
      return this.columnList.filter((item) => item.show)
    },
    tableKey() {
      const { path } = this.$route
      return `${path}/table`
    }
  },
  watch: {
    modelValue: {
      immediate: true,
      handler: 'getSelectedName'
    },
    commonParam: {
      immediate: true,
      handler: 'handleParamChange',
      deep: true
    }
  },
  methods: {
    // 输入参数变化
    handleParamChange(value) {
      if (this.commonParamChange) {
        this.commonParamChange(value)
        this.loadData()
      }
    },
    changeNode(value, name) {
      this.treeNodeId = value
      this.treeNodeName = name
      this.commonParam = Object.assign({}, this.commonParam, { id: value })
    },
    // 初始化
    init(param) {
      if (this.beforeInit != null) {
        this.beforeInit(param)
      }
      this.loadData().then((res) => {
        if (this.afterInit) {
          this.afterInit(param)
        }
        this.visible = true
      })
    },
    // 加载数据
    loadData() {
      return new Promise((resolve) => {
        this.loading = true
        const params = Object.assign(this.queryCondition, this.pageInfo, this.sortInfo)
        this.api
          .page(params)
          .then((res) => {
            this.tableData = res.data.records
            this.pageTotal = res.data.total
            resolve()
          })
          .finally(() => {
            this.loading = false
            this.currentId = this.$constant.NO_ITEM_SELECTED
          })
      })
    },
    close() {
      this.visible = false
    },
    // 清空选择
    clear() {
      this.displayName = ''
      this.$emit('update:modelValue', '')
      this.$emit('my-change', '')
    },
    confirm() {
      if (!this.checkSelectedRowExist()) {
        return
      }
      const selectedRow = this.getRow(this.currentId)

      this.displayName = selectedRow.name
      // 更新父组件绑定值
      this.$emit('update:modelValue', selectedRow.id)
      this.$emit('my-change', selectedRow.id, selectedRow)
      this.visible = false
    },

    // 获取选中的名称
    getSelectedName() {
      if (this.modelValue) {
        this.api.get(this.modelValue).then((res) => {
          this.displayName = res.data[this.nameKey]
        })
      } else {
        this.displayName = ''
      }
    },
    // 处理查询
    query() {
      // 查询之前，将当前页置为1
      this.pageInfo.pageNum = 1
      this.refresh()
    },
    // 处理分页变化
    pageChange(value) {
      this.pageInfo.pageNum = value
      this.refresh()
    },
    // 处理分页大小变化
    pageSizeChange(value) {
      this.pageInfo.pageSize = value
      this.refresh()
    },
    // 刷新
    refresh() {
      this.loadData()
    },
    // 处理排序
    // eslint-disable-next-line no-unused-vars
    sortChange({ column, prop, order }) {
      this.sortInfo.sort_field = prop
      this.sortInfo.sort_sortType = order
      this.refresh()
    },
    // 当前行变化
    rowChange(currentRow) {
      this.currentId = currentRow ? currentRow.id : this.$constant.NO_ITEM_SELECTED
    },
    // 验证是否有选中行
    checkSelectedRowExist() {
      if (this.currentId === this.$constant.NO_ITEM_SELECTED) {
        this.$message.info('当前无选中行')
        return false
      }
      return true
    },
    // 获取行记录
    getRow(id) {
      if (id && id !== this.$constant.NO_ITEM_SELECTED) {
        if (this.tableData && this.tableData.length > 0) {
          for (let i = 0; i < this.tableData.length; i++) {
            if (this.tableData[i].id === id) {
              return this.tableData[i]
            }
          }
        }
      }
      return undefined
    }
  },
  provide() {
    return {
      query: this.query,
      pageChange: this.pageChange,
      pageSizeChange: this.pageSizeChange
    }
  }
}
