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
        <el-table-column fixed="right" label="操作" width="320">
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
import UserGroup from '@/modules/system/view/userGroup/treemultiplereference.vue'
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
      name: ENTITY_TYPE + '-list',
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
          sortable: true
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
          sortable: true
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
    }
  }
}
</script>
