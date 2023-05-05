package tech.abc.platform.cip.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * 应用相关异常
 *
 * @author wqliu
 * @date 2020-03-29
 */
@Getter
public enum AppExceptionEnum implements ExceptionInterface {

    /**
     * 应用编码有误或密钥错误
     */
    APP_CODE_OR_SECRET_ERROR("应用编码有误或密钥错误"),
    /**
     * 应用编码不存在
     */
    APP_CODE_NOT_EXIST("应用编码不存在"),
    ;
    private String message;

    AppExceptionEnum(String message) {
        this.message = message;
    }

}
