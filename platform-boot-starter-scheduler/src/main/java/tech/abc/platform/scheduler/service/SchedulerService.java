package tech.abc.platform.scheduler.service;

import java.util.Map;


/**
 * 任务调度引擎服务定义
 *
 * @author wqliu
 * @date 2023-05-24
 */
public interface SchedulerService {

    /**
     * 新增任务
     *
     * @param jobClassName     任务类名
     * @param schedulerJobId   任务标识
     * @param cronExpression   cron表达式
     * @param parameters       参数
     * @param schedulerJobName 调度任务名
     */
    void add(String jobClassName, String schedulerJobId, String cronExpression,
             Map<String, String> parameters,
             String schedulerJobName);


    /**
     * 移除任务
     *
     * @param id 任务标识
     */
    void remove(String id);

    /**
     * 修改任务
     *
     * @param jobClassName     任务类名
     * @param schedulerJobId   任务标识
     * @param cronExpression   cron表达式
     * @param parameters       参数
     * @param schedulerJobName 调度任务名
     */
    void modify(String jobClassName, String schedulerJobId, String cronExpression,
                Map<String, String> parameters,
                String schedulerJobName);


    /**
     * 暂停任务
     *
     * @param id 任务标识
     */
    void pause(String id);

    /**
     * 恢复任务
     *
     * @param id 任务标识
     */
    void resume(String id);

    /**
     * 暂停所有任务
     */
    void pauseAll();

    /**
     * 恢复所有任务
     */
    void resumeAll();

    /**
     * 执行任务
     *
     * @param id 任务标识
     */
    void execute(String id);
}