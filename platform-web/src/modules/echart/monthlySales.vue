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
            ['estimate', 'actual', 'month'],
            [100, 120, '一月'],
            [120, 82, '二月'],
            [161, 91, '三月'],
            [134, 154, '四月'],
            [105, 162, '五月'],
            [160, 140, '六月'],
            [165, 145, '七月'],
            [114, 250, '八月'],
            [163, 134, '九月'],
            [185, 56, '十月'],
            [118, 99, '十一月'],
            [123, 123, '十二月']
          ]
        },
        xAxis: {
          type: 'category'
        },
        yAxis: {
          axisTick: {
            show: false
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [5, 10]
        },
        legend: {
          data: ['预计', '实际'],
          top: 20
        },
        series: [
          {
            type: 'line',
            name: '预计',
            smooth: true,
            encode: {
              x: 'month',
              y: 'estimate'
            },
            animationDuration: 2800,
            animationEasing: 'cubicInOut'
          },
          {
            type: 'line',
            name: '实际',
            smooth: true,
            encode: {
              x: 'month',
              y: 'actual'
            },
            animationDuration: 2800,
            animationEasing: 'quadraticOut'
          }
        ]
      }
    }
  },
  methods: {}
}
</script>

<style></style>
