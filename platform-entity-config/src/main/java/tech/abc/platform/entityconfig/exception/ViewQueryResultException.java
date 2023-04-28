package tech.abc.platform.entityconfig.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;

/**
 * 视图查询结果异常
 * @author wqliu
 * @date 2022-11-19
 */
@Getter
public enum ViewQueryResultException implements ExceptionInterface {

    /**
     * 该查询条件已存在，请勿重复添加
     */
    HAD_EXIST("该属性已存在，请勿重复添加"),

    ;


    private String message;

    ViewQueryResultException(String message) {
        this.message = message;
    }

}
