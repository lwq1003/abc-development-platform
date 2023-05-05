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
    <Dialog title="消息主题选择" v-model="visible" class="w-150">
      <CollapseTab>
        <el-form :inline="true" :model="queryCondition" label-width="80px" @keyup.enter="query">
          <!--查询条件区 -->
          <el-form-item label="名称">
            <QueryText v-model="queryCondition.name" type="LK" />
          </el-form-item>
          <el-form-item label="分类">
            <dictionary-select v-model="queryCondition.category" code="ApiMessageTopicCategory" />
          </el-form-item>
          <el-form-item label="编码">
            <QueryText v-model="queryCondition.code" type="LK" />
          </el-form-item>
          <el-form-item label="状态" v-show="false">
            <dictionary-radio-group v-model="queryCondition.status" code="Status" />
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
const MODULE_CODE = 'cip'
const ENTITY_TYPE = 'messageTopic'
export default {
  name: ENTITY_TYPE + '-reference',
  components: {},
  mixins: [referenceMixin],
  props: {
    messageTopicParam: {
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
        }
      ],
      queryCondition: {
        //默认值处理
        category: '',
        status: 'NORMAL'
      },
      // 名称键值
      nameKey: 'name'
    }
  },
  methods: {}
}
</script>

<style></style>
