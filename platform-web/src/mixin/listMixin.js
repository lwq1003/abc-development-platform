/**
 * 列表页面混入
 */
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
import UserSingleSelect from '@/modules/system/view/user/treeListReference.vue'
import IconPicker from '@/components/abc/IconPicker/index.vue'
import CustomQuery from '@/components/abc/CustomQuery/index.vue'

export const listMixin = {
  components: {
    ContentWrap,
    CollapseTab,
    QueryText,
    QueryButton,
    ListPager,
    ColumnsController,
    DictionarySelect,
    DictionaryRadioGroup,
    OrganizationSingleSelect,
    OrganizationMultipleSelect,
    UserSingleSelect,
    IconPicker,
    CustomQuery
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
      // 当前焦点所在行标识
      currentHoverRowId: ''
    }
  },
  props: {
    // 通用参数对象
    commonParam: {
      type: Object,
      required: false,
      default: {}
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
    commonParam: {
      immediate: true,
      handler: 'handleParamChange',
      deep: true
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    // 输入参数变化
    handleParamChange(value) {
      if (this.commonParamChange) {
        this.commonParamChange(value)
        this.init()
      }
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
    // 新增
    add() {
      if (this.$refs.detailPage) {
        this.$refs.detailPage.init('add')
      } else {
        this.$refs.addPage.init()
      }
    },
    // 修改
    modify(row) {
      if (this.$refs.detailPage) {
        this.$refs.detailPage.init('modify', row.id)
      } else {
        this.$refs.modifyPage.init(row.id)
      }
    },
    // 查看
    view(id) {
      if (this.$refs.detailPage) {
        this.$refs.detailPage.init('view', id)
      } else if (this.$refs.viewPage) {
        this.$refs.viewPage.init(id)
      }
    },
    // 单条移除
    remove(row) {
      this.$confirm('此操作将删除数据, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.api.remove(row.id).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 批量移除
    batchRemove() {
      if (!this.checkSelected()) {
        return
      }
      this.$confirm('此操作将批量删除数据, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          const ids = this.getCheckedId()
          this.api.remove(ids).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 复制新增
    addByCopy() {
      if (!this.checkSelected()) {
        return
      }
      this.$confirm('此操作将批量复制新增数据, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          const ids = this.getCheckedId()
          this.api.addByCopy(ids).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 验证是否有勾选行
    checkSelected() {
      if (this.multipleSelection.length === 0) {
        this.$message.info('请至少选中一行')
        return false
      }
      return true
    },

    // 获取表格勾选行id字符串
    getCheckedId() {
      let ids = ''
      this.multipleSelection.forEach(function (item) {
        ids += item.id + ','
      })
      return ids
    },
    // 获取表格勾选行id 数组
    getCheckedIdList() {
      return this.multipleSelection.map((item) => item.id)
    },
    // 刷新
    refresh() {
      this.$emit('refresh')
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
    },
    // 获取名称
    getName(id) {
      const row = this.getRow(id)
      if (row) {
        return row[this.nameKey]
      }
      return undefined
    },
    // 验证是否有选中行
    checkSelectedRowExist() {
      if (this.currentId === this.$constant.NO_ITEM_SELECTED) {
        this.$message.info('当前无选中行')
        return false
      }
      return true
    },

    // 复选多行
    selectionChange(val) {
      this.multipleSelection = val
    },
    // 双击事件
    rowDoubleClick(row) {
      this.view(row.id)
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
    // 启用
    enable(row) {
      this.$confirm('此操作将启用该数据, 是否继续?', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.api.enable(row.id).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
    },
    // 停用
    disable(row) {
      this.$confirm('此操作将停用该数据, 是否继续?', '确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.api.disable(row.id).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
    },
    // 下载导入模板
    downloadImportTemplate() {
      this.api.downloadImportTemplate()
    },
    // 导入
    importData(file) {
      const formData = new FormData()
      formData.append('file', file.file)
      this.api
        .import(formData)
        .then(() => {
          this.refresh()
        })
        .finally(() => {
          this.clearFile()
        })
    },
    // 导出
    exportData() {
      const params = Object.assign({}, this.queryCondition)
      this.api.export(params)
    },
    // 打开自定义查询对话框
    customQuery() {
      this.$refs.customQuery.init(this.$StringUtil.capitalizeFirstLetter(this.entityType))
    },
    //发起自定义查询
    queryWithCustom(customQueryString) {
      const params = Object.assign(this.pageInfo, this.sortInfo)
      this.api
        .customQuery(customQueryString, params)
        .then((res) => {
          this.tableData = res.data.records
          this.pageTotal = res.data.total
        })
        .finally(() => {
          this.loading = false
          this.currentId = this.$constant.NO_ITEM_SELECTED
        })
    },
    // 鼠标移入行
    hoverRow(row) {
      this.currentHoverRowId = row.id
    },
    // 鼠标移出行
    leaveRow() {
      this.currentHoverRowId = ''
    }
  },
  provide() {
    return {
      query: this.query,
      modify: this.modify,
      view: this.view,
      remove: this.remove,
      pageChange: this.pageChange,
      pageSizeChange: this.pageSizeChange
    }
  }
}
