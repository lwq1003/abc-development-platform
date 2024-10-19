<template>
  <div class="w-full">
    <div>
      <el-table
        v-loading="loading"
        :data="tableData"
        style="width: 100%"
        highlight-current-row
        border
      >
        <el-table-column
          v-for="(item, index) in columnList"
          :key="index"
          :label="item.label"
          :prop="item.prop"
          :show-overflow-tooltip="item.showOverflowTooltip"
          :width="item.width"
          :formatter="item.formatFunc"
          :sortable="item.sortable"
        />
        <el-table-column fixed="right" label="操作" align="center" width="100">
          <template #default="scope">
            <el-button type="primary" text @click="handle(scope.row)">办理</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div
        style="
          color: grey;
          float: right;
          line-height: 30px;
          font-size: 14px;
          position: absolute;
          bottom: 0;
          right: 10px;
        "
      >
        <a @click="moreView()" class="cursor-pointer">更多…</a>
      </div>
    </div>
    <el-dialog v-model="visible" append-to-body width="500px" title="参数配置" @close="close">
      <el-form>
        <el-table
          :data="configData"
          highlight-current-row
          style="margin-top: 5px"
          border
          width="450px"
        >
          <el-table-column>
            <template #header>参数名</template>
            <template #default="scope">{{ scope.row.name }}</template>
          </el-table-column>
          <el-table-column>
            <template #header>参数值</template>
            <template #default="scope">
              <el-input v-model="scope.row.value" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="confirm()">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { formatTime } from '@/utils/TableColumnFormatter.js'
export default {
  name: 'TodoPortlet',
  components: {},
  props: {
    config: {
      type: Array,
      default: function () {
        return []
      }
    },
    configVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      visible: false,
      loading: false,
      tableData: [],
      configData: [],
      columnList: [
        // prop label show 必须定义， width/showOverflowTooltip/formatFunc/sortable 需要的话定义
        { prop: 'processDefinitionName', label: '流程类型', show: true, showOverflowTooltip: true },
        { prop: 'nodeName', label: '环节', show: true, showOverflowTooltip: true },
        { prop: 'businessNo', label: '单号', show: true },
        { prop: 'processApplyName', label: '申请人', show: true },
        {
          prop: 'createTime',
          label: '创建时间',
          show: true,
          formatFunc: formatTime
        }
      ]
    }
  },
  watch: {
    configVisible(val) {
      this.visible = val
    },
    config: {
      handler() {
        this.configData = this.config
      },
      immediate: true
    }
  },
  mounted: function () {
    this.getData()
  },
  methods: {
    // 加载数据
    getData() {
      this.loading = true
      const params = {}
      for (const item of this.configData) {
        params[item.code] = item.value
      }

      this.$api.workflow.todo
        .portlet(params)
        .then((res) => {
          this.tableData = res.data
        })
        .finally(() => {
          this.loading = false
        })
    },
    close() {
      this.visible = false
      this.$emit('update:configVisible', false)
    },
    confirm() {
      this.$emit('update:config', this.configData)
      this.close()
    },
    // 处理任务
    handle(row) {
      this.$router.push({ name: 'taskHandle', query: { taskId: row.id } })
    },
    moreView() {
      this.$router.push({ path: '/workflow/todo' })
    }
  }
}
</script>

<style scoped>
.view-li {
  line-height: 30px;
  width: 100%;
  float: left;
  overflow: hidden;
  word-break: keep-all;
  white-space: nowrap;
  text-overflow: ellipsis;
  cursor: pointer;
}

a:active {
  color: #10498f;
  text-decoration: none;
}
a:hover,
a:focus {
  color: #05386b;
  text-decoration: underline;
}
</style>
