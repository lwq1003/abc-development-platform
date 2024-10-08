import moment from 'moment'



/**
 * 日期时间格式化
 * @param datetime 日期时间
 */
export function formatDateTime(datetime, format) {
	// 获取系统时区，后期会改成用户选择时区
	const zoneOffset = moment().utcOffset() / 60
	return datetime ? moment(datetime).utcOffset(zoneOffset).format(format) : ''
}

/**
 * 日期格式化
 * @param date 日期
 */
export function formatDate(date) {
	return formatDateTime(date, 'YYYY-MM-DD')
}

/**
 * 时间格式化
 * @param time 时间
 */
export function formatTime(time) {
	return formatDateTime(time, 'YYYY-MM-DD HH:mm:ss')
}

/**
 * 获取当天日期
 */
export function getToday() {
	const now = new Date()
	return formatDate(now)
}

/**
 * 获取时间差（单位秒）
 */
export function timeDiffSecond(startTime,endTime) {
	const end=new Date(endTime)
	const start=new Date(startTime)
	return (end-start) / 1000
}