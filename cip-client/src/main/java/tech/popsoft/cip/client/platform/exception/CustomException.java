package tech.popsoft.cip.client.platform.exception;


import java.text.MessageFormat;

/**
 * 自定义异常
 *
 * @author wqliu
 */
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
