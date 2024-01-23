/**
 * 新增页面混入
 */

import { Dialog } from '@/components/abc/Dialog'
import DictionaryRadioGroup from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'
import DictionarySelect from '@/components/abc/DictionarySelect/DictionarySelect.vue'
import DataDictionarySelect from '@/modules/system/view/dictionaryType/treeReferenceUseCode.vue'
import { Editor } from '@/components/abc/Editor'
import CronExpression from '@/components/abc/CronExpression/index.vue'
import OrganizationSingleSelect from '@/modules/system/view/organization/treeReference.vue'
import OrganizationMultipleSelect from '@/modules/system/view/organization/treeMultipleSelect.vue'
import UserSingleSelect from '@/modules/system/view/user/treeListReference.vue'
import IconPicker from '@/components/abc/IconPicker/index.vue'
import AttachmentManager from '@/modules/support/view/attachment/attachmentManager.vue'
import AttachmentUploader from '@/modules/support/view/attachment/attachmentUploader.vue'
import AttachmentViewer from '@/modules/support/view/attachment/attachmentViewer.vue'
import AttachmentManagerAndUploader from '@/modules/support/view/attachment/attachmentManagerAndUploader.vue'
export const addMixin = {
  components: {
    Dialog,
    DictionaryRadioGroup,
    DictionarySelect,
    DataDictionarySelect,
    Editor,
    CronExpression,
    OrganizationSingleSelect,
    OrganizationMultipleSelect,
    UserSingleSelect,
    IconPicker,
    AttachmentManager,
    AttachmentUploader,
    AttachmentViewer,
    AttachmentManagerAndUploader
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
    // 带参初始化,参数可为空
    init(param) {
      if (this.beforeInit) {
        this.beforeInit(param)
      }
      this.api.init().then((res) => {
        this.entityData = res.data
        if (this.afterInit) {
          this.afterInit(param)
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
        .add(this.entityData)
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
    },
    // 附件上传完成，刷新管理组件
    fileComplete() {
      this.$refs.attachmentManager.list()
    }
  }
}
