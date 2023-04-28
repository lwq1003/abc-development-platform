package tech.abc.platform.common.exception;


import lombok.Data;

import java.text.MessageFormat;

/**
 * 自定义异常
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class CustomException extends RuntimeException {

    /**
     * 继承exception
     */
    public CustomException(ExceptionInterface exceptionInterface) {
        super(exceptionInterface.getMessage());
    }

    /**
     * 错误信息带参数
     */
    public CustomException(ExceptionInterface exceptionInterface, Object... args) {
        super(MessageFormat.format(exceptionInterface.getMessage(), args));

    }
}
