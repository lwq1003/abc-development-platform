package tech.abc.platform.common.exception;


import lombok.Data;

/**
 * 会话超时异常
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class SessionExpiredException extends RuntimeException {

    public SessionExpiredException(String message) {
        super(message);
    }


}
