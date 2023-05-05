package tech.abc.platform.cip.common.enums;

/**
 * 消息状态，专用于消息客户端模式
 * @author  wqliu
 * @date  2021-10-5
*/
public enum MessageStatusEnum
{

	/**
	 * 待请求
	 */
	WAIT_REQUEST,

	/**
	 * 已请求
	 */
	REQUESTED,

	/**
	 * 已响应
	 */
	RESPONSED,

	/**
	 * 无需请求(消息类型为响应消息或客户端未订阅消息)
	 */
	NOT_TO_REQUEST
	

}
