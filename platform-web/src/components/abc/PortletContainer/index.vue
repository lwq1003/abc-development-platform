<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span v-if="!titleEdit"> {{ configData.name }}</span>
        <span v-if="titleEdit"><el-input v-model="configData.name" /> </span>
        <span v-if="toolbarFlag">
          <el-icon style="cursor: pointer; margin-right: 12px" v-if="!titleEdit">
            <EditPen @click="titleEdit = true" />
          </el-icon>
          <el-icon style="cursor: pointer; margin-right: 12px" v-if="titleEdit">
            <Select @click="saveTitle" />
          </el-icon>
          <el-icon style="cursor: pointer; margin-right: 12px">
            <Setting @click="configVisible = true" />
          </el-icon>
          <el-icon style="cursor: pointer">
            <Delete @click="remove" />
          </el-icon>
        </span>
      </div>
    </template>
    <component
      :is="configData.code"
      v-model:configVisible="configVisible"
      v-model:config="configData.paramList"
    />
  </el-card>
</template>
<script lang="ts">
import Favorite from '@/modules/support/view/portletList/favorite.vue'
import Notice from '@/modules/support/view/portletList/notice.vue'
export default {
  components: {
    Favorite,
    Notice
  },
  props: {
    config: {
      type: Object,
      required: false,
      default: () => {}
    },
    // 标识
    id: {
      type: String,
      default: '',
      required: false
    },
    // 标识
    toolbarFlag: {
      type: Boolean,
      default: false,
      required: false
    }
  },
  data() {
    return {
      configData: { name: '', code: '', paramList: [] },
      configVisible: false,
      //标题编辑状态
      titleEdit: false
    }
  },
  watch: {
    config: {
      handler() {
        this.configData = Object.assign({}, this.configData, this.config)
      },
      immediate: true
    }
  },
  methods: {
    saveTitle() {
      this.$emit('update:config', this.configData)
      this.titleEdit = false
    },
    remove() {
      this.$emit('remove', this.id)
    }
  }
}
</script>
<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 100%;
  height: 100%;
}
</style>
