<template>
  <CollapseTab>
    <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
      <!--查询条件区 -->

      <el-form-item label="是否订阅">
        <dictionary-select v-model="queryCondition.hasSubscription" code="YesOrNo" />
      </el-form-item>
      <el-form-item label="分类">
        <dictionary-select
          v-model="queryCondition.category"
          code="ApiMessageTopicCategory"
          multiple
        />
      </el-form-item>
      <el-form-item label="名称">
        <QueryText v-model="queryCondition.name" type="LK" />
      </el-form-item>
      <el-form-item label="编码">
        <QueryText v-model="queryCondition.code" type="LK" />
      </el-form-item>
      <el-form-item label="状态" v-show="false">
        <dictionary-select v-model="queryCondition.status" code="Status" />
      </el-form-item>

      <el-form-item style="float: right">
        <el-button type="primary" @click="loadData" icon="Search">查询</el-button>
      </el-form-item>
      <div class="clearfix"></div>
    </el-form>
  </CollapseTab>
  <div class="mb-10px mt-10px">
    <el-button
      v-permission="'cip:messageSubscription:subscribeMessage'"
      type="primary"
      @click="subscribeMessage"
      >订阅</el-button
    >
    <el-button
      v-permission="'cip:messageSubscription:unsubscribeMessage'"
      type="primary"
      @click="unsubscribeMessage"
      >取消订阅</el-button
    >
  </div>

  <el-card>
    <el-table
      v-loading="loading"
      :data="tableData"
      highlight-current-row
      border
      @sort-change="sortChange"
      @selection-change="selectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column
        v-for="(item, index) in columnList"
        :key="index"
        :label="item.label"
        :prop="item.prop"
        :show-overflow-tooltip="item.showOverflowTooltip"
        :width="item.width"
        :formatter="item.formatFunc"
        :sortable="item.sortable"
      />
    </el-table>
  </el-card>
</template>

<script lang="ts">
import CollapseTab from '@/components/abc/CollapseTab/index.vue'
import QueryText from '@/components/abc/QueryText/index.vue'

import DictionarySelect from '@/components/abc/DictionarySelect/DictionarySelect.vue'
const ENTITY_TYPE = 'messageSubscription'
export default {
  name: ENTITY_TYPE,
  components: {
    CollapseTab,
    QueryText,
    DictionarySelect
  },

  data() {
    return {
      // 加载中
      loading: false,
      // 表格数据
      tableData: [],
      // 当前行
      currentId: this.$constant.NO_ITEM_SELECTED,

      // 排序信息
      sortInfo: {
        sort_field: 'orderNo',
        sort_sortType: 'ascending'
      },
      // 复选框选中项
      multipleSelection: [],
      pageCode: 'cip:messageTopic',
      columnList: [
        {
          prop: 'name',
          label: '名称',
          show: true,
          showOverflowTooltip: true,

          sortable: true
        },
        {
          prop: 'code',
          label: '编码',
          show: true,
          showOverflowTooltip: true,

          sortable: true
        },
        {
          prop: 'categoryName',
          label: '分类',
          show: true,
          showOverflowTooltip: true
        },
        { prop: 'hasSubscriptionName', label: '是否订阅', show: true }
      ],
      queryCondition: {
        //默认值处理
        status: 'NORMAL'
      },

      visible: false
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      this.visible = true
      this.loadData()
    },
    // 加载数据
    loadData() {
      return new Promise((resolve) => {
        this.loading = true
        const params = Object.assign(this.queryCondition, this.sortInfo)
        this.$api.cip.messageTopic
          .queryMessageSubscription(params)
          .then((res) => {
            this.tableData = res.data
            resolve()
          })
          .finally(() => {
            this.loading = false
            this.currentId = this.$constant.NO_ITEM_SELECTED
          })
      })
    },

    // 订阅
    subscribeMessage() {
      if (!this.checkSelected()) {
        return
      }
      this.$confirm('是否订阅?', '确认', {
        type: 'warning'
      })
        .then(() => {
          const ids = this.getCheckedIdList()
          this.$api.cip.app.subscribeMessage(ids).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 取消订阅
    unsubscribeMessage() {
      if (!this.checkSelected()) {
        return
      }
      this.$confirm('是否取消订阅?', '确认', {
        type: 'warning'
      })
        .then(() => {
          const ids = this.getCheckedIdList()
          this.$api.cip.app.unsubscribeMessage(ids).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    close() {
      this.visible = false
    },
    // 处理查询
    query() {
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
    // 复选多行
    selectionChange(val) {
      this.multipleSelection = val
    },
    // 验证是否有选中行
    checkSelected() {
      if (this.multipleSelection.length === 0) {
        this.$message.info('请至少选中一行')
        return false
      }
      return true
    },
    // 获取表格勾选行id 数组
    getCheckedIdList() {
      return this.multipleSelection.map((item) => item.id)
    }
  }
}
</script>

<style></style>
