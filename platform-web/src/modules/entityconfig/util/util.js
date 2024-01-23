import * as constant from '@/modules/entityconfig/constant/index.ts'
const util = {
  // 根据数据类型获取控件类型数据字典编码
  getWidgetType: function (dataType) {
    let code = ''
    switch (dataType) {
      case constant.DATA_TYPE.STRING: {
        code = constant.WIDGET_TYPE.TEXT
        break
      }
      case constant.DATA_TYPE.INTEGER:
      case constant.DATA_TYPE.LONG:
      case constant.DATA_TYPE.DOUBLE:
      case constant.DATA_TYPE.DECIMAL: {
        code = constant.WIDGET_TYPE.NUMBER
        break
      }
      case constant.DATA_TYPE.DATETIME: {
        code = constant.WIDGET_TYPE.DATETIME
        break
      }
      case constant.DATA_TYPE.DATA_DICTIONARY: {
        code = constant.WIDGET_TYPE.DATA_DICTIONARY
        break
      }
      case constant.DATA_TYPE.ENTITY: {
        code = constant.WIDGET_TYPE.ENTITY
        break
      }
      case constant.DATA_TYPE.ICON_PICKER: {
        code = constant.WIDGET_TYPE.ICON_PICKER
        break
      }
      case constant.DATA_TYPE.ORGANIZATION_SINGLE: {
        code = constant.WIDGET_TYPE.ORGANIZATION_SINGLE
        break
      }
      case constant.DATA_TYPE.ORGANIZATION_MULTIPLE: {
        code = constant.WIDGET_TYPE.ORGANIZATION_MULTIPLE
        break
      }
      case constant.DATA_TYPE.ATTACHMENT: {
        code = constant.WIDGET_TYPE.ATTACHMENT
        break
      }
      case constant.DATA_TYPE.USER_SINGLE: {
        code = constant.WIDGET_TYPE.USER_SINGLE
        break
      }
      default: {
        code = constant.WIDGET_TYPE.TEXT
        break
      }
    }
    return code
  }
}

export default util
