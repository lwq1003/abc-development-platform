import dateFormatter from '@/utils/dateFormatter.js'

export function formatDate(row, column, cellValue) {
  return dateFormatter.formatUTCDate(cellValue)
}

export function formatTime(row, column, cellValue) {
  return dateFormatter.formatUTCTime(cellValue)
}
