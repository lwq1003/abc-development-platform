package tech.abc.platform.mail.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 邮件相关异常
 *
 * @author wqliu
 * @date 2023-05-25
 */
@Getter
public enum MailExceptionEnum implements ExceptionInterface {

    /**
     * 发送失败
     */
    SEND_ERROR("发送失败:{0}"),
    ;

    private String message;

    MailExceptionEnum(String message) {
        this.message = message;
    }

}
