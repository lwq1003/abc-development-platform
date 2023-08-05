package tech.abc.platform.workflow.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 流程实例 视图对象
 *
 * @author wqliu
 * @date 2023-07-04
 */
@Data
public class ProcessInstanceVO {

    /**
     * 流程实例标识
     */
    private String id;

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
     * 环节名称
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


    /**
     * 启动时间起始
     */
    private LocalDateTime startTimeBeginForQuery;

    /**
     * 启动时间截止
     */
    private LocalDateTime startTimeEndForQuery;

    /**
     * 流程状态名称
     */
    private String stateName;

}
