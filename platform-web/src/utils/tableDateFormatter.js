const tableDateFormatter = {
  // 格式化表格列的日期
  formatDate: function (row, column, cellValue) {
    return this.$dateFormatter.formatUTCDate(cellValue)
  },
  // 格式化表格列的时间
  formatTime: function (row, column, cellValue) {
    return this.$dateFormatter.formatUTCTime(cellValue)
  }
}

export default tableDateFormatter
