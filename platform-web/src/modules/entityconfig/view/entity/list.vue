<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
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
      <el-button type="primary" icon="refresh" @click="refresh">刷新</el-button>
      <el-button v-permission="pageCode + 'add'" type="primary" icon="plus" @click="add"
        >新增
      </el-button>
      <el-button
        v-permission="pageCode + 'remove'"
        type="primary"
        icon="delete"
        @click="batchRemove"
        >删除
      </el-button>
      <el-button
        v-permission="pageCode + 'addByCopy'"
        type="primary"
        icon="CopyDocument"
        @click="addByCopy"
        >复制新增</el-button
      >
      <el-button
        v-permission="pageCode + 'generateTable'"
        type="primary"
        icon="Setting"
        @click="generateTable"
        >生成库表
      </el-button>
      <el-button
        v-permission="pageCode + 'generateCode'"
        type="primary"
        icon="Setting"
        @click="generateCode"
        >生成代码
      </el-button>
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
        <el-table-column fixed="right" label="操作" width="230">
          <template #default="scope">
            <el-button type="primary" v-permission="pageCode + 'config'" @click="config(scope.row)"
              >配置</el-button
            >
            <el-button type="primary" v-permission="pageCode + 'modify'" @click="modify(scope.row)"
              >修改</el-button
            >
            <el-button type="primary" v-permission="pageCode + 'remove'" @click="remove(scope.row)"
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
import ModuleReference from '@/modules/entityconfig/view/module/reference.vue'

const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'entity'
export default {
  name: ENTITY_TYPE,
  components: {
    AddPage,
    ModifyPage,
    ViewPage,
    ModuleReference
  },
  mixins: [listMixin],
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
      }
    }
  },

  methods: {
    generateTable() {
      this.$confirm('此操作将批量生成库表, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          if (!this.checkSelected()) {
            return
          }
          const ids = this.getCheckedId()
          this.api.generateTable(ids)
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    generateCode() {
      // TODO 为提升开发效率，临时取消确认，待恢复
      // this.$confirm('此操作将批量生成代码, 是否继续?', '确认', {
      //   type: 'warning'
      // })
      //   .then(() => {
      //     if (!this.checkSelected()) {
      //       return
      //     }
      //     const ids = this.getCheckedId()
      //     this.api.generateCode(ids)
      //   })
      //   .catch(() => {
      //     this.$message.info('已取消')
      //   })
      if (!this.checkSelected()) {
        return
      }
      const ids = this.getCheckedId()
      this.api.generateCode(ids)
    },

    config(row) {
      this.$router.push({ path: '/entityconfig/configEntity', query: { id: row.id } })
    }
  }
}
</script>
