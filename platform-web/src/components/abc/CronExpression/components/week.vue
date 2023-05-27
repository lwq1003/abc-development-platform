<template>
  <div :val="value_" class="week">
    <div>
      <el-radio v-model="type" label="1" size="small" border>每周</el-radio>
    </div>
    <div>
      <el-radio v-model="type" label="5" size="small" border>不指定</el-radio>
    </div>
    <div>
      <el-radio v-model="type" label="2" size="small" border>周期</el-radio>
      <span style="margin-left: 10px; margin-right: 5px">从星期</span>
      <el-input-number
        v-model="cycle.start"
        :min="1"
        :max="7"
        size="small"
        style="width: 100px"
        @change="type = '2'"
      />
      <span style="margin-left: 5px; margin-right: 5px">至星期</span>
      <el-input-number
        v-model="cycle.end"
        :min="2"
        :max="7"
        size="small"
        style="width: 100px"
        @change="type = '2'"
      />
    </div>
    <div>
      <el-radio v-model="type" label="3" size="small" border>循环</el-radio>
      <span style="margin-left: 10px; margin-right: 5px">从星期</span>
      <el-input-number
        v-model="loop.start"
        :min="1"
        :max="7"
        size="small"
        style="width: 100px"
        @change="type = '3'"
      />
      <span style="margin-left: 5px; margin-right: 5px">开始，每</span>
      <el-input-number
        v-model="loop.end"
        :min="1"
        :max="7"
        size="small"
        style="width: 100px"
        @change="type = '3'"
      />
      天执行一次
    </div>
    <div>
      <el-radio v-model="type" label="7" size="small" border>指定周</el-radio>
      <span style="margin-left: 10px; margin-right: 5px">本月第</span>
      <el-input-number
        v-model="week.start"
        :min="1"
        :max="4"
        size="small"
        style="width: 100px"
        @change="type = '7'"
      />
      <span style="margin-left: 5px; margin-right: 5px">周，星期</span>
      <el-input-number
        v-model="week.end"
        :min="1"
        :max="7"
        size="small"
        style="width: 100px"
        @change="type = '7'"
      />
    </div>
    <div>
      <el-radio v-model="type" label="6" size="small" border>本月最后一个</el-radio>
      <span style="margin-left: 10px; margin-right: 5px">星期</span>
      <el-input-number
        v-model="last"
        :min="1"
        :max="7"
        size="small"
        style="width: 100px"
        @change="type = '6'"
      />
    </div>
    <div>
      <el-radio v-model="type" label="4" size="small" border>指定</el-radio>
      <el-checkbox-group
        v-model="appoint"
        style="margin-left: 10px; line-height: 25px"
        @change="type = '4'"
      >
        <el-checkbox v-for="i in ['1', '2', '3', '4', '5', '6', '7']" :key="i" :label="i" />
      </el-checkbox-group>
    </div>
  </div>
</template>

<script>
const weeks = [0, 2, 3, 4, 5, 6, 7, 1]
export default {
  props: {
    modelValue: {
      type: String,
      default: '*'
    }
  },
  data() {
    return {
      type: '1', // 类型
      cycle: {
        // 周期
        start: 0,
        end: 0
      },
      loop: {
        // 循环
        start: 0,
        end: 0
      },
      week: {
        // 指定周
        start: 0,
        end: 0
      },
      work: 0,
      last: 0,
      appoint: [] // 指定
    }
  },
  computed: {
    value_() {
      const result = []
      switch (this.type) {
        case '1': // 每
          result.push('*')
          break
        case '2': // 周期
          result.push(`${weeks[this.cycle.start]}-${weeks[this.cycle.end]}`)
          break
        case '3': // 循环
          result.push(`${weeks[this.loop.start]}/${weeks[this.loop.end]}`)
          break
        case '4': // 指定
          result.push(this.appoint.map((i) => weeks[parseInt(i)]).join(','))
          break
        case '6': // 最后
          result.push(`${this.last === 0 ? '' : weeks[this.last]}L`)
          break
        case '7': // 指定周
          result.push(`${weeks[this.week.end]}#${this.week.start}`)
          break
        default: // 不指定
          result.push('?')
          break
      }
      this.$emit('update:modelValue', result.join(''))
      return result.join('')
    }
  },
  watch: {
    modelValue() {
      this.updateVal()
    }
  },
  created() {
    this.updateVal()
  },
  methods: {
    updateVal() {
      if (!this.modelValue) {
        return
      }
      if (this.modelValue === '?') {
        this.type = '5'
      } else if (this.modelValue.indexOf('-') !== -1) {
        // 2周期
        if (this.modelValue.split('-').length === 2) {
          this.type = '2'
          this.cycle.start = this.modSeven(parseInt(this.modelValue.split('-')[0]))
          this.cycle.end = this.modSeven(parseInt(this.modelValue.split('-')[1]))
        }
      } else if (this.modelValue.indexOf('/') !== -1) {
        // 3循环
        if (this.modelValue.split('/').length === 2) {
          this.type = '3'
          this.loop.start = this.modSeven(parseInt(this.modelValue.split('/')[0]))
          this.loop.end = this.modSeven(parseInt(this.modelValue.split('/')[1]))
        }
      } else if (this.modelValue.indexOf('*') !== -1) {
        // 1每
        this.type = '1'
      } else if (this.modelValue.indexOf('L') !== -1) {
        // 6最后
        this.type = '6'
        this.last = this.modSeven(parseInt(this.modelValue.replace('L', '')))
      } else if (this.modelValue.indexOf('#') !== -1) {
        // 7指定周
        if (this.modelValue.split('#').length === 2) {
          this.type = '7'
          this.week.start = parseInt(this.modelValue.split('#')[1])
          this.week.end = this.modSeven(parseInt(this.modelValue.split('#')[0]))
        }
      } else if (this.modelValue.indexOf('W') !== -1) {
        // 8工作日
        this.type = '8'
        this.work = this.modSeven(parseInt(this.modelValue.replace('W', '')))
      } else {
        // *
        this.type = '4'
        this.appoint = this.modelValue.split(',').map((i) => this.modSeven(parseInt(i)) + '')
      }
    },
    modSeven(val) {
      const result = (val + 6) % 7
      return result === 0 ? 7 : result
    }
  }
}
</script>

<style scoped>
.week .el-radio {
  margin-right: 0px;
}
.week .el-checkbox {
  margin-left: 10px;
  margin-right: 0px;
}

.week div {
  line-height: 40px;
}
</style>
