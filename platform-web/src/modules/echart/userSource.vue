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
            ['数量', '来源'],
            [335, '直接访问'],
            [310, '邮件营销'],
            [234, '联盟广告'],
            [135, '视频广告'],
            [1548, '搜索引擎']
          ]
        },
        title: {
          show: false
        },
        tooltip: {
          trigger: 'item',
          formatter: function (params) {
            // 只返回第一列数据
            return params.data[0] + '(' + params.percent + '%)'
          }
        },
        legend: {
          orient: 'horizontal',
          top: 'bottom',
          left: 'center'
        },
        series: [
          {
            type: 'pie',
            radius: '80%',
            center: ['50%', '50%'],
            encode: {
              tooltip: [0, 1],
              value: 0,
              itemName: 1
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
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
