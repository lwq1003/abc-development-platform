package tech.popsoft.cip.client.manage.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 消息主题相关异常
 *
 * @author wqliu
 * @date: 2020-03-29 19:11
 */
@Getter
public enum ApiMessageTopicExceptionEnum implements ExceptionInterface {

    /**
     * 消息主题不存在
     */
    TOPIC_NOT_EXIST("消息主题不存在"),
    /**
     * 消息主题未设置处理器
     */
    TOPIC_NOT_SET_HANDLER("消息主题未设置处理器"),
    /**
     * 消息主题未设置发送器
     */
    TOPIC_NOT_SET_SENDER("消息主题未设置发送器"),
    /**
     * 响应消息主题未设置处理器
     */
    RESPONSE_TOPIC_NOT_SET_HANDLER("响应消息主题未设置处理器"),
    ;
    private String message;

    ApiMessageTopicExceptionEnum(String message) {
        this.message = message;
    }

}
