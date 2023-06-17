<template>
  <div class="w-full">
    <div>
      <el-row>
        <el-col>
          <ul style="margin: 0px; padding: 0px">
            <li v-for="item in entityData" :key="item.id" class="view-li">
              <el-tag v-if="item.topFlag === 'YES'" type="danger">置顶</el-tag>
              <el-tag v-if="item.importantFlag === 'YES'" type="warning">重要</el-tag>
              <a :title="item.title" @click="view(item)">
                <span>&nbsp;&nbsp;{{ item.title }}&nbsp;&nbsp;</span>
                <span>{{ $dateFormatter.formatUTCDate(item.publishTime) }}</span>
              </a>
            </li>
          </ul>
        </el-col>
      </el-row>
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
        <a @click="moreView()">更多…</a>
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
export default {
  name: 'NoticePortlet',
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
      entityData: [],
      configData: []
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
      const params = {}
      for (const item of this.configData) {
        params[item.code] = item.value
      }
      this.$api.support.notice.portlet(params).then((res) => {
        this.entityData = res.data
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
    view(row) {
      this.$router.push({ path: '/support/noticeView', query: { id: row.id } })
    },
    moreView() {
      this.$router.push({ path: '/support/noticeViewList' })
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
