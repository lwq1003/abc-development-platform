package tech.abc.platform.workflow.enums;

import lombok.Getter;

/**
 *  流程监听器类别
 * @author wqliu
 */
@Getter
public enum WorkflowListenerCategoryEnum {

    /**
     * 任务监听器
     */
    TASK,
    /**
     * 执行监听器
     */
    EXECUTION,
    ;

}
