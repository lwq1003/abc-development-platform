<template>
  <v-chart :option="option" theme="auto" :autoresize="true" style="width: 100%; height: 350px" />
</template>

<script>
export default {
  data() {
    return {
      option: {
        series: [
          {
            type: 'gauge',
            radius: '75%',
            center: ['50%', '25%'],
            axisLine: {
              lineStyle: {
                width: 15,
                color: [
                  [0.3, '#67e0e3'],
                  [0.7, '#37a2da'],
                  [1, '#fd666d']
                ]
              }
            },
            pointer: {
              itemStyle: {
                color: 'inherit'
              }
            },
            axisTick: {
              distance: -15,
              length: 4,
              lineStyle: {
                color: '#fff',
                width: 1
              }
            },
            splitLine: {
              distance: -15,
              length: 15,
              lineStyle: {
                color: '#fff',
                width: 2
              }
            },
            axisLabel: {
              color: 'inherit',
              distance: 20,
              fontSize: 10
            },
            detail: {
              valueAnimation: true,
              formatter: '{value} km/h',
              color: 'inherit',
              fontSize: 12
            },
            data: [
              {
                value: 70
              }
            ]
          }
        ]
      }
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      setInterval(this.generateSpeed, 2000)
    },
    generateSpeed() {
      this.option = Object.assign(this.option, {
        series: [
          {
            data: [
              {
                value: +(Math.random() * 100).toFixed(2)
              }
            ]
          }
        ]
      })
    }
  }
}
</script>

<style></style>
