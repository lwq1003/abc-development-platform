<template>
  <div class="w-full">
    <el-input v-model="displayName" disabled>
      <template #append>
        <el-button-group>
          <el-button icon="grid" @click="init" />
          <el-button icon="delete" @click="clear" />
        </el-button-group>
      </template>
    </el-input>
    <Dialog title="实体选择" v-model="visible" width="60%">
      <CollapseTab>
        <el-form :inline="true" :model="queryCondition" label-width="80px" @keyup.enter="query">
          <!--查询条件区 -->
          <el-form-item label="模块">
            <ModuleReference v-model="queryCondition.module" :module-param="moduleParam" />
          </el-form-item>
          <el-form-item label="名称">
            <QueryText v-model="queryCondition.name" type="LK" />
          </el-form-item>
          <el-form-item label="编码">
            <QueryText v-model="queryCondition.code" type="LK" />
          </el-form-item>
          <el-form-item label="作者">
            <QueryText v-model="queryCondition.author" type="LK" />
          </el-form-item>
          <el-form-item style="float: right">
            <QueryButton :page-code="pageCode" />
          </el-form-item>
          <div class="clearfix"></div>
        </el-form>
      </CollapseTab>
      <div class="mb-10px mt-10px">
        <el-button type="primary" icon="check" @click="confirm">确定</el-button>
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
          @row-dblclick="rowDoubleClick"
        >
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
        </el-table>
        <ListPager
          :page-num="pageInfo.pageNum"
          :page-size="pageInfo.pageSize"
          :page-total="pageTotal"
        />
      </el-card>
      <ViewPage ref="viewPage" />
    </Dialog>
  </div>
</template>

<script>
import { referenceMixin } from '@/mixin/referenceMixin.js'
import ViewPage from './view.vue'
import ModuleReference from '@/modules/entityconfig/view/module/reference.vue'
const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'entity'
export default {
  name: ENTITY_TYPE + '-reference',
  components: {
    ViewPage,
    ModuleReference
  },
  mixins: [referenceMixin],
  props: {
    entityParam: {
      type: Object,
      required: false
    }
  },
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      // 模块组件参数，用于传递数据
      moduleParam: {},
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
          prop: 'author',
          label: '作者',
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
          prop: 'moduleName',
          label: '模块',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        }
      ],
      queryCondition: {
        //默认值处理
      },
      // 名称键值
      nameKey: 'name'
    }
  },
  methods: {}
}
</script>

<style></style>
