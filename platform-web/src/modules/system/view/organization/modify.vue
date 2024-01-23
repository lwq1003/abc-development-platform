<template>
  <Dialog title="修改" v-model="visible" width="800px">
    <form-create :rule="rule" v-model:api="fApi" :option="options" v-model="formValue" />
    <template #footer>
      <el-button type="primary" @click="save" v-permission="pageCode + 'modify'">保存</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { modifyMixin } from '@/mixin/modifyForAdvanceConfigMixin.js'
const MODULE_CODE = 'system'
const ENTITY_TYPE = 'organization'
export default {
  name: ENTITY_TYPE + '-modify',
  components: {},
  mixins: [modifyMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      //fc组件
      options: {
        form: {
          labelPosition: 'right',
          size: 'default',
          labelWidth: '120px',
          hideRequiredAsterisk: false,
          showMessage: true,
          inlineMessage: false
        },
        submitBtn: { show: false, innerText: '提交' },
        resetBtn: { show: false, innerText: '重置' }
      },
      rule: [
        {
          type: 'FcRow',
          _fc_drag_tag: 'row',
          hidden: false,
          display: true,
          children: [
            {
              type: 'col',
              props: { span: 12 },
              _fc_drag_tag: 'col',
              hidden: false,
              display: true,
              children: [
                {
                  hidden: false,
                  display: true,
                  value: null,
                  type: 'OrganizationSingleSelect',
                  title: '组织机构',
                  field: 'organization',
                  validate: [{ required: true, message: '【组织机构】不能为空', trigger: 'blur' }],
                  _fc_drag_tag: 'OrganizationSingleSelect'
                }
              ]
            },
            {
              type: 'col',
              props: { span: 12 },
              _fc_drag_tag: 'col',
              hidden: false,
              display: true,
              children: [
                {
                  props: { type: 'text' },
                  hidden: false,
                  display: true,
                  value: null,
                  type: 'input',
                  title: '名称',
                  field: 'name',
                  validate: [{ required: true, message: '【名称】不能为空', trigger: 'blur' }],
                  _fc_drag_tag: 'input'
                }
              ]
            }
          ]
        },
        {
          type: 'FcRow',
          _fc_drag_tag: 'row',
          hidden: false,
          display: true,
          children: [
            {
              type: 'col',
              props: { span: 12 },
              _fc_drag_tag: 'col',
              hidden: false,
              display: true,
              children: [
                {
                  props: { type: 'text' },
                  hidden: false,
                  display: true,
                  value: null,
                  type: 'input',
                  title: '编码',
                  field: 'code',
                  _fc_drag_tag: 'input'
                }
              ]
            },
            {
              type: 'col',
              props: { span: 12 },
              _fc_drag_tag: 'col',
              hidden: false,
              display: true,
              children: [
                {
                  props: { code: 'OrganizationType' },
                  hidden: false,
                  display: true,
                  value: 'DEPARTMENT',
                  type: 'DictionarySelect',
                  title: '类型',
                  field: 'type',
                  validate: [{ required: true, message: '【类型】不能为空', trigger: 'blur' }],
                  _fc_drag_tag: 'DictionarySelect'
                }
              ]
            }
          ]
        },
        {
          type: 'FcRow',
          _fc_drag_tag: 'row',
          hidden: false,
          display: true,
          children: [
            {
              type: 'col',
              props: { span: 12 },
              _fc_drag_tag: 'col',
              hidden: false,
              display: true,
              children: [
                {
                  props: { code: 'Status' },
                  hidden: false,
                  display: true,
                  value: 'NORMAL',
                  type: 'DictionaryRadioGroup',
                  title: '状态',
                  field: 'status',
                  validate: [{ required: true, message: '【状态】不能为空', trigger: 'blur' }],
                  _fc_drag_tag: 'DictionaryRadioGroup'
                }
              ]
            },
            {
              type: 'col',
              props: { span: 12 },
              _fc_drag_tag: 'col',
              hidden: false,
              display: true,
              children: [
                {
                  props: { type: 'text' },
                  hidden: false,
                  display: true,
                  value: null,
                  type: 'input',
                  title: '排序',
                  field: 'orderNo',
                  _fc_drag_tag: 'input'
                }
              ]
            }
          ]
        },
        {
          type: 'FcRow',
          _fc_drag_tag: 'row',
          hidden: false,
          display: true,
          children: [
            {
              type: 'col',
              props: { span: 12 },
              _fc_drag_tag: 'col',
              hidden: false,
              display: true,
              children: [
                {
                  hidden: false,
                  display: true,
                  value: null,
                  type: 'input',
                  title: '备注',
                  field: 'remark',
                  _fc_drag_tag: 'input'
                }
              ]
            }
          ]
        }
      ]
    }
  },
  methods: {}
}
</script>

<style></style>
