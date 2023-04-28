package tech.abc.platform.system.exception;

import tech.abc.platform.common.exception.ExceptionInterface;
import lombok.Getter;

/**
 * 系统参数相关异常
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Getter
public enum ParamExceptionEnum implements ExceptionInterface {
    /**
     * 没有找到该参数
     */
    NOT_EXIST("没有找到该参数"),
    /**
     * 编码已存在
     */
    CODE_EXIST("编码已存在"),
    /**
     * 名称已存在
     */
    NAME_EXIST("名称已存在"),

    ;


    private String message;

    ParamExceptionEnum(String message) {
        this.message = message;
    }

}
