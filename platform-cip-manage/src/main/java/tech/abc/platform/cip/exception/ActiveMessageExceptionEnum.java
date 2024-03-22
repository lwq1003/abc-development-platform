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
public enum ActiveMessageExceptionEnum implements ExceptionInterface {

    /**
     * 活跃消息不存在
     */
    ACTIVE_MESSAGE_NOT_EXIST("活跃消息不存在"),
    /**
     * 无权确认该条消息
     */
    MESSAGE_CONFIRM_PERMISSION_ERROR("无权确认该条消息"),
    ;
    private String message;

    ActiveMessageExceptionEnum(String message) {
        this.message = message;
    }

}
