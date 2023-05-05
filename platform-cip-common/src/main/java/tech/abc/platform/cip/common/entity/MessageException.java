package tech.abc.platform.cip.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息异常
 *
 * @author wqliu
 * @date 2021-10-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageException extends RuntimeException {

    /**
     * 错误编码
     */
    private String errorCode;

    public MessageException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

}
