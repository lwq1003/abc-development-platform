<template>
  <div class="w-full">
    <el-input v-model="displayName" disabled>
      <template #append>
        <el-button-group>
          <el-button icon="grid" @click="openModal" />
          <el-button icon="delete" @click="clear" />
        </el-button-group>
      </template>
    </el-input>
    <component
      :is="componentCode"
      ref="selectComponent"
      @select-change="selectChange"
      :selected-value="modelValue"
      @get-name="getName"
    />
  </div>
</template>

<script>
import DataDictionarySelect from '@/components/abc/CommonSelect/DataDictionary/DataDictionaryModal.vue'
import ModuleReference from '@/modules/entityconfig/view/module/reference.vue' // import EntityReference from '@/modules/entityconfig/view/entity/reference.vue'
// import EntityReference from '@/modules/entityconfig/view/entity/reference.vue'
export default {
  name: 'EntityReference',
  components: {
    DataDictionarySelect,
    ModuleReference
    // EntityReference
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
    componentCode: {
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
      displayName: ''
    }
  },
  methods: {
    openModal() {
      this.$refs.selectComponent.init()
    },
    selectChange(value, name) {
      this.displayName = name
      // 更新父组件绑定值
      this.$emit('update:modelValue', value)
      this.$emit('my-change', value)
    },
    clear() {
      this.displayName = ''
      this.$emit('update:modelValue', '')
      this.$emit('my-change', '')
    },
    getName(name) {
      this.displayName = name
    }
  }
}
</script>

<style></style>
