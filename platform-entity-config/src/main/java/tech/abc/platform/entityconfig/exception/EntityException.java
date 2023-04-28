package tech.abc.platform.entityconfig.exception;

import tech.abc.platform.common.exception.ExceptionInterface;
import lombok.Getter;

/**
 * 实体异常
 *
 * @author wqliu
 * @date 2023-03-28
 */
@Getter
public enum EntityException implements ExceptionInterface {

    /**
     * 未找到模型，请先配置模型
     */
    MODEL_NOT_FOUND("实体【{0}】未找到模型，请先配置模型"),

    ;


    private String message;

    EntityException(String message) {
        this.message = message;
    }

}
