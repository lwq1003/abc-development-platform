const StringUtil = {
  // 将下划线或横杠连接的字符串转换为小驼峰命名
  toCamelCase: function (str) {
    return str.toLowerCase().replace(/[-_]+([a-zA-Z])/g, function (match, letter) {
      return letter.toUpperCase()
    })
  },

  // 首字母大写
  capitalizeFirstLetter: function (str) {
    return str.replace(/^./, (match) => match.toUpperCase())
  }
}

export default StringUtil
