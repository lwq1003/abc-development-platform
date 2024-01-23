import FcDesigner from '@form-create/designer'
import { markRaw } from 'vue'
import CustomComponent from '@/components/abc/IconPicker/index.vue'
const label = CustomComponent.label
const name = CustomComponent.name
let i = 1
const uniqueId = () => `uni${i++}`
export default {
  icon: 'icon-select',
  label,
  name,
  rule() {
    return {
      type: name,
      component: markRaw(CustomComponent),
      field: uniqueId(),
      title: label,
      info: '',
      props: {}
    }
  },
  props() {
    return []
  }
}
