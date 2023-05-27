<template>
  <el-popover
    :visible="popoverVisible"
    placement="bottom-start"
    popper-class="pupop-select-cron"
    trigger="manual"
    :disabled="disabledSelected"
    @show="updateVal"
    width="700px"
  >
    <div>
      <span style="color: #e6a23c; font-size: 12px"
        >corn从左到右（用空格隔开）：秒 分 小时 月份中的日期 月份 星期中的日期 年份</span
      >
      <el-button style="float: right" size="small" @click="popoverVisible = false">取消</el-button>
      <el-button
        type="primary"
        size="small"
        style="float: right; margin-right: 10px"
        @click="setVal"
        >确定</el-button
      >
      <el-tabs v-model="activeName">
        <el-tab-pane label="秒" name="s">
          <second-and-minute v-model="cron.sVal" lable="秒" />
        </el-tab-pane>
        <el-tab-pane label="分" name="m">
          <second-and-minute v-model="cron.mVal" lable="分" />
        </el-tab-pane>
        <el-tab-pane label="时" name="h">
          <hour v-model="cron.hVal" lable="时" />
        </el-tab-pane>
        <el-tab-pane label="日" name="d">
          <day v-model="cron.dVal" lable="日" />
        </el-tab-pane>
        <el-tab-pane label="月" name="month">
          <month v-model="cron.monthVal" lable="月" />
        </el-tab-pane>
        <el-tab-pane label="周" name="week">
          <week v-model="cron.weekVal" lable="周" />
        </el-tab-pane>
        <el-tab-pane label="年" name="year">
          <year v-model="cron.yearVal" lable="年" />
        </el-tab-pane>
      </el-tabs>
      <!-- table -->
      <el-table :data="tableData" size="small" border style="width: 100%">
        <el-table-column prop="sVal" label="秒" />
        <el-table-column prop="mVal" label="分" />
        <el-table-column prop="hVal" label="时" />
        <el-table-column prop="dVal" label="日" />
        <el-table-column prop="monthVal" label="月" />
        <el-table-column prop="weekVal" label="周" />
        <el-table-column prop="yearVal" label="年" />
      </el-table>
    </div>
    <!-- 页面显示内容区 -->
    <template #reference>
      <div
        :class="{
          'cron-expression': 1,
          'is-opened': popoverVisible,
          'is-active': modelValue,
          'is-disabled': disabledSelected
        }"
      >
        <!-- 显示图标 -->
        <div class="cron-expression-input">
          <el-input v-model="tempVal" auto-complete="off" :size="size" :style="inputStyle" />
          <el-button
            v-if="!popoverVisible"
            icon="ArrowDownBold"
            title="打开图形配置"
            @click="popoverVisible = true"
          />
          <el-button
            v-else
            icon="ArrowUpBold"
            title="关闭图形配置"
            @click="popoverVisible = false"
          />
        </div>
      </div>
    </template>
  </el-popover>
</template>

<script>
import SecondAndMinute from './components/secondAndMinute.vue'
import hour from './components/hour.vue'
import day from './components/day.vue'
import month from './components/month.vue'
import week from './components/week.vue'
import year from './components/year.vue'
export default {
  components: {
    SecondAndMinute,
    hour,
    day,
    month,
    week,
    year
  },

  props: {
    modelValue: {
      type: String,
      default: '* * * ? * *',
      required: false
    },
    size: {
      type: String,
      default: ''
    },
    disabled: {
      type: Boolean,
      default: false
    },
    inputStyle: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      popoverVisible: false,
      activeName: 's',
      tempVal: this.modelValue ? this.modelValue.trim() : this.modelValue,
      cron: {
        sVal: '',
        mVal: '',
        hVal: '',
        dVal: '',
        monthVal: '',
        weekVal: '',
        yearVal: ''
      }
    }
  },
  computed: {
    disabledSelected() {
      if (this.disabled) return true
      return this.$parent.form ? this.$parent.form.disabled : false
    },
    tableData() {
      return [
        {
          sVal: this.cron.sVal,
          mVal: this.cron.mVal,
          hVal: this.cron.hVal,
          dVal: this.cron.dVal,
          monthVal: this.cron.monthVal,
          weekVal: this.cron.weekVal,
          yearVal: this.cron.yearVal
        }
      ]
    }
  },
  watch: {
    tempVal(newVal, oldVal) {
      if (newVal !== oldVal) {
        this.$emit('update:modelValue', newVal)
      }
    }
  },
  beforeUpdate() {
    this.tempVal = this.modelValue
  },
  methods: {
    setVal() {
      // if (!this.cron.dVal && !this.cron.weekVal) {
      //   return ''
      // }
      if (this.cron.dVal === '?' && this.cron.weekVal === '?') {
        this.$message.error('日期与星期不可以同时为“不指定”')
      }
      if (this.cron.dVal !== '?' && this.cron.weekVal !== '?') {
        this.$message.error('日期与星期必须有一个为“不指定”')
      }
      const v = `${this.cron.sVal} ${this.cron.mVal} ${this.cron.hVal} ${this.cron.dVal} ${this.cron.monthVal} ${this.cron.weekVal} ${this.cron.yearVal}`
      this.tempVal = v.trim()
      this.popoverVisible = false
    },
    updateVal() {
      if (!this.tempVal) {
        return
      }
      const arrays = this.tempVal.split(' ')
      this.cron.sVal = arrays[0]
      this.cron.mVal = arrays[1]
      this.cron.hVal = arrays[2]
      this.cron.dVal = arrays[3]
      this.cron.monthVal = arrays[4]
      this.cron.weekVal = arrays[5]
      this.cron.yearVal = arrays[6] ? arrays[6] : ''
    }
  }
}
</script>

<style>
.cron {
  padding: 20px;
  width: 700px;
}
</style>
