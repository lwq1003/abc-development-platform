<template>
  <el-button :icon="modelValue" @click="show">{{ modelValue }} </el-button>
  <el-icon @click="clear" size="large" style="margin-left: 10px">
    <Delete />
  </el-icon>
  <Dialog title="图标选择" v-model="visible" width="300px">
    <el-input v-model="searchValue" placeholder="输入图标名搜索" style="margin-bottom: 10px" />
    <div class="el-icon-picker">
      <component
        v-for="icon in iconList"
        :key="icon"
        :class="[icon, 'icon', { 'icon-active': icon == modelValue }]"
        :is="icon"
        @click="confirm(icon)"
      />
    </div>
  </Dialog>
</template>

<script>
import { Dialog } from '@/components/abc/Dialog'
export default {
  components: {
    Dialog
  },
  props: {
    modelValue: {
      type: String,
      default: '',
      required: false
    }
  },
  emits: ['update:modelValue'],
  data() {
    return {
      iconList: this.$icons,
      // 搜索值
      searchValue: '',
      // 可见性
      visible: false
    }
  },
  watch: {
    searchValue(value) {
      if (value !== '') {
        value = value.toLowerCase()
        this.iconList = this.$icons.filter((icon) => {
          return icon.toLowerCase().includes(value)
        })
      } else {
        this.iconList = this.$icons
      }
    }
  },
  methods: {
    show() {
      this.visible = true
    },
    confirm(icon) {
      this.$emit('update:modelValue', icon)
      this.visible = false
    },
    clear() {
      this.$emit('update:modelValue', '')
    }
  }
}
</script>

<style scoped>
.el-icon-picker {
  display: flex;
  height: 256px;
  overflow-y: scroll;
  flex-wrap: wrap;
}

.icon {
  display: inline-block;
  width: 24px;
  height: 24px;
  margin: 5px;
  font-size: 20px;
  line-height: 45px;
  color: var(--el-text-color-regular);
  text-align: center;
  cursor: pointer;
  border-radius: 4px;
}

.icon:hover {
  color: var(--el-color-primary);
}

.icon-active {
  color: var(--el-color-primary);
}
</style>
