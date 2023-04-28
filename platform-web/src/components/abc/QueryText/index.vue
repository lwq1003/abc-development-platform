<template>
  <el-input
    v-model="displayText"
    :placeholder="placeholder"
    :disabled="readonly"
    @input="handleInput"
  />
</template>

<script>
export default {
  name: 'QueryText',
  props: {
    modelValue: {
      type: String,
      required: false,
      default: ''
    },
    readonly: {
      type: Boolean,
      required: false,
      default: false
    },
    type: {
      type: String,
      required: false,
      default: 'LK'
    },
    placeholder: {
      type: String,
      required: false,
      default: ''
    }
  },
  data() {
    return {
      // 显示内容
      displayText: ''
    }
  },
  watch: {
    modelValue: {
      immediate: true,
      handler: 'handleValue'
    }
  },
  methods: {
    // 该方法实现的意义更多的是在于处理预设值，例如在父组件中预设查询条件
    handleValue() {
      if (!this.modelValue) {
        this.displayText = ''
      } else {
        // 根据约定的规则处理，获取显示内容
        this.displayText = this.modelValue.substring(this.modelValue.indexOf(')') + 1)
      }
    },
    // 在文本框中改变值时，先触发该函数，根据规则附加查询字符，并通过emit方法将值传给父组件
    // 更新父组件中绑定的data中的数据，同时父组件的值改变，又会通过props机制传递给子组件的value
    // 子组件通过watch值的变化，再把查询的特殊字符去除掉，从而显示正常
    // 这种方式，一方面显示给用户的是正常文本，另一方面，父组件的查询模型绑定的值又是后台需要的特殊字符
    handleInput(value) {
      if (value && value.length > 0) {
        value = '(' + this.type + ')' + value
      } else {
        // 若为空，则直接清空，否则传给后台可能会出现只有查询字符没有内容的情况，如*，>=等
        value = ''
      }
      // 将处理过，待查询特殊字符的值传给父组件绑定的数据
      this.$emit('update:modelValue', value)
    }
  }
}
</script>

<style></style>
