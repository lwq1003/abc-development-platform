<template>
  <el-button
    :v-permission="pageCode + functionCode"
    type="primary"
    icon="Search"
    @click="handleClick"
    >查询</el-button
  >
</template>

<script>
export default {
  name: 'QueryButton',
  inject: ['query'],
  props: {
    pageCode: {
      type: String,
      required: false,
      default: ''
    },
    clickEvent: {
      type: Function,
      required: false,
      default: null
    }
  },
  data() {
    return {
      functionCode: 'query'
    }
  },
  methods: {
    handleClick() {
      if (this.clickEvent) {
        this.clickEvent()
      } else {
        this.query()
      }
    }
  }
}
</script>

<style></style>
