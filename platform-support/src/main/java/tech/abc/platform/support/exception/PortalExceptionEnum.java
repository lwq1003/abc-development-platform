package tech.abc.platform.support.exception;

import tech.abc.platform.common.exception.ExceptionInterface;
import lombok.Getter;

/**
 * 门户及组件相关异常
 * @author wqliu
 * @date: 2020-03-29 19:11
 *
 */
@Getter
public enum PortalExceptionEnum implements ExceptionInterface {

    /**
     * 组件参数错误
     */
    PORTLET_PARAM_ERROR("【{0}】组件参数错误，{1}"),

    ;


    private String message;
    PortalExceptionEnum(String message){
        this.message = message;
    }

}
