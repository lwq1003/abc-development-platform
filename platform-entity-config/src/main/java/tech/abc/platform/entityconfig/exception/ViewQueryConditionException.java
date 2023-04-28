package tech.abc.platform.entityconfig.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;

/**
 * 视图查询区域异常
 * @author wqliu
 * @date 2022-11-19
 */
@Getter
public enum ViewQueryConditionException implements ExceptionInterface {

    /**
     * 该查询条件已存在，请勿重复添加
     */
    HAD_EXIST("该条件已存在，请勿重复添加"),

    ;


    private String message;

    ViewQueryConditionException(String message) {
        this.message = message;
    }

}
