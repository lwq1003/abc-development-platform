<template>
  <el-select
    v-model="selectedValue"
    :size="size"
    clearable
    :disabled="readonly"
    style="width: 200px; margin: 20 auto"
    @change="change"
  >
    <el-option
      v-for="item in dictionaryItemList"
      :key="item.id"
      :value="item.id"
      :label="item.name"
    />
  </el-select>
</template>

<script>
export default {
  name: 'QueryPlanSelect',
  label: '查询方案下拉',
  props: {
    modelValue: {
      type: String,
      required: false,
      default: ''
    },
    code: {
      type: String,
      default: ''
    },
    readonly: {
      type: Boolean,
      required: false,
      default: false
    },
    size: {
      type: String,
      default: ''
    },
    entityModelCode: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      dictionaryItemList: [],
      selectedValue: ''
    }
  },
  watch: {
    modelValue: {
      immediate: true,
      handler: 'setSelected'
    },
    entityModelCode: {
      immediate: true,
      handler: 'loadData'
    }
  },
  methods: {
    change(value) {
      let rowData = null
      this.dictionaryItemList.forEach((item) => {
        if (item.id === value) {
          rowData = item
          return
        }
      })
      // 更新绑定值
      this.$emit('update:modelValue', value)
      // 注意，此处若命令为change，则可能会与底层实现冲突，导致执行两次
      this.$emit('my-change', value, rowData)
    },
    setSelected() {
      this.selectedValue = this.modelValue
    },
    loadData() {
      if (this.entityModelCode) {
        this.dictionaryItemList = []
        this.$api.system.queryPlan.list({ entityModelCode: this.entityModelCode }).then((res) => {
          this.dictionaryItemList = res.data
          if (this.dictionaryItemList.length == 1) {
            this.selectedValue = this.dictionaryItemList[0].id
            this.$emit('my-change', this.dictionaryItemList[0].id, this.dictionaryItemList[0])
          } else {
            this.selectedValue = ''
            this.$emit('my-change', '', null)
          }
        })
      }
    }
  }
}
</script>
