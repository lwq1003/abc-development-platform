package tech.abc.platform.scheduler.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;

/**
 * 任务调度模块异常
 *
 * @author wqliu
 * @date: 2020-03-29 19:11
 */
@Getter
public enum SchedulerExceptionEnum implements ExceptionInterface {

    /**
     * 注册任务出错
     */
    REGISTER_SCHEDULER_JOB("注册任务出错"),
    /**
     * 任务名称已存在
     */
    JOB_NAME_EXIST("任务名称已存在"),
    /**
     * 任务已在调度中使用
     */
    JOB_HAS_SCHEDULER("任务已在调度中使用"),
    /**
     * 调度任务名称已存在
     */
    SCHEDULER_JOB_NAME_EXIST("调度任务名称已存在"),
    /**
     * 调度任务已在调度中使用
     */
    SCHEDULER_JOB_HAS_SCHEDULER("调度任务已在调度中使用"),
    /**
     * 调度任务移除失败
     */
    SCHEDULER_JOB_DELETE_FAILURE("调度任务移除失败"),
    /**
     * 调度任务暂停失败
     */
    SCHEDULER_JOB_PAUSE_FAILURE("调度任务暂停失败"),
    /**
     * 调度任务恢复失败
     */
    SCHEDULER_JOB_RESUME_FAILURE("调度任务恢复失败"),
    /**
     * 当前任务已经是暂停状态
     */
    SCHEDULER_JOB_IS_PAUSED("当前任务已经是暂停状态"),
    /**
     * 当前任务已经是运行状态
     */
    SCHEDULER_JOB_IS_RUNNING("当前任务已经是运行状态"),
    /**
     * cron表达式不正确
     */
    CRON_EXPRESSION_ERROR("cron表达式不正确:{0}"),
    /**
     * 执行失败
     */
    EXECUTE_ERROR("执行失败"),
    ;


    private String message;

    SchedulerExceptionEnum(String message) {
        this.message = message;
    }

}
