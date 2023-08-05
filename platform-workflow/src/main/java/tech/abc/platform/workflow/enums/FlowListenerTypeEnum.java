package tech.abc.platform.workflow.enums;

import lombok.Getter;

/**
 *  流程监听器类类型
 * @author wqliu
 */
@Getter
public enum FlowListenerTypeEnum {

    /**
     * 类
     */
    CLASS,
    /**
     * 表达式
     */
    EXPRESSION,
    /**
     * 委托类
     */
    DELEGATE_EXPRESSION,
    ;

}
