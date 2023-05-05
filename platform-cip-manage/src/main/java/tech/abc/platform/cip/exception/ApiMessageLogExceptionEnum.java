package tech.abc.platform.cip.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 消息主题相关异常
 *
 * @author wqliu
 * @date: 2020-03-29
 */
@Getter
public enum ApiMessageLogExceptionEnum implements ExceptionInterface {

    /**
     * 消息日志不存在
     */
    MESSAGE_LOG_NOT_EXIST("消息日志不存在"),
    /**
     * 无权确认该条消息
     */
    MESSAGE_CONFIRM_PERMISSION_ERROR("无权确认该条消息"),
    ;
    private String message;

    ApiMessageLogExceptionEnum(String message) {
        this.message = message;
    }

}
