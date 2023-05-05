<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="实体模型" v-show="false">
          <QueryText v-model="queryCondition.entityModel" type="EQ" />
        </el-form-item>
        <el-form-item label="名称">
          <QueryText v-model="queryCondition.name" type="LK" />
        </el-form-item>
        <el-form-item label="编码">
          <QueryText v-model="queryCondition.code" type="LK" />
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
      <div style="float: right; margin-top: 0; margin-bottom: 10px">
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
        <el-table-column fixed="right" label="操作" width="180">
          <template #default="scope">
            <el-button type="primary" @click="modify(scope.row)">修改</el-button>
            <el-button type="primary" @click="remove(scope.row)">删除</el-button>
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
const ENTITY_TYPE = 'entityModelProperty'
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
      columnList: [
        {
          prop: 'name',
          label: '名称',
          show: true,
          width: 120,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'code',
          label: '编码',
          show: true,
          width: 120,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'dataTypeName',
          label: '数据类型',
          show: true,
          width: 120,
          showOverflowTooltip: true
        },
        {
          prop: 'dictionaryType',
          label: '字典类型',
          show: true,
          width: 120,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'widgetType',
          label: '控件类型',
          show: true,
          width: 120,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'formatPattern',
          label: '显示格式',
          show: true,
          width: 120,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'maxLength',
          label: '最大长度',
          show: true,
          width: 120,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'decimalLength',
          label: '小数位数',
          show: true,
          width: 120,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'defaultValue',
          label: '默认值',
          show: true,
          width: 120,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'nullFlagName',
          label: '是否可为空',
          show: true,
          width: 120,
          showOverflowTooltip: true
        },
        {
          prop: 'uniqueFlagName',
          label: '是否唯一',
          show: true,
          width: 120,
          showOverflowTooltip: true
        },
        {
          prop: 'entityModelProperty',
          label: '唯一性参照',
          show: true,
          width: 120,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'mainFlagName',
          label: '是否主属性',
          show: true,
          width: 120,
          showOverflowTooltip: true
        },
        {
          prop: 'parentPropertyFlagName',
          label: '是否上级属性',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'orderNo',
          label: '排序',
          show: true,
          width: 120,
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
      this.queryCondition.entityModel = this.$route.query.id
    },
    addWithId() {
      this.$refs.addPage.init({ id: this.$route.query.id })
    }
  }
}
</script>
