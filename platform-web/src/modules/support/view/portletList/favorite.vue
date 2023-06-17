<template>
  <div class="w-full">
    <div>
      <el-row>
        <el-col>
          <ul style="margin: 0px; padding: 0px">
            <li v-for="item in entityData" :key="item.id" class="view-li">
              <el-icon>
                <CaretRight />
              </el-icon>
              <a :title="item.name" :href="item.url" target="_blank">{{ item.name }}</a>
            </li>
          </ul>
        </el-col>
      </el-row>
    </div>
    <el-dialog
      v-model="visible"
      append-to-body
      width="500px"
      title="收藏项配置"
      class="custom"
      @close="close"
    >
      <div style="text-align: right; margin-right: 10px; cursor: pointer">
        <el-icon size="18" class="mr-3">
          <Plus @click="addRow" />
        </el-icon>
      </div>
      <el-row>
        <el-col>
          <ul style="margin: 0px; padding: 0px">
            <draggable
              v-model="entityData"
              @start="drag = true"
              @end="drag = false"
              item-key="index"
            >
              <template #item="{ element }">
                <li class="edit-li">
                  <div style="width: 95%; display: inline-block; padding: 5px 3px">
                    <label for="name" class="mr-3 mb-3">名称</label>
                    <el-input id="name" v-model="element.name" style="width: 90%" class="mb-3" />
                    <br />
                    <label for="url" class="mr-3 mb-3">地址</label>
                    <el-input id="url" v-model="element.url" style="width: 90%" class="mb-3" />
                  </div>
                  <div class="del">
                    <el-icon size="18">
                      <Delete @click="delRow(element)" />
                    </el-icon>
                  </div>
                </li>
              </template>
            </draggable>
          </ul>
        </el-col>
      </el-row>

      <template #footer>
        <el-button type="primary" @click="confirm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { uuid } from '@/utils'
import draggable from 'vuedraggable'

export default {
  name: 'UserFavorite',
  components: {
    draggable
  },
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
      configData: {},
      visible: false,
      name: '名称',
      url: '地址',
      entityData: [],
      errMsg: ''
    }
  },
  watch: {
    configVisible(val) {
      this.visible = val
    },
    config: {
      handler() {
        this.configData = this.config
        if (this.configData) {
          this.configData.forEach((item) => {
            if (item.code === 'item') {
              this.entityData = JSON.parse(item.value)
            }
          })
        }
      },
      immediate: true
    }
  },
  methods: {
    confirm() {
      if (!this.validate()) {
        return
      }

      if (this.configData && this.configData.length > 0) {
        this.configData.forEach((item) => {
          if (item.code === 'item') {
            if (this.entityData) {
              this.entityData = this.entityData.filter((e) => e.name)
            }
            item.value = JSON.stringify(this.entityData)
          }
        })
      }
      this.$emit('update:config', this.configData)
      this.close()
    },
    close() {
      this.visible = false
      this.$emit('update:configVisible', false)
    },
    addRow() {
      const index = this.entityData.length
      this.entityData.splice(index + 1, 0, {
        rId: uuid(),
        name: '',
        url: ''
      })
    },
    delRow(row) {
      let index = -1
      if (row.id) {
        index = this.entityData.findIndex((x) => x.id === row.id)
      } else {
        index = this.entityData.findIndex((x) => x.rId === row.rId)
      }
      this.entityData.splice(index, 1)
    },
    validate() {
      const valid = this.entityData.filter((item) => !item.name || !item.url).length === 0
      if (!valid) {
        this.$message.error('存在名称或地址为空的数据')
      }
      return valid
    }
  }
}
</script>

<style scoped>
.view-li {
  line-height: 30px;
  width: 49%;
  float: left;
  overflow: hidden;
  word-break: keep-all;
  white-space: nowrap;
  text-overflow: ellipsis;
  cursor: pointer;
}
.edit-li {
  line-height: 30px;
  width: 100%;
  overflow: hidden;
  word-break: keep-all;
  white-space: nowrap;
  text-overflow: ellipsis;
  cursor: move;
}
.custom .el-dialog__body {
  max-height: 300px;
  overflow-y: auto;
}
.del {
  display: inline-block;
  vertical-align: middle;
}
</style>
