package tech.abc.platform.cip.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


/**
 * API服务相关异常
 *
 * @author wqliu
 * @date: 2022-02-11
 */
@Getter
public enum ApiServiceExceptionEnum implements ExceptionInterface {

    /**
     * API服务不存在
     */
    API_SERVICE_NOT_EXIST("API服务不存在"),
    /**
     * 该API服务未设置处理器
     */
    API_SERVICE_NOT_SET_HANDLER("该API服务未设置处理器");

    private String message;

    ApiServiceExceptionEnum(String message) {
        this.message = message;
    }

}
