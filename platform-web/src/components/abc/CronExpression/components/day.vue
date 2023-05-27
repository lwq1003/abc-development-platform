<template>
  <div :val="value_" class="day">
    <div>
      <el-radio v-model="type" label="1" size="small" border>每日</el-radio>
    </div>
    <div>
      <el-radio v-model="type" label="5" size="small" border>不指定</el-radio>
    </div>
    <div>
      <el-radio v-model="type" label="2" size="small" border>周期</el-radio>
      <span style="margin-left: 10px; margin-right: 5px">从</span>
      <el-input-number
        v-model="cycle.start"
        :min="1"
        :max="31"
        size="small"
        style="width: 100px"
        @change="type = '2'"
      />
      <span style="margin-left: 5px; margin-right: 5px">至</span>
      <el-input-number
        v-model="cycle.end"
        :min="2"
        :max="31"
        size="small"
        style="width: 100px"
        @change="type = '2'"
      />
      日
    </div>
    <div>
      <el-radio v-model="type" label="3" size="small" border>循环</el-radio>
      <span style="margin-left: 10px; margin-right: 5px">从</span>
      <el-input-number
        v-model="loop.start"
        :min="1"
        :max="31"
        size="small"
        style="width: 100px"
        @change="type = '3'"
      />
      <span style="margin-left: 5px; margin-right: 5px">日开始，每</span>
      <el-input-number
        v-model="loop.end"
        :min="1"
        :max="31"
        size="small"
        style="width: 100px"
        @change="type = '3'"
      />
      日执行一次
    </div>
    <div>
      <el-radio v-model="type" label="8" size="small" border>工作日</el-radio>
      <span style="margin-left: 10px; margin-right: 5px">本月</span>
      <el-input-number
        v-model="work"
        :min="1"
        :max="31"
        size="small"
        style="width: 100px"
        @change="type = '8'"
      />
      号，最近的工作日
    </div>
    <div>
      <el-radio v-model="type" label="6" size="small" border>本月最后一天</el-radio>
    </div>
    <div class="appoint">
      <el-radio v-model="type" label="4" size="small" border>指定</el-radio>
      <el-checkbox-group v-model="appoint" @change="type = '4'">
        <div v-for="(days, i) in dayList" :key="i" style="margin-left: 10px; line-height: 25px">
          <el-checkbox v-for="(day, j) in days" :key="j" :label="day" />
        </div>
      </el-checkbox-group>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    modelValue: {
      type: String,
      default: '?'
    }
  },
  data() {
    return {
      dayList: [
        ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'],
        ['11', '12', '13', '14', '15', '16', '17', '18', '19', '20'],
        ['21', '22', '23', '24', '25', '26', '27', '28', '29', '30'],
        ['31']
      ],
      type: '5', // 类型
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
        case '1': // 每秒
          result.push('*')
          break
        case '2': // 周期
          result.push(`${this.cycle.start}-${this.cycle.end}`)
          break
        case '3': // 循环
          result.push(`${this.loop.start}/${this.loop.end}`)
          break
        case '4': // 指定
          result.push(this.appoint.join(','))
          break
        case '6': // 最后
          result.push(`${this.last === 0 ? '' : this.last}L`)
          break
        case '7': // 指定周
          result.push(`${this.week.start}#${this.week.end}`)
          break
        case '8': // 工作日
          result.push(`${this.work}W`)
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
          this.cycle.start = parseInt(this.modelValue.split('-')[0])
          this.cycle.end = parseInt(this.modelValue.split('-')[1])
        }
      } else if (this.modelValue.indexOf('/') !== -1) {
        // 3循环
        if (this.modelValue.split('/').length === 2) {
          this.type = '3'
          this.loop.start = parseInt(this.modelValue.split('/')[0])
          this.loop.end = parseInt(this.modelValue.split('/')[1])
        }
      } else if (this.modelValue.indexOf('*') !== -1) {
        // 1每
        this.type = '1'
      } else if (this.modelValue.indexOf('L') !== -1) {
        // 6最后
        this.type = '6'
        this.last = this.modelValue.replace('L', '')
      } else if (this.modelValue.indexOf('#') !== -1) {
        // 7指定周
        if (this.modelValue.split('#').length === 2) {
          this.type = '7'
          this.week.start = this.modelValue.split('#')[0]
          this.week.end = this.modelValue.split('#')[1]
        }
      } else if (this.modelValue.indexOf('W') !== -1) {
        // 8工作日
        this.type = '8'
        this.work = this.modelValue.replace('W', '')
      } else {
        // *
        this.type = '4'
        this.appoint = this.modelValue.split(',')
      }
    }
  }
}
</script>

<style scoped>
.day .el-radio {
  margin-right: 0px;
}
.day .el-checkbox {
  margin-left: 10px;
  margin-right: 0px;
}

.day .appoint .el-checkbox__label {
  width: 20px;
  padding-left: 5px;
}
.day div {
  line-height: 40px;
}
</style>
