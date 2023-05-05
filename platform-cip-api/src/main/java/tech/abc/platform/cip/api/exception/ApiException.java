package tech.abc.platform.cip.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口服务异常
 *
 * @author wqliu
 * @date 2021-8-19
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ApiException extends RuntimeException {
    /**
     * 错误编码
     */
    private String errorCode;

    public ApiException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

}
