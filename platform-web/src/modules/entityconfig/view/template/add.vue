<template>
  <Dialog title="新增" v-model="visible" width="800px">
    <form-create :rule="rule" v-model:api="fApi" :option="options" v-model="formValue" />
    <template #footer>
      <el-button type="primary" @click="save" v-permission="pageCode + 'modify'">保存</el-button>
      <el-button @click="close">关闭</el-button>
    </template>
  </Dialog>
</template>

<script>
import { addMixin } from '@/mixin/addForAdvanceConfigMixin.js'
import UserReference from '@/modules/system/view/user/treeListReference.vue'
const MODULE_CODE = 'entityconfig'
const ENTITY_TYPE = 'template'
export default {
  name: ENTITY_TYPE + '-add',
  components: {
    UserReference
  },
  mixins: [addMixin],
  data() {
    return {
      entityType: ENTITY_TYPE,
      moduleCode: MODULE_CODE,
      // eslint-disable-next-line no-eval
      api: eval('this.$api.' + MODULE_CODE + '.' + ENTITY_TYPE),
      pageCode: MODULE_CODE + ':' + ENTITY_TYPE + ':',
      entityData: {},
      // 用户组件参数，用于传递数据
      userParam: {},
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
                  type: 'input',
                  title: '用户',
                  field: 'entity',
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
                  hidden: false,
                  display: true,
                  value: '',
                  type: 'IconPicker',
                  title: '图标',
                  field: 'icon',
                  validate: [{ required: true, message: '【图标】不能为空', trigger: 'blur' }],
                  _fc_drag_tag: 'IconPicker'
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
                  title: '流水号',
                  field: 'serialNo',
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
                  validate: [{ required: true, message: '【编码】不能为空', trigger: 'blur' }],
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
                  hidden: false,
                  display: true,
                  value: null,
                  type: 'UserSingleSelect',
                  title: '用户单选',
                  field: 'userSingle',
                  validate: [{ required: true, message: '【用户单选】不能为空', trigger: 'blur' }],
                  _fc_drag_tag: 'UserSingleSelect'
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
                  type: 'OrganizationSingleSelect',
                  title: '组织机构单选',
                  field: 'organizationSingle',
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
                  hidden: false,
                  display: true,
                  value: '',
                  type: 'OrganizationMultipleSelect',
                  title: '组织机构多选',
                  field: 'organizationMultiple',
                  _fc_drag_tag: 'OrganizationMultipleSelect'
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
                  props: { valueFormat: 'YYYY-MM-DD 00:00:00', type: 'date' },
                  hidden: false,
                  display: true,
                  value: null,
                  type: 'datePicker',
                  title: '日期',
                  field: 'date',
                  _fc_drag_tag: 'datePicker'
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
                  props: { valueFormat: 'YYYY-MM-DD HH:mm:ss', type: 'datetime' },
                  hidden: false,
                  display: true,
                  value: null,
                  type: 'datePicker',
                  title: '时间',
                  field: 'time',
                  _fc_drag_tag: 'datePicker'
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
                  props: { code: 'YesOrNo' },
                  hidden: false,
                  display: true,
                  value: '',
                  type: 'DictionaryRadioGroup',
                  title: '是否',
                  field: 'yesOrNo',
                  validate: [{ required: true, message: '【是否】不能为空', trigger: 'blur' }],
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
                  props: { code: 'Status' },
                  hidden: false,
                  display: true,
                  value: 'NORMAL',
                  type: 'DictionarySelect',
                  title: '状态',
                  field: 'status',
                  validate: [{ required: true, message: '【状态】不能为空', trigger: 'blur' }],
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
                  props: { type: 'text' },
                  hidden: false,
                  display: true,
                  value: '',
                  type: 'input',
                  title: '排序',
                  field: 'orderNo',
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
                  props: { type: 'textarea', rows: '4' },
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
                  type: 'RichText',
                  title: '说明',
                  field: 'description',
                  _fc_drag_tag: 'RichText'
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
                  props: {
                    entityId: '@EntityId@',
                    entityType: '@EntityType@',
                    moduleCode: '@ModuleCode@'
                  },
                  hidden: false,
                  display: true,
                  value: '',
                  type: 'AttachmentUploader',
                  title: '附件上传',
                  field: 'attachmentUpload',
                  _fc_drag_tag: 'AttachmentUploader'
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
                  props: { entityId: '@EntityId@', ref: 'attachmentManager' },
                  hidden: false,
                  display: true,
                  value: '',
                  type: 'AttachmentManager',
                  title: '附件管理',
                  field: 'attachmentManage',
                  _fc_drag_tag: 'AttachmentManager'
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
                  props: { entityId: '@EntityId@' },
                  hidden: false,
                  display: true,
                  value: '',
                  type: 'AttachmentViewer',
                  title: '附件查看',
                  field: 'attachmentView',
                  _fc_drag_tag: 'AttachmentViewer'
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
                  props: {
                    entityId: '@EntityId@',
                    entityType: '@EntityType@',
                    moduleCode: '@ModuleCode@'
                  },
                  hidden: false,
                  display: true,
                  value: '',
                  type: 'AttachmentManagerAndUploader',
                  title: '附件管理及上传',
                  field: 'attachmentManagerAndUploader',
                  _fc_drag_tag: 'AttachmentManagerAndUploader'
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
