package tech.abc.platform.notification.enums;

import lombok.Getter;

/**
 * 消息类型
 *
 * @author wqliu
 */
@Getter
public enum SystemMessageTypeEnum {
    /**
     * 登录请求
     */
    LOGIN_REQUEST,

    /**
     * 登录响应
     */
    LOGIN_RESPONSE,

    /**
     * 心跳请求
     */
    HEARTBEAT_REQUEST,

    /**
     * 心跳响应
     */
    HEARTBEAT_RESPONSE,

    /**
     * 业务消息
     */
    BUSINESS_MESSAGE,


    /**
     * 注销请求
     */
    LOGOUT_REQUEST,

    ;


}
