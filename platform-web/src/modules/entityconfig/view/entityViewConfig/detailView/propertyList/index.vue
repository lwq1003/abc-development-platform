<template>
  <el-row :gutter="20">
    <el-col :span="24">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>属性列表</span>
            <span style="float: right">
              <el-button style="padding: 0; border: 0" icon="right" @click="addAll" />
            </span>
          </div>
        </template>

        <draggable
          v-model="allPropertyList"
          :group="allPropertyGroup"
          :move="move"
          item-key="index"
        >
          <template #item="{ element }">
            <el-row>
              <el-col>
                <el-tag>{{ element.name }}</el-tag>
              </el-col>
            </el-row>
          </template>
        </draggable>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import Draggable from 'vuedraggable'
import { throttle } from 'lodash'
export default {
  components: { Draggable },
  data() {
    return {
      entityViewId: '',
      allPropertyList: [],
      allPropertyGroup: {
        name: 'list',
        pull: 'clone',
        put: false
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
      this.$api.entityconfig.entityView.get(this.entityViewId).then((res) => {
        const entityModel = res.data.entityModel
        this.$api.entityconfig.entityModelProperty.getFullPropertyList(entityModel).then((res) => {
          this.allPropertyList = res.data
          this.$emit('get-all-property-data', this.allPropertyList)
        })
      })
    },
    // 全部添加
    addAll() {
      this.$confirm(
        '此操作将把所有属性批量添加作为视图属性,已添加和配置的属性不会覆盖，是否继续?',
        '确认',
        {
          type: 'warning'
        }
      )
        .then(() => {
          this.$api.entityconfig.viewProperty
            .addBatchFromModelProperty(this.entityViewId)
            .then(() => {
              this.$emit('refresh-property')
            })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 移动
    move: throttle(function (e) {
      // 处理拖动事件
      const code = e.draggedContext.element.code
      const list = e.relatedContext.list
      const exist = list.some((item) => {
        return item.code === code
      })
      return !exist
    }, 500)
  }
}
</script>

<style>
.el-row {
  margin-bottom: 10px;
}
</style>
