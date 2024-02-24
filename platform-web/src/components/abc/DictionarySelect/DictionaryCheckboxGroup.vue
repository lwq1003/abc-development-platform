<template>
  <div>
    <el-checkbox
      v-if="showCheckAll"
      v-model="checkAll"
      :indeterminate="isIndeterminate"
      @change="handleCheckAllChange"
      class="mb-2"
      >全选</el-checkbox
    >
    <el-checkbox-group
      v-model="selectValue"
      :size="size"
      :disabled="readonly"
      @input="handleInput"
      @change="checkedChange"
    >
      <el-checkbox
        v-for="item in dictionaryItemList"
        :key="item.code"
        :class="direction"
        :label="item.code"
        >{{ item.label }}</el-checkbox
      >
    </el-checkbox-group>
  </div>
</template>

<script>
export default {
  props: {
    modelValue: {
      type: [Array],
      required: false,
      default: () => []
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
    // 方向 水平horizontal  垂直 vertical
    direction: {
      type: String,
      default: 'horizontal',
      required: false
    },
    // 是否显示全选
    showCheckAll: {
      type: Boolean,
      default: true,
      required: false
    }
  },
  data() {
    return {
      selectValue: [],
      dictionaryItemList: [],
      checkAll: false,
      isIndeterminate: false
    }
  },
  watch: {
    modelValue: {
      immediate: true,
      handler: 'handleValue'
    }
  },
  mounted() {
    this.dictionaryItemList = []
    this.$api.system.dictionaryType.getItem(this.code).then((res) => {
      this.dictionaryItemList = res.data
    })
  },
  methods: {
    handleInput: function (selectedValue) {
      this.$emit('change', selectedValue)
    },
    handleValue: function () {
      this.selectValue = this.modelValue
      this.setCheckAllStatus(this.selectValue.length)
    },
    handleCheckAllChange(val) {
      if (val) {
        this.selectValue = this.dictionaryItemList.map((x) => x.code)
      } else {
        this.selectValue = []
      }
      // 更新绑定值
      this.$emit('update:modelValue', this.selectValue)
      this.$emit('change', this.selectValue)
      this.isIndeterminate = false
    },
    checkedChange(value) {
      console.log('变更', value)
      // 更新绑定值
      this.$emit('update:modelValue', value)
      this.setCheckAllStatus(value.length)
    },
    setCheckAllStatus(checkedCount) {
      // 注意，该函数执行时，数据字典项可能还在执行过程中，即this.dictionaryItemList可能为空
      if (checkedCount === 0) {
        // 选中值数量如为零，则全选复选框两个值都为false
        this.checkAll = false
        this.isIndeterminate = false
      } else if (checkedCount === this.dictionaryItemList.length) {
        // 选中值数量大为零，并且与列表项总量相等，则全选为true，半选状态为false
        this.checkAll = true
        this.isIndeterminate = false
      } else {
        // 选中值数量大为零，并且小于列表项总量相等，则半选状态为true
        this.isIndeterminate = true
      }
    }
  }
}
</script>
<style scoped>
.horizontal {
  display: inline;
}
.vertical {
  display: block;
}
</style>
