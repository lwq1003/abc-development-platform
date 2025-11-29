<template>
  <div class="custom-editor-container">
    <!-- 编辑器主体 -->
    <vue-markdown-editor
      ref="editorRef"
      class="custom-md-editor"
      v-model="editorContent"
      mode="editable"
      :height="height"
      :placeholder="placeholder"
      :disabled="disabled"
      :left-toolbar="leftToolbar"
      :disabled-menus="[]"
      @upload-image="handleUploadImage"
      @change="handleContentChange"
      @save="handleSave"
    >
      <template #before v-if="$slots.before">
        <slot name="before"></slot>
      </template>
      <template #after v-if="$slots.after">
        <slot name="after"></slot>
      </template>
    </vue-markdown-editor>

    <!-- 错误提示 -->
    <div class="editor-error" v-if="errorMessage">{{ errorMessage }}</div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onUnmounted } from 'vue'
import VueMarkdownEditor from '@kangc/v-md-editor'
import '@kangc/v-md-editor/lib/style/base-editor.css'
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js'
import '@kangc/v-md-editor/lib/theme/style/vuepress.css'
import Prism from 'prismjs'
import api from '@/api/index'

// 初始化编辑器主题
VueMarkdownEditor.use(vuepressTheme, {
  Prism
})

// 定义props
const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  height: {
    type: String,
    default: '500px'
  },
  placeholder: {
    type: String,
    default: '请输入内容...'
  },
  disabled: {
    type: Boolean,
    default: false
  },
  leftToolbar: {
    type: String,
    default:
      'undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code'
  },
  errorMessage: {
    type: String,
    default: ''
  }
})

// 定义事件
const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void
  (e: 'change', value: string): void
  (e: 'save', value: string): void
  (e: 'modeChange', isPreview: boolean): void
}>()

// 编辑器实例
const editorRef = ref<InstanceType<typeof VueMarkdownEditor> | null>(null)
const editorContent = ref(props.modelValue)

const handleUploadImage = (event, insertImage, files) => {
  // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容
  const formData = new FormData()
  formData.append('image', files[0])
  api.support.attachment.uploadImage(formData).then((res) => {
    const id = res.data
    let url =
      window.location.origin +
      '/' +
      import.meta.env.VITE_API_BASEPATH +
      '/support/attachment/' +
      id +
      '/getImage'

    // 插入图片
    insertImage({
      url: url,
      desc: '',
      width: '100%',
      height: 'auto'
    })
  })
}

// 处理内容变更
const handleContentChange = (value: string) => {
  editorContent.value = value
  emit('update:modelValue', value)
  emit('change', value)
}

// 处理保存事件
const handleSave = () => {
  if (props.disabled) return
  emit('save', editorContent.value)
}

// 监听外部内容变化
watch(
  () => props.modelValue,
  (newVal) => {
    if (newVal !== editorContent.value) {
      editorContent.value = newVal
    }
  },
  { immediate: true }
)

// 监听禁用状态变化
watch(
  () => props.disabled,
  (val) => {
    if (editorRef.value) {
      editorRef.value.setDisabled(val)
    }
  }
)

// 暴露给父组件的方法
const exposeMethods = {
  getContent: (): string => {
    return editorRef.value?.getValue() || editorContent.value
  },
  setContent: (content: string): void => {
    if (editorRef.value) {
      editorRef.value.setValue(content)
    } else {
      editorContent.value = content
    }
    emit('update:modelValue', content)
  },
  clearContent: (): void => {
    exposeMethods.setContent('')
  },

  focus: (): void => {
    if (editorRef.value && !props.disabled) {
      editorRef.value.focus()
    }
  },
  blur: (): void => {
    if (editorRef.value) {
      editorRef.value.blur()
    }
  }
}

defineExpose(exposeMethods)
</script>

<style scoped>
.custom-editor-container {
  width: 100%;
  box-sizing: border-box;
  padding: 8px;
}

.editor-toolbar-extra {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
  padding: 8px 0;
  gap: 16px;
}

.preview-switch {
  margin-left: auto;
}

.editor-error {
  color: #ff4d4f;
  font-size: 12px;
  margin-top: 8px;
  min-height: 16px;
  padding: 0 4px;
}

:deep(.custom-md-editor) {
  border-radius: 4px;
  transition: border-color 0.2s;
}

:deep(.custom-md-editor .editor-container) {
  border: 1px solid #e5e7eb;
  border-radius: 4px;
  transition: border-color 0.2s, background-color 0.2s;
}

:deep(.custom-md-editor:hover .editor-container:not(.is-disabled)) {
  border-color: #c0c4cc;
}

:deep(.custom-md-editor .editor-container.is-disabled) {
  background-color: #f5f7fa;
  border-color: #e5e7eb;
  cursor: not-allowed;
}

:deep(.custom-md-editor .toolbar-container) {
  border-bottom: 1px solid #e5e7eb;
  background-color: #f9fafb;
}

:deep(.custom-md-editor .toolbar-item) {
  color: #666;
  transition: color 0.2s, background-color 0.2s;
}

:deep(.custom-md-editor .toolbar-item:hover) {
  color: #1890ff;
  background-color: #f0f7ff;
}

:deep(.custom-md-editor .toolbar-item.is-disabled) {
  color: #ccc;
  cursor: not-allowed;
}

:deep(.custom-md-editor .editor-content) {
  font-family: 'SFMono-Regular', Consolas, 'Liberation Mono', Menlo, monospace;
  font-size: 14px;
  line-height: 1.6;
}

:deep(.custom-md-editor .preview-container) {
  background-color: #fff;
  line-height: 1.8;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .custom-editor-container {
    padding: 4px;
  }

  .editor-toolbar-extra {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .preview-switch {
    margin-left: 0;
  }
}
</style>
