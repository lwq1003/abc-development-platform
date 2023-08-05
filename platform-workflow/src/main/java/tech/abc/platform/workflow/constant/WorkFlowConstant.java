package tech.abc.platform.workflow.constant;

/**
 * 工作流常量
 *
 * @author wqliu
 * @date 2020-7-13
 */
public class WorkFlowConstant {

  /**
   * 流程事件-创建
   */
  public static final String EVENT_NAME_CREATE = "create";
  /**
   * 流程事件-委托
   */
  public static final String EVENT_NAME_ASSIGNMENT = "assignment";
  /**
   * 流程事件-完成
   */
  public static final String EVENT_NAME_COMPLETE = "complete";
  /**
   * 流程事件-删除
   */
  public static final String EVENT_NAME_DELETE = "delete";

  /**
   * 流程实例人员设置参数名
   */
  public static final String INSTANCE_APPROVER_LIST = "assigneeList";

  /**
   * 流程实例下一环节参数名
   */
  public static final String INSTANCE_NEXT_STEP = "nextStep";


  /**
   * 流程实例首环节处理人参数名
   */
  public static final String INSTANCE_FIRST_STEP_HANDLER = "firstNodeAssignee";


  /**
   * 任务删除原因-完成（工作流引擎会在任务完成时从运行表中删除，并设置删除原因字段的值到历史表中）
   */
  public static final String TASK_DELETE_REASON_COMPLETE = "completed";


  /**
   * 用户任务参数类型
   */
  public static final String USER_TASK_TYPE_NAME = "userTask";

  /**
   * 回退边标记参数编码
   */
  public static final String REJECT_FLAG_CODE = "reject";


  /**
   * 回退边标记参数值
   */
  public static final String REJECT_FLAG_VALUE = "${reject==true}";

  /**
   * 结束事件（环节）参数类型
   */
  public static final String END_EVENT_TYPE_NAME = "endEvent";


  /**
   * 委派状态-等待
   */
  public static final String DELEGATION_PENDING = "PENDING";

  /**
   * 委派状态-已处理
   */
  public static final String DELEGATION_RESOLVED = "RESOLVED";
}
