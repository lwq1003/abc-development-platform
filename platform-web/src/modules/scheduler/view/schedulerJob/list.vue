<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="名称">
          <QueryText v-model="queryCondition.name" type="LK" />
        </el-form-item>
        <el-form-item label="状态">
          <dictionary-select v-model="queryCondition.status" code="JobStatus" multiple />
        </el-form-item>
        <el-form-item style="float: right">
          <QueryButton :page-code="pageCode" />
        </el-form-item>
        <div class="clearfix"></div>
      </el-form>
    </CollapseTab>
    <div class="mb-10px mt-10px">
      <el-button type="primary" icon="refresh" @click="refresh">刷新</el-button>
      <el-button v-permission="pageCode + 'add'" type="primary" icon="plus" @click="add"
        >新增</el-button
      >
      <el-button
        v-permission="pageCode + 'remove'"
        type="primary"
        icon="delete"
        @click="batchRemove"
        >删除</el-button
      >
      <el-button
        v-permission="pageCode + 'addByCopy'"
        type="primary"
        icon="CopyDocument"
        @click="addByCopy"
        >复制新增</el-button
      >
      <el-button
        v-permission="pageCode + 'pauseAll'"
        type="primary"
        icon="VideoPause"
        @click="pauseAll"
        >暂停全部</el-button
      >
      <el-button
        v-permission="pageCode + 'resumeAll'"
        type="primary"
        icon="VideoPlay"
        @click="resumeAll"
        >恢复全部</el-button
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
        <el-table-column fixed="right" label="操作" align="center" width="320">
          <template #default="scope">
            <el-button
              v-permission="pageCode + 'configParam'"
              type="primary"
              text
              @click="configParam(scope.row)"
              >参数</el-button
            >
            <el-button
              v-permission="pageCode + 'modify'"
              type="primary"
              text
              @click="modify(scope.row)"
              >修改</el-button
            >
            <el-dropdown class="ml-10px">
              <el-button type="primary" text>
                更多
                <el-icon class="el-icon--right"><arrow-down /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-button text v-permission="pageCode + 'remove'" @click="remove(scope.row)"
                      >删除</el-button
                    >
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button text v-permission="pageCode + 'pause'" @click="pause(scope.row)"
                      >暂停</el-button
                    >
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button text v-permission="pageCode + 'resume'" @click="resume(scope.row)"
                      >恢复</el-button
                    >
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button text v-permission="pageCode + 'execute'" @click="execute(scope.row)"
                      >执行</el-button
                    >
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <ListPager
        :page-num="pageInfo.pageNum"
        :page-size="pageInfo.pageSize"
        :page-total="pageTotal"
      />
    </el-card>

    <AddPage ref="addPage" @refresh="refresh" />
    <ModifyPage ref="modifyPage" @refresh="refresh" />
    <ViewPage ref="viewPage" />
  </ContentWrap>
</template>

<script lang="ts">
import { listMixin } from '@/mixin/listMixin.js'
import AddPage from './add.vue'
import ModifyPage from './modify.vue'
import ViewPage from './view.vue'
const MODULE_CODE = 'scheduler'
const ENTITY_TYPE = 'schedulerJob'
export default {
  name: ENTITY_TYPE,
  components: {
    AddPage,
    ModifyPage,
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
      columnList: [
        {
          prop: 'name',
          label: '名称',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'jobName',
          label: '任务',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'cronExpression',
          label: '调度触发',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'statusName',
          label: '状态',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'orderNo',
          label: '排序',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'remark',
          label: '备注',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        }
      ],
      queryCondition: {
        //默认值处理
        status: ''
      }
    }
  },

  methods: {
    pauseAll() {
      this.$confirm('是否确认暂停全部', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.api.pauseAll().then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    resumeAll() {
      this.$confirm('是否确认恢复全部?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.api.resumeAll().then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    configParam(row) {
      this.$router.push({ path: '/scheduler/schedulerJobParam', query: { id: row.id } })
    },
    pause(row) {
      this.$confirm('是否确认暂停？', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.api.pause(row.id).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    resume(row) {
      this.$confirm('是否确认恢复？', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.api.resume(row.id).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    execute(row) {
      this.$confirm('是否立即执行一次该任务?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.api.execute(row.id)
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    }
  }
}
</script>
