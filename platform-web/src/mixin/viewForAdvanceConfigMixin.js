/**
 * 查看页面混入
 */

import { Dialog } from '@/components/abc/Dialog'

export const viewMixin = {
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

    // 关闭
    close() {
      this.visible = false
    }
  }
}
