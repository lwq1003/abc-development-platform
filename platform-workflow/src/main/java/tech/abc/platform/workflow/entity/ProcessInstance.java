package tech.abc.platform.workflow.entity;


import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 流程实例 实体对象
 * @author wqliu
 * @date 2020-09-18
 *
 */
@Data
public class ProcessInstance extends BaseEntity {

    /**
     * 单据号
     */
    private String businessNo;

    /**
     * 流程定义标识
     */
    private String processDefinitionId;
    /**
     * 流程编码
     */
    private String processDefinitionKey;
    /**
     * 流程类型
     */
    private String processDefinitionName;
    /**
     * 流程启动时间
     */
    private LocalDateTime startTime;
    /**
     * 流程结束时间
     */
    private LocalDateTime endTime;
    /**
     * 流程状态
     */
    private String state;
    /**
     * 流程申请人标识
     */
    private String processApplyId;
    /**
     * 流程申请人姓名
     */
    private String processApplyName;
    /**
     * 当前环节名称
     */
    private String nodeName;
    /**
     * 环节处理人标识
     */
    private String nodeAssignee;
    /**
     * 环节处理人姓名
     */
    private String nodeAssigneeName;

}
