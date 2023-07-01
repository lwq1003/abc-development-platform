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
            ['count', 'mysource'],
            [335, 'a'],
            [310, 'b'],
            [234, 'c'],
            [135, 'd'],
            [1548, 'e']
          ]
        },
        xAxis: { type: 'category' },
        yAxis: {},
        series: [
          {
            type: 'bar',
            encode: {
              x: 'mysource',
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
