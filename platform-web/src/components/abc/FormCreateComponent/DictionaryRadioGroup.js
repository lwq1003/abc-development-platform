import FcDesigner from '@form-create/designer'
import { markRaw } from 'vue'
import CustomComponent from '@/components/abc/DictionarySelect/DictionaryRadioGroup.vue'
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
    return [
      //生成`checkbox`组件的`options`配置规则
      { type: 'input', field: 'code', title: '字典类型编码' }
    ]
  }
}
