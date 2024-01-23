/**
 * 高级配置修改页面混入
 */

import { Dialog } from '@/components/abc/Dialog'

export const modifyMixin = {
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
    // 初始化
    init(id) {
      if (this.beforeInit) {
        this.beforeInit()
      }

      this.api.get(id).then((res) => {
        this.entityData = res.data
        this.formValue = res.data
        this.entityId = res.data.id
        this.updateData()
        if (this.afterInit) {
          this.afterInit()
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
      this.fApi.validate((valid, fail) => {
        if (valid === true) {
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
      const data = Object.assign(this.entityData, this.formValue)
      this.api
        .modify(data)
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
