package tech.abc.platform.workflow.enums;

import lombok.Getter;


/**
 * 流程节点类型
 *
 * @author wqliu
 * @date 2023-07-12
 */
@Getter
public enum FlowCodeTypeEnum {

    /**
     * 开始环节
     */
    START,

    /**
     * 发起环节
     */
    ROOT,


    /**
     * 办理环节
     */
    HANDLE,

    /**
     * 条件路由
     */
    INCLUSIVE_GATEWAY,


    /**
     * 条件节点
     */
    CONDITION,

    /**
     * 服务任务
     */
    SERVICE_TASK,

    /**
     * 结束环节
     */
    END,
    ;
}
