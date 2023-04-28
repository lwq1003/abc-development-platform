<template>
  <el-pagination
    :current-page="pageNum"
    :page-sizes="$constant.DEFAULT_PAGE_SIZE_ARR"
    :page-size="pageSize"
    layout="total, sizes, prev, pager, next, jumper"
    :total="pageTotal"
    style="margin-top: 10px"
    @size-change="pageSizeChange"
    @current-change="pageChange"
  />
</template>

<script>
export default {
  name: 'ListPager',
  inject: ['pageChange', 'pageSizeChange'],
  props: {
    pageNum: {
      type: Number,
      required: false,
      default: 1
    },
    pageSize: {
      type: Number,
      required: false,
      default: 10
    },
    pageTotal: {
      type: Number,
      required: false,
      default: 0
    }
  },
  data() {
    return {}
  },

  methods: {}
}
</script>

<style></style>
