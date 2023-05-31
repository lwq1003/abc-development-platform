package tech.abc.platform.support.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 流水号相关异常
 *
 * @author wqliu
 * @date 2023-05-31
 */
@Getter
public enum SerialNoExceptionEnum implements ExceptionInterface {

    /**
     * 流水号编码不存在
     */
    CODE_NOT_EXIST("流水号编码不存在"),

    ;


    private String message;

    SerialNoExceptionEnum(String message) {
        this.message = message;
    }

}
