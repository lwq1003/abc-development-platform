import dateFormatter from '@/utils/dateFormatter.js'

export function formatDate(row, column, cellValue) {
  return dateFormatter.formatUTCDate(cellValue)
}

export function formatTime(row, column, cellValue) {
  return dateFormatter.formatUTCTime(cellValue)
}

export function getFormatMethod(name) {
  if (name === 'FORMAT_DATE') {
    return formatDate
  } else if (name === 'FORMAT_TIME') {
    return formatTime
  }
}
