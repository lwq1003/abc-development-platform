/**
 * 详情页面混入,包括新增、修改和查看功能
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
// import { checkButtonPermission } from '@/utils/auth'

export const detailMixin = {
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
      loading: false,
      // 模式
      mode: '',
      // 只读
      readonly: false
    }
  },
  computed: {
    title() {
      let text = ''
      if (this.mode === 'add') {
        text = '新增'
      } else if (this.mode === 'modify') {
        text = '修改'
      } else if (this.mode === 'view') {
        text = '查看'
      }
      return text
    }
   
  },
  methods: {
    // 带参初始化,参数可为空
    init(mode, param) {
      // 获取到模式
      this.mode = mode
      // 初始化前操作
      if (this.beforeInit) {
        this.beforeInit(param)
      }
      if (this.mode === 'add') {
        // 新增
        this.api.init().then((res) => {
          this.loadData(res.data)
        })
      } else if (this.mode === 'modify') {
        // 修改
        this.api.get(param).then((res) => {
          this.loadData(res.data)
        })
      } else if (this.mode === 'view') {
        // 查看
        this.api.get(param).then((res) => {
          this.loadData(res.data)
        })
      }
    },
    // 加载数据
    loadData(data) {
      this.entityData = data
      if (this.afterInit) {
        this.afterInit(data)
      }
      this.visible = true
    },
    // 关闭
    close() {
      this.visible = false
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
              if (this.beforeSaveData) {
                this.beforeSaveData()
                  .then(() => {
                    this.saveData()
                  })
                  .catch(() => {
                    this.$message.info('已取消')
                  })
              } else {
                this.saveData()
              }
            }
          } else {
            // 无需数据验证，直接执行
            if (this.beforeSaveData) {
              this.beforeSaveData()
                .then(() => {
                  this.saveData()
                })
                .catch(() => {
                  this.$message.info('已取消')
                })
            } else {
              this.saveData()
            }
          }
        }
      })
    },
    // 保存
    saveData() {
      if (this.mode === 'add') {
        // 新增
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
      } else if (this.mode === 'modify') {
        // 修改
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
}
