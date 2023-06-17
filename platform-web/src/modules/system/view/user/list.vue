<template>
  <ContentWrap>
    <CollapseTab>
      <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
        <!--查询条件区 -->
        <el-form-item label="姓名">
          <QueryText v-model="queryCondition.name" type="LK" />
        </el-form-item>
        <el-form-item label="账号">
          <QueryText v-model="queryCondition.account" type="LK" />
        </el-form-item>
        <el-form-item label="状态">
          <dictionary-select v-model="queryCondition.status" multiple code="UserStatus" />
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
      <el-button
        v-permission="pageCode + 'export'"
        type="primary"
        icon="Document"
        @click="exportData"
        class="ml-3"
        >批量导出
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
        <el-table-column fixed="right" label="操作" width="270">
          <template #default="scope">
            <el-button
              v-permission="pageCode + 'configUserGroup'"
              type="primary"
              @click="configUserGroup(scope.row)"
              >用户组</el-button
            >
            <el-button v-permission="pageCode + 'modify'" type="primary" @click="modify(scope.row)"
              >修改</el-button
            >
            <el-dropdown class="ml-10px">
              <el-button type="primary">
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
                    <el-button text v-permission="pageCode + 'enable'" @click="enable(scope.row)"
                      >启用</el-button
                    >
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button text v-permission="pageCode + 'disable'" @click="disable(scope.row)"
                      >停用</el-button
                    >
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button
                      text
                      v-permission="pageCode + 'resetPassword'"
                      @click="resetPassword(scope.row)"
                      >重置密码</el-button
                    >
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button text v-permission="pageCode + 'unlock'" @click="unlock(scope.row)"
                      >手工解锁</el-button
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
    <UserGroup ref="userGroup" @confirm="saveUserGroup" />
  </ContentWrap>
</template>

<script lang="ts">
import { listMixin } from '@/mixin/listMixin.js'
import AddPage from './add.vue'
import ModifyPage from './modify.vue'
import ViewPage from './view.vue'
import UserGroup from '@/modules/system/view/userGroup/treeMultipleDirectReference.vue'
import { getFormatMethod } from '@/utils/TableColumnFormatter.js'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'user'
export default {
  components: {
    AddPage,
    ModifyPage,
    ViewPage,
    UserGroup
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
          label: '姓名',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'account',
          label: '账号',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'organizationName',
          label: '组织机构',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'genderName',
          label: '性别',
          show: true,
          showOverflowTooltip: true,
          width: '120'
        },
        {
          prop: 'telephone',
          label: '手机号',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'email',
          label: '邮箱',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'position',
          label: '职位',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        },
        {
          prop: 'birthday',
          label: '出生日期',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true,
          formatFunc: getFormatMethod('FORMAT_DATE')
        },
        {
          prop: 'statusName',
          label: '状态',
          show: true,
          showOverflowTooltip: true,
          width: '120'
        },
        {
          prop: 'lockTime',
          label: '锁定时间',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true,
          formatFunc: getFormatMethod('FORMAT_TIME')
        },
        {
          prop: 'orderNo',
          label: '排序',
          show: true,
          showOverflowTooltip: true,
          width: '120',
          sortable: true
        }
      ],
      queryCondition: {
        //默认值处理
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
    configUserGroup(row) {
      this.currentId = row.id
      this.api.getUserGroup(row.id).then((res) => {
        this.$refs.userGroup.init({ data: res.data })
      })
    },
    resetPassword(row) {
      this.$confirm('是否重置密码？', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.api.resetPassword(row.id)
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    unlock(row) {
      this.$confirm('是否解锁账号？', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.api.unlock(row.id).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 保存用户与用户组对应
    saveUserGroup(userGroupIdList) {
      this.api.saveUserGroup(this.currentId, userGroupIdList)
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
