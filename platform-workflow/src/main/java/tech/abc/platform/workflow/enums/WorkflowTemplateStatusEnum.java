package tech.abc.platform.workflow.enums;

import lombok.Getter;


/**
 * 工作流程模板状态
 *
 * @author wqliu
 * @date 2023-08-10
 */
@Getter
public enum WorkflowTemplateStatusEnum {

    /**
     * 未发布
     */
    UNPUBLISHED,
    /**
     * 运行中
     */
    RUNNING,
    /**
     * 已归档
     */
    ARCHIVED,
    ;


}
