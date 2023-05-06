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
public enum ApiMessageLogExceptionEnum implements ExceptionInterface {

    /**
     * 消息日志不存在
     */
    MESSAGE_LOG_NOT_EXIST("消息日志不存在"),

    ;
    private String message;

    ApiMessageLogExceptionEnum(String message) {
        this.message = message;
    }

}
