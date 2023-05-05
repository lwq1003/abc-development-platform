<template>
  <el-row :gutter="20" type="flex">
    <el-col :span="24">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <span>{{ butttonGroup }}按钮配置</span>
            <span style="float: right">
              <el-button style="padding: 0; border: 0" icon="SetUp" @click="addFromTemplate" />
              <el-button style="padding: 0; border: 0" icon="Plus" @click="add" />
              <el-button style="padding: 0; border: 0" icon="Delete" @click="clear" />
            </span>
          </div>
        </template>
        <draggable v-model="buttonList" :group="group" @update="updateSort" item-key="index">
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

        <AddPage ref="addPage" @refresh="refresh" />
        <ModifyPage ref="modifyPage" @refresh="refresh" />
        <TempalteButtonSelect ref="tempalteButtonSelect" @refresh="refresh" />
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import AddPage from '../../viewButton/add.vue'
import ModifyPage from '../../viewButton/modify.vue'
import TempalteButtonSelect from '../../viewButton/select.vue'
import Draggable from 'vuedraggable'
export default {
  components: {
    Draggable,
    AddPage,
    ModifyPage,
    TempalteButtonSelect
  },
  props: {
    buttonType: {
      type: String,
      default: '',
      required: false
    }
  },
  data() {
    return {
      entityViewId: '',
      buttonList: [],
      buttonGroup: '',
      group: {
        name: 'pageButton',
        pull: false,
        put: false
      }
    }
  },
  computed: {
    butttonGroup() {
      if (this.buttonType !== this.$constant.VIEW_BUTTON_TYPE_ITEM.LIST_ROW) {
        return '页'
      } else {
        return '行'
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
    // 从模板新增
    addFromTemplate() {
      this.$refs.tempalteButtonSelect.init(this.entityViewId, this.buttonType)
    },
    // 新增
    add() {
      this.$refs.addPage.init({ id: this.entityViewId, buttonType: this.buttonType })
    },
    // 修改
    modify(id) {
      this.$refs.modifyPage.init(id)
    },
    // 移除
    remove(id) {
      this.$confirm('此操作将移除' + this.butttonGroup + '按钮, 是否继续?', '确认', {
        type: 'warning'
      })
        .then(() => {
          this.$api.entityconfig.viewButton.remove(id).then(() => {
            this.query()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },
    // 清空
    clear() {
      this.$confirm(
        '此操作将移除所有' + this.butttonGroup + '按钮,已配置信息丢失且不可恢复，是否继续?',
        '确认',
        {
          type: 'warning'
        }
      )
        .then(() => {
          this.$api.entityconfig.viewButton.clear(this.entityViewId, this.buttonType).then(() => {
            this.query()
          })
        })
        .catch(() => {
          this.$message.info('已取消')
        })
    },

    // 加载按钮列表
    query() {
      this.$api.entityconfig.viewButton
        .listByViewAndType(this.entityViewId, this.buttonType)
        .then((res) => {
          this.buttonList = res.data
        })
    },

    // 更新次序
    updateSort(evt) {
      evt.preventDefault()
      const sortedButtonList = this.buttonList.map(function (value, index) {
        return { index: index, code: value.code }
      })
      this.$api.entityconfig.viewButton.updateButtonSort(this.entityViewId, sortedButtonList)
    },
    refresh() {
      this.query()
    }
  }
}
</script>

<style>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-row {
  margin-bottom: 10px;
}
</style>
