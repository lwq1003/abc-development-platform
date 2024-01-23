<template>
  <fc-designer v-model="value" ref="designer" height="800px">
    <template #handle>
      <el-button type="primary" plain round size="small" @click="save"
        ><i class="fc-icon icon-select"></i> 保 存
      </el-button>
    </template>
  </fc-designer>
</template>

<script lang="ts">
import DictionarySelectDesigner from '@/components/abc/FormCreateComponent/DictionarySelect.js'
import DictionaryRadioGroupDesigner from '@/components/abc/FormCreateComponent/DictionaryRadioGroup.js'
import OrganizationSingleSelectDesigner from '@/components/abc/FormCreateComponent/OrganizationSingleSelect.js'
import OrganizationMultipleSelectDesigner from '@/components/abc/FormCreateComponent/OrganizationMultipleSelect.js'
import UserSingleSelectDesigner from '@/components/abc/FormCreateComponent/UserSingleSelect.js'
import IconPickerDesigner from '@/components/abc/FormCreateComponent/IconPicker.js'
import RichTextDesigner from '@/components/abc/FormCreateComponent/RichText.js'
import { maker } from '@form-create/element-ui'
import AttachmentUploaderDesigner from '@/components/abc/FormCreateComponent/AttachmentUploader.js'
import AttachmentManagerDesigner from '@/components/abc/FormCreateComponent/AttachmentManager.js'
import AttachmentViewerDesigner from '@/components/abc/FormCreateComponent/AttachmentViewer.js'
import AttachmentManagerAndUploaderDesigner from '@/components/abc/FormCreateComponent/AttachmentManagerAndUploader.js'
export default {
  components: {
    DictionarySelectDesigner,
    DictionaryRadioGroupDesigner,
    OrganizationSingleSelectDesigner,
    OrganizationMultipleSelectDesigner,
    UserSingleSelectDesigner,
    IconPickerDesigner,
    RichTextDesigner,
    maker,
    AttachmentUploaderDesigner,
    AttachmentManagerDesigner,
    AttachmentViewerDesigner,
    AttachmentManagerAndUploaderDesigner
  },
  data() {
    return {
      options: {
        onSubmit: (formData) => {
          alert(JSON.stringify(formData))
        },
        resetBtn: true
      },
      rule: [],
      //表单数据
      value: {}
    }
  },
  mounted() {
    //初始化表单选项
    this.initAdvanceOption()

    // 注册自定义组件
    this.regsterCustomComponent()

    //生成自定义组件组
    this.generateCustomComponentGroup()

    this.$api.entityconfig.entityView.get(this.$route.query.id).then((res) => {
      const entityView = res.data
      if (entityView.advanceConfigRule && entityView.advanceConfigRule != '[]') {
        this.rule = JSON.parse(entityView.advanceConfigRule)
        this.options = JSON.parse(entityView.advanceConfigOption)
        this.$refs.designer.setRule(this.rule)
        this.$refs.designer.setOptions(this.options)
      } else {
        //  双列初始化
        let row = {}
        let col = {}
        this.$api.entityconfig.viewProperty.listByView(this.$route.query.id).then((res) => {
          const propertyList = res.data
          propertyList.forEach((item, index) => {
            if (index % 2 == 0) {
              row = {
                type: 'FcRow',
                _fc_drag_tag: 'row',
                hidden: false,
                display: true,
                children: []
              }
              col = {
                type: 'col',
                props: {
                  span: 12
                },
                _fc_drag_tag: 'col',
                hidden: false,
                display: true
              }
              row.children = [col]
              this.rule.push(row)
            } else {
              col = {
                type: 'col',
                props: {
                  span: 12
                },
                _fc_drag_tag: 'col',
                hidden: false,
                display: true
              }
              row.children.push(col)
            }

            let property = {}
            if (item.widgetType == 'TEXT') {
              property = maker.create('input', item.code, item.name).props({ type: 'text' })
            } else if (item.widgetType == 'TEXTAREA') {
              property = maker
                .create('input', item.code, item.name)
                .props({ type: 'textarea', rows: '4' })
            } else if (item.widgetType == 'RICH_TEXT') {
              property = maker.create(RichTextDesigner.name, item.code, item.name)
            } else if (item.widgetType == 'DROP_DOWN_LIST') {
              property = maker
                .create(DictionarySelectDesigner.name, item.code, item.name)
                .props({ code: item.dictionaryType })
            } else if (item.widgetType == 'RADIO_BUTTON_GROUP') {
              property = maker
                .create(DictionaryRadioGroupDesigner.name, item.code, item.name)
                .props({ code: item.dictionaryType })
            } else if (item.widgetType == 'ORGANIZATION_SINGLE_SELECT') {
              property = maker.create(OrganizationSingleSelectDesigner.name, item.code, item.name)
            } else if (item.widgetType == 'ORGANIZATION_MULTIPLE_SELECT') {
              property = maker.create(OrganizationMultipleSelectDesigner.name, item.code, item.name)
            } else if (item.widgetType == 'USER_SINGLE_SELECT') {
              property = maker.create(UserSingleSelectDesigner.name, item.code, item.name)
            } else if (item.widgetType == 'USER_SINGLE_SELECT') {
              property = maker.create(UserSingleSelectDesigner.name, item.code, item.name)
            } else if (item.widgetType == 'ICON_PICKER') {
              property = maker.create(IconPickerDesigner.name, item.code, item.name)
            } else if (item.widgetType == 'DATETIME') {
              property = maker.datePicker(item.name, item.code, item.defaultValue).props({
                valueFormat: this.$dateFormatter.getDatetimeFormat(item.formatPattern),
                type: this.$dateFormatter.getDatetimeType(item.formatPattern)
              })
            } else if (item.widgetType == 'MANAGE_AND_UPLOAD') {
              property = maker
                .create(AttachmentManagerAndUploaderDesigner.name, item.code, item.name)
                .props({
                  entityId: '@EntityId@',
                  entityType: '@EntityType@',
                  moduleCode: '@ModuleCode@'
                })
            } else if (item.widgetType == 'UPLOAD') {
              property = maker.create(AttachmentUploaderDesigner.name, item.code, item.name).props({
                entityId: '@EntityId@',
                entityType: '@EntityType@',
                moduleCode: '@ModuleCode@'
              })
            } else if (item.widgetType == 'MANAGE') {
              property = maker.create(AttachmentManagerDesigner.name, item.code, item.name).props({
                entityId: '@EntityId@',
                ref: 'attachmentManager'
              })
            } else if (item.widgetType == 'VIEW') {
              property = maker.create(AttachmentViewerDesigner.name, item.code, item.name).props({
                entityId: '@EntityId@'
              })
            } else {
              property = maker.input(item.name, item.code, item.defaultValue)
            }
            // 设置默认值
            property = property.value(item.defaultValue)
            //设置必填
            if (item.requireFlag == 'YES') {
              property = property.validate([
                { required: true, message: '【' + item.name + '】不能为空', trigger: 'blur' }
              ])
            }
            // 设置只读
            if (item.readonly == 'YES') {
              property = property.readonly(true)
            }
            col.children = [property.getRule()]
          })
          this.$refs.designer.setRule(this.rule)
        })
      }
    })
  },
  methods: {
    //初始化表单选项
    initAdvanceOption() {
      const option = {
        form: {
          labelPosition: 'right',
          size: 'default',
          labelWidth: '120px',
          hideRequiredAsterisk: false,
          showMessage: true,
          inlineMessage: false
        },
        submitBtn: false
      }
      this.$refs.designer.setOption(option)
    },
    regsterCustomComponent() {
      this.$refs.designer.addComponent(DictionarySelectDesigner)
      this.$refs.designer.addComponent(DictionaryRadioGroupDesigner)
      this.$refs.designer.addComponent(OrganizationSingleSelectDesigner)
      this.$refs.designer.addComponent(OrganizationMultipleSelectDesigner)
      this.$refs.designer.addComponent(UserSingleSelectDesigner)
      this.$refs.designer.addComponent(IconPickerDesigner)
      this.$refs.designer.addComponent(RichTextDesigner)
      this.$refs.designer.addComponent(AttachmentUploaderDesigner)
      this.$refs.designer.addComponent(AttachmentManagerDesigner)
      this.$refs.designer.addComponent(AttachmentViewerDesigner)
      this.$refs.designer.addComponent(AttachmentManagerAndUploaderDesigner)
    },
    generateCustomComponentGroup() {
      //读取自定义组件信息，生成左侧组件对象
      const dictionarySelectButton = {
        icon: DictionarySelectDesigner.icon,
        name: DictionarySelectDesigner.name,
        label: DictionarySelectDesigner.label
      }
      const dictionaryRadioGroupButton = {
        icon: DictionaryRadioGroupDesigner.icon,
        name: DictionaryRadioGroupDesigner.name,
        label: DictionaryRadioGroupDesigner.label
      }
      const organizationSingleSelectButton = {
        icon: OrganizationSingleSelectDesigner.icon,
        name: OrganizationSingleSelectDesigner.name,
        label: OrganizationSingleSelectDesigner.label
      }
      const organizationMultipleSelectButton = {
        icon: OrganizationMultipleSelectDesigner.icon,
        name: OrganizationMultipleSelectDesigner.name,
        label: OrganizationMultipleSelectDesigner.label
      }
      const userSingleSelectButton = {
        icon: UserSingleSelectDesigner.icon,
        name: UserSingleSelectDesigner.name,
        label: UserSingleSelectDesigner.label
      }
      const iconPickerButton = {
        icon: IconPickerDesigner.icon,
        name: IconPickerDesigner.name,
        label: IconPickerDesigner.label
      }
      const richTextButton = {
        icon: RichTextDesigner.icon,
        name: RichTextDesigner.name,
        label: RichTextDesigner.label
      }

      const attachmentUploaderButton = {
        icon: AttachmentUploaderDesigner.icon,
        name: AttachmentUploaderDesigner.name,
        label: AttachmentUploaderDesigner.label
      }
      const attachmentManagerButton = {
        icon: AttachmentManagerDesigner.icon,
        name: AttachmentManagerDesigner.name,
        label: AttachmentManagerDesigner.label
      }
      const attachmentViewerButton = {
        icon: AttachmentViewerDesigner.icon,
        name: AttachmentViewerDesigner.name,
        label: AttachmentViewerDesigner.label
      }

      const attachmentManagerAndUploaderButton = {
        icon: AttachmentManagerAndUploaderDesigner.icon,
        name: AttachmentManagerAndUploaderDesigner.name,
        label: AttachmentManagerAndUploaderDesigner.label
      }

      // 插入自定义分组及自定义组件
      this.$refs.designer.addMenu({
        title: '自定义',
        name: 'custom',
        list: [
          dictionarySelectButton,
          dictionaryRadioGroupButton,
          organizationSingleSelectButton,
          organizationMultipleSelectButton,
          userSingleSelectButton,
          iconPickerButton,
          richTextButton,
          attachmentUploaderButton,
          attachmentManagerButton,
          attachmentViewerButton,
          attachmentManagerAndUploaderButton
        ]
      })
    },
    preview() {
      this.rule = this.$refs.designer.getRule()
      this.options = this.$refs.designer.getOption()
    },
    clear() {
      const param = {
        id: this.$route.query.id,
        advanceConfigRule: '',
        advanceConfigOption: ''
      }
      this.$api.entityconfig.entityView.updateAdvanceConfig(param)
    },
    save() {
      const param = {
        id: this.$route.query.id,
        advanceConfigRule: JSON.stringify(this.$refs.designer.getRule()),
        advanceConfigOption: JSON.stringify(this.$refs.designer.getOption())
      }
      this.$api.entityconfig.entityView.updateAdvanceConfig(param)
    }
  }
}
</script>

<style></style>
