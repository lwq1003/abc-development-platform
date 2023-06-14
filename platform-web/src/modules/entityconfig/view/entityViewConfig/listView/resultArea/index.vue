<template>
  <el-row :gutter="20">
    <el-col :span="24">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>查询结果</span>
            <span style="float: right">
              <el-button style="padding: 0; border: 0" icon="plus" @click="addWithId" />
              <el-button style="padding: 0; border: 0" icon="delete" @click="clear" />
            </span>
          </div>
        </template>
        <el-scrollbar max-height="200px">
          <draggable
            v-model="queryResultList"
            :group="group"
            style="height: 200px"
            @add="addFromModelProperty"
            @update="updateSort"
            item-key="index"
          >
            <template #item="{ element }">
              <el-row>
                <el-col>
                  <el-tag closable @close="remove(element.id)" @click="modify(element.id)">
                    {{ element.name }}</el-tag
                  >
                </el-col>
              </el-row>
            </template>
          </draggable>
        </el-scrollbar>
      </el-card>
      <AddPage ref="addPage" @refresh="refresh" />
      <ModifyPage ref="modifyPage" @refresh="refresh" />
    </el-col>
  </el-row>
</template>

<script>
import AddPage from './add.vue'
import ModifyPage from './modify.vue'
import Draggable from 'vuedraggable'
export default {
  components: { AddPage, ModifyPage, Draggable },
  props: {
    propertyListData: {
      type: Array,
      default: () => [],
      required: false
    }
  },
  data() {
    return {
      entityViewId: '',
      queryResultList: [],
      group: {
        name: 'list',
        pull: false,
        put: true
      }
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    // 初始化
    init() {
      this.entityViewId = this.$route.query.id
      this.query()
    },
    // 新增
    addWithId() {
      this.$refs.addPage.init({ id: this.$route.query.id })
    },
    // 修改
    modify(id) {
      this.$refs.modifyPage.init(id)
    },
    // 新增
    addFromModelProperty(evt) {
      const code = this.propertyListData[evt.oldIndex].code
      this.$api.entityconfig.viewQueryResult
        .addFromModelProperty(this.entityViewId, code)
        .then(() => {
          this.sort()
        })
    },

    // 移除
    remove(queryConditionId) {
      this.$confirm('此操作将移除该列, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.$api.entityconfig.viewQueryResult.remove(queryConditionId).then(() => {
            this.query()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },

    // 加载列表
    query() {
      this.$api.entityconfig.viewQueryResult.listByView(this.entityViewId).then((res) => {
        this.queryResultList = res.data
      })
    },
    // 拖拽结束
    updateSort(evt) {
      evt.preventDefault()
      this.sort()
    },
    // 排序
    sort() {
      const sortedList = this.queryResultList.map(function (value, index) {
        return { index: index, code: value.code }
      })
      this.$api.entityconfig.viewQueryResult.updateSort(this.entityViewId, sortedList).then(() => {
        this.query()
      })
    },
    // 清空
    clear() {
      this.$confirm('此操作将移除所有查询结果属性,已配置信息丢失且不可恢复，是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.$api.entityconfig.viewQueryResult.clear(this.entityViewId).then(() => {
            this.query()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    refresh() {
      this.query()
    }
  }
}
</script>

<style></style>
