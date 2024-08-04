/**
 * 新增页面混入
 */

import { Dialog } from '@/components/abc/Dialog'

export const addMixin = {
  components: {
    Dialog
  },
  data() {
    return {
      // 可见性
      visible: false,
      // 加载中
      loading: false,
      fApi: {},
      entityId: '',
      formValue: {}
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
        this.formValue = res.data
        this.entityId = res.data.id
        this.updateData()
        if (this.afterInit) {
          this.afterInit(param)
        }
        this.visible = true
      })
    },
    //更新标签值
    updateData() {
      const ruleString = JSON.stringify(this.rule)
        .replaceAll('@EntityId@', this.entityId, 'g')
        .replaceAll('@EntityType@', this.entityType, 'g')
        .replaceAll('@ModuleCode@', this.moduleCode, 'g')
      this.rule = JSON.parse(ruleString)
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
    // 关闭
    close() {
      this.visible = false
    },
    // 保存
    saveData() {
      const data = Object.assign(this.entityData, this.formValue)
      this.api
        .add(data)
        .then((res) => {
          this.entityData = res.data
          this.formValue = res.data
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
