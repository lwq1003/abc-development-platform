<template>
  <div class="w-full">
    <el-input v-model="displayName" disabled style="width: 152px" />
    <el-button-group>
      <el-button icon="grid" @click="init" style="border-left-width: 0; padding: 10px" />
      <el-button icon="delete" @click="clear" style="border-left-width: 0; padding: 10px" />
    </el-button-group>
    <Dialog title="用户选择" v-model="visible" class="w-150">
      <CollapseTab>
        <el-form :inline="true" :model="queryCondition" label-width="80px" @keyup.enter="query">
          <!--查询条件区 -->
          <el-form-item label="姓名">
            <QueryText v-model="queryCondition.name" type="LK" />
          </el-form-item>
          <el-form-item label="账号">
            <QueryText v-model="queryCondition.account" type="LK" />
          </el-form-item>
          <el-form-item>
            <el-checkbox v-model="queryCondition.ignoreParent">查询全部</el-checkbox>
          </el-form-item>
          <el-form-item style="float: right">
            <QueryButton :page-code="pageCode" />
          </el-form-item>
          <div class="clearfix"></div>
        </el-form>
      </CollapseTab>
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
      <template #footer>
        <el-button type="primary" @click="confirm">确定</el-button>
        <el-button @click="close">关闭</el-button>
      </template>
    </Dialog>
  </div>
</template>

<script>
import { referenceMixin } from '@/mixin/referenceMixin.js'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'user'
export default {
  name: ENTITY_TYPE + '-reference',
  components: {},
  mixins: [referenceMixin],
  props: {
    userParam: {
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
      // 组织机构组件参数，用于传递数据
      organizationParam: {},
      columnList: [
        {
          prop: 'name',
          label: '姓名',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'account',
          label: '账号',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'organizationName',
          label: '组织机构',
          show: true,
          showOverflowTooltip: true
        }
      ],
      queryCondition: {
        //默认值处理
      },
      // 名称键值
      nameKey: 'name'
    }
  },
  methods: {
    commonParamChange(param) {
      this.queryCondition.organization = param.id
    }
  }
}
</script>

<style></style>
