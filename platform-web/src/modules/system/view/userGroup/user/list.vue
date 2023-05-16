<template>
  <el-row :gutter="5">
    <el-col :span="20">
      <CollapseTab>
        <el-form :inline="true" :model="queryCondition" label-width="120px" @keyup.enter="query">
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
          <el-form-item>
            <el-checkbox v-model="queryCondition.ignoreUserGroup">全部用户</el-checkbox>
          </el-form-item>
          <el-form-item style="float: right">
            <QueryButton :page-code="pageCode" />
          </el-form-item>
          <div class="clearfix"></div>
        </el-form>
      </CollapseTab>
      <div class="mb-10px mt-10px">
        <el-button type="primary" icon="refresh" @click="refresh">刷新</el-button>
        <el-button
          v-permission="'system:userGroup:configUser'"
          type="primary"
          icon="delete"
          @click="batchRemove"
          >移除</el-button
        >
        <el-button
          v-permission="'system:userGroup:configUser'"
          type="primary"
          icon="CopyDocument"
          @click="batchAdd"
          >添加</el-button
        >
      </div>

      <el-card>
        <div style="float: right; margin-top: 0; margin-bottom: 10px">
          <ColumnsController :value="columnList" :tableKey="tableKey" />
        </div>

        <el-table
          v-loading="loading"
          :data="tableData"
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
                v-permission="'system:userGroup:configUser'"
                type="primary"
                @click="remove(scope.row)"
                >移除</el-button
              >
              <el-button
                v-permission="'system:userGroup:configUser'"
                type="primary"
                @click="add(scope.row)"
                >添加</el-button
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
    </el-col>
    <el-col :span="4">
      <el-card class="h-full w-full">
        <el-button
          v-permission="'system:userGroup:configUser'"
          type="primary"
          @click="save"
          class="mb-5"
          >保存</el-button
        >
        <el-scrollbar max-height="800px">
          <el-row v-for="element in userList" :key="element.id">
            <el-col>
              <el-tag closable @close="removeFromList(element)">
                {{ element.name }} {{ element.account }}</el-tag
              >
            </el-col>
          </el-row>
        </el-scrollbar>
      </el-card>
    </el-col>
  </el-row>
</template>

<script lang="ts">
import { listMixin } from '@/mixin/listMixin.js'

const MODULE_CODE = 'system'
const ENTITY_TYPE = 'user'
export default {
  components: {},
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
        }
      ],
      queryCondition: {
        //默认值处理
        status: 'NORMAL'
      },
      userGroupId: '',
      userList: []
    }
  },

  methods: {
    // 加载数据
    loadData() {
      return new Promise((resolve) => {
        this.loading = true
        const params = Object.assign(this.queryCondition, this.pageInfo, this.sortInfo)
        this.$api.system.user
          .getUserByUserGroupId(this.userGroupId, params)
          .then((res) => {
            this.tableData = res.data.records
            this.pageTotal = res.data.total
            resolve()
          })
          .finally(() => {
            this.loading = false
            this.currentId = this.$constant.NO_ITEM_SELECTED
          })
      })
    },
    // 批量移除
    batchRemove() {
      if (!this.checkSelected()) {
        return
      }
      this.$confirm('此操作将批量解除用户组与用户关联关系, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          const ids = this.getCheckedIdList()

          this.$api.system.userGroup.batchRemoveUser(this.userGroupId, ids).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 单条移除
    remove(row) {
      this.$confirm('此操作将解除用户组与用户关联关系, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.$api.system.userGroup.batchRemoveUser(this.userGroupId, [row.id]).then(() => {
            this.refresh()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 从列表中移除
    removeFromList(row) {
      this.$confirm('此操作将从待添加列表中移除该用户, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.userList = this.userList.filter((item) => item.id !== row.id)
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },

    commonParamChange(param) {
      this.userGroupId = param.userGroupId
      this.queryCondition.organization = param.id
    },

    // 添加单个用户
    add(row) {
      const exist = this.userList.some((item) => item.id === row.id)
      if (!exist) {
        this.userList.push(row)
      } else {
        this.$message.info('已存在，无需重复添加')
      }
    },
    // 批量添加
    batchAdd() {
      if (!this.checkSelected()) {
        return
      }
      this.multipleSelection.forEach((row) => {
        const exist = this.userList.some((item) => item.id === row.id)
        if (!exist) {
          this.userList.push(row)
        }
      })
    },
    // 保存用户与用户组对应
    save() {
      const ids = this.userList.map((item) => item.id)
      this.$api.system.userGroup.addUser(this.userGroupId, ids)
    }
  }
}
</script>

<style>
.el-row {
  margin-bottom: 10px;
}
</style>
