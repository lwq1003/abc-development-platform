// 引入windi css
import '@/plugins/windi.css'

// 导入全局的svg图标
import '@/plugins/svgIcon'

// 初始化多语言
import { setupI18n } from '@/plugins/vueI18n'

// 引入状态管理
import { setupStore } from '@/store'

// 全局组件
import { setupGlobCom } from '@/components'

// 引入全局样式
import '@/styles/index.less'

// 引入动画
import '@/plugins/animate.css'

// 路由
import { setupRouter } from './router'

// 权限
import { setupPermission } from './directives'

import { createApp } from 'vue'

import App from './App.vue'

import './permission'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import constant from '@/constant/index'
import api from '@/api/index'

import dateFormatter from '@/utils/dateFormatter.js'

import StringUtil from '@/utils/stringUtil.js'

import uploader from 'vue-simple-uploader'

import 'vue-simple-uploader/dist/style.css'

// web socket
import webSocket from '@/modules/notification/view/systemMessage/webSocket.js'

// SSE
import sseInstance from '@/modules/notification/view/systemMessage/sse.js'

// 可自配置的网格布局
import VueGridLayout from 'vue-grid-layout'

// echart图表
import 'echarts'
import ECharts from 'vue-echarts'

// 表单构建器
import FormCreate from '@form-create/element-ui'
import FcDesigner from '@form-create/designer'

import { Base64 } from 'js-base64'

// markdown 预览组件
import VMdPreview from '@kangc/v-md-editor/lib/preview'
import '@kangc/v-md-editor/lib/style/preview.css'
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js'
import '@kangc/v-md-editor/lib/theme/style/github.css'

// 自定义组件

import DictionarySelect from '@/components/abc/DictionarySelect/DictionarySelect.vue'
import DictionaryRadioGroup from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'
import OrganizationSingleSelect from '@/modules/system/view/organization/treeReference.vue'
import OrganizationMultipleSelect from '@/modules/system/view/organization/treeMultipleSelect.vue'
import UserSingleSelect from '@/modules/system/view/user/treeListReference.vue'
import IconPicker from '@/components/abc/IconPicker/index.vue'
import { Editor } from '@/components/abc/Editor'
import AttachmentUploader from '@/modules/support/view/attachment/attachmentUploader.vue'
import AttachmentManager from '@/modules/support/view/attachment/attachmentManager.vue'
import AttachmentViewer from '@/modules/support/view/attachment/attachmentViewer.vue'
import AttachmentManagerAndUploader from '@/modules/support/view/attachment/attachmentManagerAndUploader.vue'

FormCreate.component(DictionarySelect)
FormCreate.component(DictionaryRadioGroup)
FormCreate.component(OrganizationSingleSelect)
FormCreate.component(OrganizationMultipleSelect)
FormCreate.component(UserSingleSelect)
FormCreate.component(IconPicker)
FormCreate.component(Editor)
FormCreate.component(AttachmentUploader)
FormCreate.component(AttachmentManager)
FormCreate.component(AttachmentViewer)
FormCreate.component(AttachmentManagerAndUploader)

VMdPreview.use(githubTheme, {})

// 创建实例
const setupAll = async () => {
  const app = createApp(App)

  await setupI18n(app)

  setupStore(app)

  setupGlobCom(app)

  setupRouter(app)

  setupPermission(app)

  // 全量注册element-plus组件
  app.use(ElementPlus)

  // 自定义表单
  app.use(FormCreate)
  app.use(FcDesigner)

  // 全量注册element-plus图标
  app.config.globalProperties.$icons = []
  for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
    // 将图标键值放入全局变量，用于图标选择器
    app.config.globalProperties.$icons.push(key)
  }

  // 挂载全局变量，兼容2.0习惯写法
  app.config.globalProperties.$constant = constant

  app.config.globalProperties.$api = api

  // 日期函数
  app.config.globalProperties.$dateFormatter = dateFormatter

  // 字符串工具函数
  app.config.globalProperties.$StringUtil = StringUtil

  // web socket
  app.config.globalProperties.$webSocket = webSocket

  // sse
  app.config.globalProperties.$sse = sseInstance.getInstance()

  // base64工具类
  app.config.globalProperties.$base64Util = Base64

  // 文件上传
  app.use(uploader)

  // 可自配置的网格布局
  app.use(VueGridLayout)

  // markdown浏览器
  app.use(VMdPreview)

  // echart图表
  app.component('v-chart', ECharts)

  app.mount('#app')

  app.directive('focus', {
    mounted(el) {
      el.focus()
    }
  })

  app.directive('enterNumber', {
    mounted(el, { value = 100 }, vnode) {
      el = el.nodeName == 'INPUT' ? el : el.children[0]
      var RegStr = value == 0 ? `^[\\+\\-]?\\d+\\d{0,0}` : `^[\\+\\-]?\\d+\\.?\\d{0,${value}}`
      el.addEventListener('input', function () {
        el.value = el.value.match(new RegExp(RegStr, 'g'))
        el.dispatchEvent(new Event('input'))
      })
    }
  })
}

setupAll()
