package tech.abc.platform.entityconfig.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;

/**
 * 实体m模型异常
 *
 * @author wqliu
 * @date 2023-03-28
 */
@Getter
public enum EntityModelException implements ExceptionInterface {

    /**
     * 实体模型未定义流水号配置，请确认
     */
    SERIAL_CONFIG_NOT_FOUND("实体模型【{0}】未定义流水号配置，请确认"),
    /**
     * 实体数据模型基类未配置，请确认
     */
    PARENT_MODE_NOT_CONFIG("实体数据模型【{0}】基类未配置，请确认"),
    ;


    private String message;

    EntityModelException(String message) {
        this.message = message;
    }

}
