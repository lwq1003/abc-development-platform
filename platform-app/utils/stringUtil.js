// 缩略
export function abbreviate(str, length) {
	// 长度未超出时，直接返回原字符串
	if (str) {
		if (str.length <= length) {
			return str
		}
		// 否则返回缩略
		return str.substring(0, length) + '……'
	} else {
		return ''
	}
}