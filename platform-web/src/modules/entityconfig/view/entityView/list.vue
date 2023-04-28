<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="名称">
          <QueryText v-model="queryCondition.name" type="LK" />
        </el-form-item>
        <el-form-item label="编码">
          <QueryText v-model="queryCondition.code" type="LK" />
        </el-form-item>
        <el-form-item label="实体" v-show="false">
          <QueryText v-model="queryCondition.entity" type="EQ" />
        </el-form-item>
        <el-form-item style="float: right">
          <QueryButton :page-code="pageCode" />
        </el-form-item>
        <div class="clearfix"></div>
      </el-form>
    </CollapseTab>
    <div class="mb-10px mt-10px">
      <el-button type="primary" icon="refresh" @click="refresh">刷新</el-button>
      <el-button v-permission="pageCode + 'add'" type="primary" icon="plus" @click="addWithId"
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
        <el-table-column fixed="right" label="操作" width="350">
          <template #default="scope">
            <el-button
              v-permission="pageCode + 'configView'"
              type="primary"
              @click="configView(scope.row)"
              >配置</el-button
            >
            <el-button v-permission="pageCode + 'modify'" type="primary" @click="modify(scope.row)"
              >修改</el-button
            >
            <el-button v-permission="pageCode + 'remove'" type="primary" @click="remove(scope.row)"
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
const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'entityView'
export default {
  components: {
    AddPage,
    ModifyPage,
    ViewPage
  },
  mixins: [listMixin],
  data() {
    return {
      name: ENTITY_TYPE + '-list',
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 实体模型组件参数，用于传递数据
      entityModelParam: {},
      columnList: [
        {
          prop: 'entityViewTypeName',
          label: '视图类型',
          show: true,
          showOverflowTooltip: true
        },
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
          prop: 'orderNo',
          label: '排序',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'entityModelName',
          label: '实体模型',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        }
      ],
      queryCondition: {
        //默认值处理
      }
    }
  },

  methods: {
    beforeInit(param) {
      this.queryCondition.entity = this.$route.query.id
    },
    addWithId() {
      this.$refs.addPage.init({ id: this.$route.query.id })
    },
    configView(row) {
      // 过滤无需配置的视图类型
      if (
        row.entityViewType == 'TREE' ||
        row.entityViewType == 'TREELIST' ||
        row.entityViewType == 'TREEREFERENCE' ||
        row.entityViewType == 'TREEMULTIPLEREFERENCE'
      ) {
        this.$message.info('无需配置')
        return
      }

      let viewType = ''
      switch (row.entityViewType) {
        case 'LIST':
        case 'REFERENCE': {
          viewType = 'list'
          break
        }
        case 'ADD':
        case 'MODIFY':
        case 'VIEW': {
          viewType = 'detail'
          break
        }
        default: {
          this.$message.error('未设置视图配置模板')
          return
        }
      }

      this.$router.push({ path: '/entityconfig/' + viewType + 'ViewConfig', query: { id: row.id } })
    }
  }
}
</script>
