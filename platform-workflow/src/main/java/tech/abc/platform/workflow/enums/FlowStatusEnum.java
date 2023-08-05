package tech.abc.platform.workflow.enums;

import lombok.Getter;

/**
 * 用户状态
 * @author wqliu
 */
@Getter
public enum FlowStatusEnum {

    /**
     * 运行
     */
    ACTIVE,
    /**
     * 挂起
     */
    SUSPENDED,
    /**
     * 结束
     */
    COMPLETED,
    ;


}
