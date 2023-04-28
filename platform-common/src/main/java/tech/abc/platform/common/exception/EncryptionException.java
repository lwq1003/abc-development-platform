package tech.abc.platform.common.exception;

import lombok.Getter;

/**
 * 加密解密异常类
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Getter
public enum EncryptionException implements ExceptionInterface {
    /**
     * 加密失败
     */
    ENCRYPT_FAILED("加密失败"),
    /**
     * 解密失败
     */
    DECRYPT_FAILED("解密失败"),

    ;


    private String message;

    EncryptionException(String message) {
        this.message = message;
    }

}