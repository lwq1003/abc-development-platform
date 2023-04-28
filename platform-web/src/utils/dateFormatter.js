import moment from 'moment'
const dateFormatter = {
  // 格式化UTC日期
  formatUTCDate: function (time, format = 'YYYY-MM-DD') {
    // 获取系统时区，后期会改成用户选择时区
    const zoneOffset = moment().utcOffset() / 60
    return time ? moment(time).utcOffset(zoneOffset).format(format) : ''
  },
  // 格式化UTC时间
  formatUTCTime: function (time, format = 'YYYY-MM-DD HH:mm:ss') {
    // 获取系统时区，后期会改成用户选择时区
    const zoneOffset = moment().utcOffset() / 60
    return time ? moment(time).utcOffset(zoneOffset).format(format) : ''
  },
  // 获取日期时间格式
  getDatetimeFormat: function (formatCode) {
    let formatString = ''
    switch (formatCode) {
      case 'DAY':
        formatString = 'YYYY-MM-DD 00:00:00'
        break
      case 'MINITE':
        formatString = 'YYYY-MM-DD HH:mm:00'
        break
      case 'SECOND':
        formatString = 'YYYY-MM-DD HH:mm:ss'
        break
    }
    return formatString
  },

  // 获取日期时间控件类型
  getDatetimeType: function (formatCode) {
    let type = ''
    switch (formatCode) {
      case 'DAY':
        type = 'date'
        break
      case 'MINITE':
      case 'SECOND':
        type = 'datetime'
        break
    }
    return type
  }
}

export default dateFormatter
