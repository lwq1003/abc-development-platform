import FcDesigner from '@form-create/designer'
import { markRaw } from 'vue'
import { Editor } from '@/components/abc/Editor'

const label = Editor.label
const name = Editor.name
let i = 1
const uniqueId = () => `uni${i++}`
export default {
  icon: 'icon-select',
  label,
  name,
  rule() {
    return {
      type: name,
      component: markRaw(Editor),
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
