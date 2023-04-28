<template>
  <div class="w-full">
    <el-input v-model="dataDictionaryName" disabled>
      <template #append>
        <el-button-group>
          <el-button icon="grid" @click="openModal" />
          <el-button icon="delete" @click="clear" />
        </el-button-group>
      </template>
    </el-input>

    <dataDictionary-modal ref="dataDictionaryModel" @ok="handleOK" />
  </div>
</template>

<script>
import DataDictionaryModal from './DataDictionaryModal.vue'
export default {
  name: 'DataDictionarySelect',
  components: {
    DataDictionaryModal
  },
  props: {
    width: {
      type: Number,
      default: 500,
      required: false
    },
    modelValue: {
      type: String,
      default: '',
      required: false
    },
    name: {
      type: String,
      default: '',
      required: false
    },
    disabled: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  emits: ['update:modelValue'],
  data() {
    return {
      visible: false,
      dataDictionaryName: ''
    }
  },
  watch: {
    modelValue: {
      immediate: true,
      handler: 'handleValue'
    }
  },
  methods: {
    openModal() {
      this.$refs.dataDictionaryModel.init()
    },
    handleValue() {
      if (this.modelValue) {
        this.$api.system.dictionaryType.getByCode(this.modelValue).then((res) => {
          this.dataDictionaryName = res.data.name
        })
      } else {
        this.dataDictionaryName = ''
      }
    },
    handleOK(value) {
      // 更新父组件绑定值
      this.$emit('update:modelValue', value)
      this.$emit('change', value)
    },
    clear() {
      this.dataDictionaryName = ''
      this.$emit('update:modelValue', '')
      this.$emit('change', '')
    }
  }
}
</script>

<style scoped></style>
