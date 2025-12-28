<template>
  <el-select
    v-model="selectedValue"
    :size="size"
    clearable
    :multiple="multiple"
    :disabled="readonly"
    style="width: 100%; margin: 0 auto"
    @change="change"
  >
    <el-option
      v-for="item in dictionaryItemList"
      :key="item.code"
      :value="item.code"
      :label="item.label"
    />
  </el-select>
</template>

<script>
export default {
  name: 'DictionarySelect',
  label: '字典下拉',
  props: {
    modelValue: {
      type: [String, Array],
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
    multiple: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      dictionaryItemList: [],
      selectedValue: []
    }
  },
  watch: {
    code: {
      immediate: true,
      handler: 'loadData'
    },
    modelValue: {
      immediate: false,
      handler: 'setSelected'
    }
  },
  methods: {
    change(value) {
      if (!this.multiple) {
        let name = ''
        this.dictionaryItemList.forEach((item) => {
          if (item.code === value) {
            name = item.label
            return
          }
        })
        // 更新绑定值
        this.$emit('update:modelValue', value)
        // 注意，此处若命令为change，则可能会与底层实现冲突，导致执行两次
        this.$emit('my-change', value, name)
      } else {
        // 更新绑定值
        this.$emit('update:modelValue', value)
      }
    },
    setSelected() {
      if (this.modelValue != '') {
        if (this.multiple) {
          if (Array.isArray(this.modelValue)) {
            this.selectedValue = this.modelValue
          } else {
            this.selectedValue.push(this.modelValue)
          }
        } else {
          this.selectedValue = this.modelValue
        }
      } else {
        if (this.multiple) {
          this.selectedValue = []
        } else {
          this.selectedValue = ''
        }
      }
    },
    loadData() {
      if (this.code) {
        this.dictionaryItemList = []
        this.$api.system.dictionaryType.getItem(this.code).then((res) => {
          this.dictionaryItemList = res.data
          this.setSelected()
          if (this.dictionaryItemList.length == 1) {
            this.$emit('update:modelValue', this.dictionaryItemList[0].code)
          }
        })
      }
    }
  }
}
</script>
