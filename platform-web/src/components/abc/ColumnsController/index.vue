<template>
  <el-popover placement="bottom" trigger="click" :visible="visible" style="margin-top: 0px">
    <div>
      <div>
        <el-checkbox
          v-model="checkAll"
          :indeterminate="indeterminate"
          style="display: inline-block; font-weight: bold; margin-top: 8px"
          @change="handleCheckAll"
          >全选</el-checkbox
        >
        <div class="close" style="display: inline-block; float: right">
          <Icon icon="material-symbols:close" @click="close" :size="18" />
          <Icon icon="line-md:confirm" @click="confirm" :size="18" />
        </div>
      </div>
      <div style="height: 1px; background-color: #dcdfe6"></div>
      <el-checkbox-group v-model="selectedCols" @change="handleCheckChange">
        <el-checkbox v-for="(item, i) in value" :key="i" :label="item.prop">{{
          item.label
        }}</el-checkbox>
      </el-checkbox-group>
    </div>
    <template #reference>
      <el-button style="padding: 5px 7px" @click="visible = !visible">
        <Icon icon="heroicons:view-columns" />
      </el-button>
    </template>
  </el-popover>
</template>

<script>
export default {
  props: {
    value: {
      type: Array,
      required: false
    },
    tableKey: {
      type: String,
      default: 'key'
    }
  },
  data() {
    const selColsStr = window.localStorage.getItem(this.tableKey)
    const selCols =
      (selColsStr && JSON.parse(selColsStr)) ||
      (this.value && this.value.filter((item) => item.show).map((item) => item.prop))
    const tempList = [...this.value]
    tempList.forEach((item) => {
      item.show = selCols.includes(item.prop)
    })
    this.$emit('input', [...tempList])
    return {
      visible: false,
      checkAll: this.value.findIndex((item) => !item.show) === -1,
      indeterminate:
        this.value.findIndex((item) => !item.show) !== -1 &&
        this.value.findIndex((item) => item.show) !== -1,
      selectedCols: selCols
    }
  },
  methods: {
    handleCheckAll(val) {
      this.selectedCols = val ? this.value.map((item) => item.prop) : []
      this.indeterminate = false
    },
    handleCheckChange(val) {
      const checkedCount = val.length
      this.checkAll = checkedCount === this.value.length
      this.indeterminate = checkedCount > 0 && checkedCount < this.value.length
    },
    close() {
      this.visible = false
    },
    confirm() {
      const tempList = [...this.value]
      tempList.forEach((item) => {
        item.show = this.selectedCols.includes(item.prop)
      })
      this.$emit('input', [...tempList])
      window.localStorage.setItem(this.tableKey, JSON.stringify(this.selectedCols))
      this.visible = false
    }
  }
}
</script>

<style>
.col-controller .el-checkbox {
  display: block;
  margin: 10px;
}

.close {
  display: inline-block;
  line-height: 39px;
}

.close i {
  border: 1px solid #dcdfe6;
  margin-right: 5px;
  cursor: pointer;
  /* &:hover {
          border: 1px solid #1890ff;
          color: #1890ff;
   } */
}
</style>
