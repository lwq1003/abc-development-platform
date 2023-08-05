package tech.abc.platform.workflow.vo;

import lombok.Data;

import java.util.List;


/**
 * 审批 视图对象
 *
 * @author wqliu
 * @date 2023-07-04
 */
@Data
public class ApprovalVO {
    /**
     *  任务标识
     */
    private String taskId;
    /**
     *  环节标识
     */
    private String nodeId;

    /**
     *  流程实例标识
     */
    private String processInstanceId;

    /**
     *  办理人
     */
    private List<String> assigneeList;


    /**
     *  处理意见
     */
    private String comment;


}
