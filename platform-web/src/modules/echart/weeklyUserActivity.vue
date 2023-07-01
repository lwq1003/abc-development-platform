<template>
  <v-chart :option="option" theme="auto" :autoresize="true" style="width: 100%; height: 300px" />
</template>

<script>
export default {
  data() {
    return {
      option: {
        dataset: {
          source: [
            ['count', 'week'],
            [13253, '周一'],
            [34235, '周二'],
            [26321, '周三'],
            [12340, '周四'],
            [24643, '周五'],
            [1322, '周六'],
            [1324, '周日']
          ]
        },
        xAxis: { type: 'category' },
        yAxis: {},
        series: [
          {
            type: 'bar',
            encode: {
              x: 'week',
              y: 'count'
            }
          }
        ]
      }
    }
  },
  methods: {}
}
</script>

<style></style>
