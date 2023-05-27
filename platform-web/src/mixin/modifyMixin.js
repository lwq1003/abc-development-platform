/**
 * 修改页面混入
 */

import { Dialog } from '@/components/abc/Dialog'
import DictionaryRadioGroup from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'
import DictionarySelect from '@/components/abc/DictionarySelect/DictionarySelect.vue'
import DataDictionarySelect from '@/modules/system/view/dictionaryType/treeReferenceUseCode.vue'
import IconPicker from '@/components/abc/IconPicker/index.vue'
import { Editor } from '@/components/abc/Editor'
import CronExpression from '@/components/abc/CronExpression/index.vue'
export const modifyMixin = {
  components: {
    Dialog,
    DictionaryRadioGroup,
    DictionarySelect,
    DataDictionarySelect,
    IconPicker,
    Editor,
    CronExpression
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
    // 保存
    save() {
      if (this.beforeSave) {
        this.beforeSave()
      }
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.validateData) {
            // 数据验证通过后才执行保存操作
            if (this.validateData()) {
              this.saveData()
            }
          } else {
            // 无需数据验证，直接执行
            this.saveData()
          }
        }
      })
    },
    // 关闭
    close() {
      this.visible = false
    },
    // 保存
    saveData() {
      this.api
        .modify(this.entityData)
        .then((res) => {
          this.entityData = res.data
          if (this.afterSave) {
            this.afterSave()
          }
          this.$emit('refresh', this.entityData)
          this.close()
        })
        .finally(() => {
          this.loading = false
        })
    }
  }
}
