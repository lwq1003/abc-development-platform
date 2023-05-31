<script setup lang="ts">
import { onBeforeUnmount, computed, PropType, unref, nextTick, ref, watch, shallowRef } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import { IDomEditor, IEditorConfig, i18nChangeLanguage } from '@wangeditor/editor'
import { propTypes } from '@/utils/propTypes'
import { isNumber } from '@/utils/is'
import { ElMessage } from 'element-plus'
import { useLocaleStore } from '@/store/modules/locale'
import { getToken } from '@/utils/auth'
import api from '@/api/index'
const localeStore = useLocaleStore()

const currentLocale = computed(() => localeStore.getCurrentLocale)

i18nChangeLanguage(unref(currentLocale).lang)

const props = defineProps({
  editorId: propTypes.string.def('wangeEditor-1'),
  height: propTypes.oneOfType([Number, String]).def('500px'),
  editorConfig: {
    type: Object as PropType<IEditorConfig>,
    default: () => undefined
  },
  modelValue: propTypes.string.def(''),
  readonly: propTypes.bool.def(false)
})

const emit = defineEmits(['change', 'update:modelValue'])

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef<IDomEditor>()

const valueHtml = ref('')

watch(
  () => props.modelValue,
  (val: string) => {
    if (val === unref(valueHtml)) return
    valueHtml.value = val
  },
  {
    immediate: true
  }
)

// 监听
watch(
  () => valueHtml.value,
  (val: string) => {
    emit('update:modelValue', val)
  }
)

const handleCreated = (editor: IDomEditor) => {
  editorRef.value = editor
}

const token = getToken()

// 编辑器配置
const editorConfig = computed((): IEditorConfig => {
  // 初始化 MENU_CONF 属性
  const customConfig: Partial<IEditorConfig> = {
    // TS 语法
    // const editorConfig = {                       // JS 语法
    MENU_CONF: {}

    // 其他属性...
  }

  // 修改 uploadImage 菜单配置
  customConfig.MENU_CONF['uploadImage'] = {
    // 单个文件的最大体积限制，默认为 2M
    maxFileSize: 5 * 1024 * 1024,
    // 低于指定体积以base64编码方式插入
    base64LimitSize: 5 * 1024 * 1024,
    // 最多可上传几个文件，默认为 100
    maxNumberOfFiles: 10,

    // 选择文件时的类型限制，默认为 ['image/*'] 。如不想限制，则设置为 []
    allowedFileTypes: ['image/*']

    // TODO 以下方式可实现插入图片，但受权限管控因素影响，会暴露插入图片的操作人的token
    // 根源上解决该问题，只能为图片设置一个不控权限的存储与访问路径
    // // 自定义上传
    // async customUpload(file: File, insertFn: InsertFnType) {
    //   const formData = new FormData()
    //   formData.append('image', file)
    //   api.support.attachment.uploadImage(formData).then((res) => {
    //     const id = res.data
    //     let url = '/support/attachment/' + id + '/download?X-Token=' + token
    //     insertFn(url, '', '')
    //   })
    // }
  }

  return Object.assign(
    {
      readOnly: props.readonly,
      customAlert: (s: string, t: string) => {
        switch (t) {
          case 'success':
            ElMessage.success(s)
            break
          case 'info':
            ElMessage.info(s)
            break
          case 'warning':
            ElMessage.warning(s)
            break
          case 'error':
            ElMessage.error(s)
            break
          default:
            ElMessage.info(s)
            break
        }
      },
      autoFocus: false,
      scroll: true,
      uploadImgShowBase64: true
    },
    props.editorConfig || {},
    customConfig
  )
})

const editorStyle = computed(() => {
  return {
    height: isNumber(props.height) ? `${props.height}px` : props.height
  }
})

// 回调函数
const handleChange = (editor: IDomEditor) => {
  emit('change', editor)
}

// 组件销毁时，及时销毁编辑器
onBeforeUnmount(() => {
  const editor = unref(editorRef.value)
  if (editor === null) return

  // 销毁，并移除 editor
  editor?.destroy()
})

const getEditorRef = async (): Promise<IDomEditor> => {
  await nextTick()
  return unref(editorRef.value) as IDomEditor
}

defineExpose({
  getEditorRef
})
</script>

<template>
  <div class="border-1 border-solid border-[var(--tags-view-border-color)] z-3000">
    <!-- 工具栏 -->
    <Toolbar
      :editor="editorRef"
      :editorId="editorId"
      class="border-bottom-1 border-solid border-[var(--tags-view-border-color)]"
    />
    <!-- 编辑器 -->
    <Editor
      v-model="valueHtml"
      :editorId="editorId"
      :defaultConfig="editorConfig"
      :style="editorStyle"
      @on-change="handleChange"
      @on-created="handleCreated"
    />
  </div>
</template>

<style src="@wangeditor/editor/dist/css/style.css"></style>
