<template>
  <Dialog title="选择模板按钮" v-model="visible" width="30%">
    <el-checkbox-group v-model="checkedButtonList" style="margin-left: 20px">
      <el-checkbox v-for="item in buttonList" :key="item.code" :label="item.code"
        >{{ item.name }}
      </el-checkbox>
    </el-checkbox-group>

    <template #footer>
      <el-button type="primary" @click="save">确认</el-button>
    </template>
  </Dialog>
</template>

<script>
import { Dialog } from '@/components/abc/Dialog'

export default {
  name: 'ViewButtonSelect',
  components: { Dialog },
  data() {
    return {
      visible: false,
      entityViewId: '',
      buttonType: '',
      buttonList: [],
      checkedButtonList: [],
      // 排序信息
      sortInfo: {
        sort_field: 'orderNo',
        sort_sortType: 'ascending'
      }
    }
  },

  methods: {
    init(entityViewId, buttonType) {
      this.entityViewId = entityViewId
      this.buttonType = buttonType
      const param = Object.assign({ buttonType: this.buttonType }, this.sortInfo)
      this.$api.entityconfig.viewButtonTemplate.list(param).then((res) => {
        this.buttonList = res.data
        this.visible = true
      })
    },
    save() {
      this.$api.entityconfig.viewButton
        .addFromTemplate(this.entityViewId, this.buttonType, this.checkedButtonList)
        .then(() => {
          this.checkedButtonList = []
          this.$emit('refresh')
          this.visible = false
        })
    }
  }
}
</script>

<style scoped></style>
