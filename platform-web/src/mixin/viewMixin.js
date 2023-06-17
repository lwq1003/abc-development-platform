/**
 * 查看页面混入
 */

import { Dialog } from '@/components/abc/Dialog'
import DictionaryRadioGroup from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'
import DictionarySelect from '@/components/abc/DictionarySelect/DictionarySelect.vue'
import DataDictionarySelect from '@/modules/system/view/dictionaryType/treeReferenceUseCode.vue'
import IconPicker from '@/components/abc/IconPicker/index.vue'
import { Editor } from '@/components/abc/Editor'
import CronExpression from '@/components/abc/CronExpression/index.vue'
import OrganizationMultipleSelect from '@/modules/system/view/organization/treeMultipleReference.vue'

export const viewMixin = {
  components: {
    Dialog,
    DictionaryRadioGroup,
    DictionarySelect,
    DataDictionarySelect,
    IconPicker,
    Editor,
    CronExpression,
    OrganizationMultipleSelect
  },
  data() {
    return {
      // 可见性
      visible: false,
      // 加载中
      loading: false
    }
  },

  methods: {
    // 初始化
    init(id) {
      if (this.beforeInit) {
        this.beforeInit()
      }
      this.api.get(id).then((res) => {
        this.entityData = res.data
        if (this.afterInit) {
          this.afterInit()
        }
        this.visible = true
      })
    },
    // 查看
    view(id) {
      if (this.beforeView) {
        this.beforeView()
      }
      this.api.get(id).then((res) => {
        this.entityData = res.data
        if (this.afterView) {
          this.afterView()
        }
        this.visible = true
      })
    },

    // 关闭
    close() {
      this.visible = false
    }
  }
}
