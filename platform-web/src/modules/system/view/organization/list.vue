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
        <el-form-item label="类型">
          <dictionary-select v-model="queryCondition.type" multiple code="OrganizationType" />
        </el-form-item>
        <el-form-item label="状态">
          <dictionary-select v-model="queryCondition.status" multiple code="Status" />
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
        v-permission="pageCode + 'downloadImportTemplate'"
        type="primary"
        icon="Download"
        @click="downloadImportTemplate"
        >下载模板</el-button
      >
      <el-upload
        ref="uploader"
        :limit="1"
        :http-request="importData"
        action="/"
        :show-file-list="false"
        :before-upload="onBeforeUpload"
        accept=".xlsx,.xls"
        class="uploader"
      >
        <el-button v-permission="pageCode + 'import'" type="primary" icon="List"
          >批量导入
        </el-button>
      </el-upload>
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
        <el-table-column fixed="right" label="操作" width="250">
          <template #default="scope">
            <el-button v-permission="pageCode + 'modify'" @click="modify(scope.row)" type="primary"
              >修改</el-button
            >
            <el-button v-permission="pageCode + 'remove'" @click="remove(scope.row)" type="primary"
              >删除</el-button
            >

            <el-dropdown class="ml-10px">
              <el-button type="primary">
                更多
                <el-icon class="el-icon--right"><arrow-down /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>
                    <el-button v-permission="pageCode + 'enable'" text @click="enable(scope.row)"
                      >启用</el-button
                    >
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button v-permission="pageCode + 'disable'" text @click="disable(scope.row)"
                      >停用</el-button
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
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'organization'
export default {
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
          prop: 'code',
          label: '编码',
          show: true,
          showOverflowTooltip: true,
          sortable: true
        },
        {
          prop: 'typeName',
          label: '类型',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'statusName',
          label: '状态',
          show: true,
          showOverflowTooltip: true
        },
        {
          prop: 'organizationName',
          label: '上级组织',
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
        }
      ],
      queryCondition: {
        //默认值处理
        type: '',
        status: 'NORMAL'
      }
    }
  },

  methods: {
    add() {
      this.$refs.addPage.init({ id: this.queryCondition.organization })
    },
    commonParamChange(param) {
      this.queryCondition.organization = param.id
    },
    clearFile() {
      // 上传成功之后清除历史记录,否则再次上传浏览器无响应

      this.$refs.uploader.clearFiles()
    },
    onBeforeUpload(file) {
      const fileSuffix = file.name.substring(file.name.lastIndexOf('.') + 1)
      const whiteList = ['xls', 'xlsx']
      if (whiteList.indexOf(fileSuffix) === -1) {
        this.$message.error('导入文件只能是xls、xlsx格式')
        return false
      }
      const isSizeLimit = file.size / 1024 / 1024 < 10
      if (!isSizeLimit) {
        this.$message.error('上传文件大小不能超过 10MB')
        return false
      }
    }
  }
}
</script>

<style scoped>
:deep(.el-upload) {
  margin-left: 12px;
  display: inline;
  text-align: center;
  cursor: pointer;
  outline: 0;
}

:deep(.uploader) {
  display: inline;
}
</style>
