package tech.abc.platform.workflow.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 流程任务 视图对象
 *
 * @author wqliu
 * @date 2023-07-04
 */
@Data
public class ProcessTaskVO {


    /**
     * 任务标识
     */
    private String id;


    /**
     * 环节名称
     */
    private String nodeName;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    /**
     * 环节处理人标识
     */
    private String assignee;


    /**
     * 环节处理人姓名
     */
    private String assigneeName;

    /**
     * 任务拥有人
     */
    private String owner;

    /**
     * 流程定义标识
     */
    private String processDefinitionId;

    /**
     * 流程实例标识
     */
    private String processInstanceId;

    /**
     * 流程申请人标识
     */
    private String processApplyId;

    /**
     * 流程申请人姓名
     */
    private String processApplyName;

    /**
     * 任务定义标识
     */
    private String taskDefinitionKey;


    /**
     * 流程编码
     */
    private String processDefinitionKey;


    /**
     * 流程类型
     */
    private String processDefinitionName;

    /**
     * 单据号
     */
    private String businessNo;

    /**
     * 委派状态
     */
    private String delegation;


    /**
     * 候选人
     */
    private String candidateUser;


    /**
     * 执行流标识
     */
    private String executionId;

    /**
     * 创建时间起始
     */
    private LocalDateTime createTimeBeginForQuery;

    /**
     * 创建时间结束
     */
    private LocalDateTime createTimeEndForQuery;



}
