package tech.abc.platform.entityconfig.exception;

import tech.abc.platform.common.exception.ExceptionInterface;
import lombok.Getter;

/**
 * 实体视图异常
 *
 * @author wqliu
 * @date 2023-03-28
 */
@Getter
public enum EntityViewException implements ExceptionInterface {

    /**
     * 未找到模型，请先配置模型
     */
    MAIN_REFERENTITY_VIEW_NOT_SET("实体【{0}】未设置主参照视图"),

    ;


    private String message;

    EntityViewException(String message) {
        this.message = message;
    }

}
