package tech.abc.platform.workflow.enums;

import lombok.Getter;


/**
 * 流程实例状态
 *
 * @author wqliu
 * @date 2023-08-10
 */
@Getter
public enum WorkflowInstanceStatusEnum {

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
