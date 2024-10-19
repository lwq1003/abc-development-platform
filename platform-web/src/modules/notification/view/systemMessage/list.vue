<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="标题">
          <QueryText v-model="queryCondition.title" type="LK" />
        </el-form-item>
        <el-form-item label="是否已读">
          <dictionary-select v-model="queryCondition.readFlag" code="YesOrNo" multiple />
        </el-form-item>
        <el-form-item style="float: right">
          <QueryButton :page-code="pageCode" />
        </el-form-item>
        <div class="clearfix"></div>
      </el-form>
    </CollapseTab>
    <div class="mb-10px mt-10px">
      <el-button type="primary" icon="Refresh" @click="refresh">刷新</el-button>
      <el-button
        v-permission="pageCode + 'remove'"
        type="primary"
        icon="delete"
        @click="batchRemove"
        >删除</el-button
      >
      <el-button
        v-permission="pageCode + 'setAllRead'"
        type="primary"
        icon="Reading"
        @click="setAllRead"
        >设置所有已读</el-button
      >
    </div>

    <el-card style="width: 100%">
      <div style="margin-top: 0; margin-bottom: 10px; float: right">
        <ColumnsController :value="columnList" :tableKey="tableKey" />
      </div>

      <el-table
        v-loading="loading"
        :data="tableData"
        style="width: 100%"
        highlight-current-row
        border
        @sort-change="sortChange"
        @current-change="rowChange"
        @selection-change="selectionChange"
        @row-dblclick="rowDoubleClick"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column
          v-for="(item, index) in showCols"
          :key="index"
          :label="item.label"
          :prop="item.prop"
          :show-overflow-tooltip="item.showOverflowTooltip"
          :width="item.width"
          :formatter="item.formatFunc"
          :sortable="item.sortable"
        />
        <el-table-column fixed="right" label="操作" align="center" width="90">
          <template #default="scope">
            <el-button
              v-permission="pageCode + 'remove'"
              type="primary"
              text
              @click="remove(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <ListPager
        :page-num="pageInfo.pageNum"
        :page-size="pageInfo.pageSize"
        :page-total="pageTotal"
      />
    </el-card>

    <ViewPage ref="viewPage" @refresh="refresh" />
  </ContentWrap>
</template>

<script lang="ts">
import { listMixin } from '@/mixin/listMixin.js'
import { useNotificationStore } from '@/store/modules/notification'
const notificationStore = useNotificationStore()
import ViewPage from './view.vue'
const MODULE_CODE = 'notification'
const ENTITY_TYPE = 'systemMessage'
export default {
  name: ENTITY_TYPE,
  components: {
    ViewPage
  },
  mixins: [listMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 排序信息
      sortInfo: {
        sort_field: 'id',
        sort_sortType: 'descending'
      },
      columnList: [
        {
          prop: 'title',
          label: '标题',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'content',
          label: '内容',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'readFlagName',
          label: '是否已读',
          show: true,
          showOverflowTooltip: true
        }
      ],
      queryCondition: {
        //默认值处理
        readFlag: ''
      }
    }
  },

  methods: {
    setAllRead() {
      this.$confirm('是否将所有消息设置为已读', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.api.setAllRead().then(() => {
            // 未读消息清零
            notificationStore.setUnreadMessageCount(0)
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    }
  }
}
</script>
