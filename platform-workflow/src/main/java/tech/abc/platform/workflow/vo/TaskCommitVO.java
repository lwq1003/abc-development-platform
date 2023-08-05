package tech.abc.platform.workflow.vo;

import lombok.Data;

import java.util.List;


/**
 * 任务提交 视图对象
 *
 * @author wqliu
 * @date 2023-07-04
 */
@Data
public class TaskCommitVO {

  /**
   * 任务标识
   */
  private String taskId;

  /**
   * 环节名称
   */
  private String stepName;

  /**
   * 流程实例标识
   */
  private String processInstanceId;

  /**
   * 审批意见
   */
  private String comment;

  /**
   * 下一环节标识
   */
  private String nextStepId;

  /**
   * 处理人
   */
  private List<String> assigneeList;

}
