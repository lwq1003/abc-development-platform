<template>
  <el-radio-group :value="value" :size="size" :disabled="readonly" @change="change">
    <el-radio v-for="item in dictionaryItemList" :key="item.code" :label="item.code">{{
      item.label
    }}</el-radio>
  </el-radio-group>
</template>

<script>
export default {
  props: {
    value: {
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
    }
  },
  data() {
    return {
      dictionaryItemList: []
    }
  },
  watch: {
    code: {
      immediate: true,
      handler: 'loadData'
    }
  },

  methods: {
    change(value) {
      this.$emit('change', value)
    },
    loadData() {
      if (this.code) {
        this.dictionaryItemList = []
        this.$api.system.dictionaryType.getItem(this.code).then((res) => {
          this.dictionaryItemList = res.data
        })
      }
    }
  }
}
</script>
