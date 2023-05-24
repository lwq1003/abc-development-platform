package tech.abc.platform.scheduler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.scheduler.exception.SchedulerExceptionEnum;
import tech.abc.platform.scheduler.service.SchedulerService;

import java.util.Map;


/**
 * quartz调度任务实现类
 *
 * @author wqliu
 * @date 2023-05-24
 */
@Service
@Slf4j
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private Scheduler scheduler;


    @Override
    public void add(String jobClassName, String schedulerJobId, String cronExpression,
                    Map<String, String> parameters,
                    String schedulerJobName) {


        // 构建job信息
        JobDataMap jobDataMap = new JobDataMap(parameters);
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(schedulerJobId)
                .usingJobData(jobDataMap)
                .withDescription(schedulerJobName)
                .build();


        // 表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                // 错过的触发合并为一次执行
                .withMisfireHandlingInstructionFireAndProceed();


        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(schedulerJobId)
                .withDescription(schedulerJobName)
                .withSchedule(scheduleBuilder)
                .build();

        try {

            // 添加任务
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new CustomException(SchedulerExceptionEnum.REGISTER_SCHEDULER_JOB);

        }
    }


    @Override
    public void modify(String jobClassName, String schedulerJobId, String cronExpression,
                       Map<String, String> parameters, String schedulerJobName) {

        // 删除任务
        remove(schedulerJobId);
        // 构建job信息
        add(jobClassName, schedulerJobId, cronExpression, parameters, schedulerJobName);
    }


    @Override
    public void remove(String id) {

        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(id));
            scheduler.unscheduleJob(TriggerKey.triggerKey(id));
            scheduler.deleteJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new CustomException(SchedulerExceptionEnum.SCHEDULER_JOB_DELETE_FAILURE);
        }

    }


    @Override
    public void pause(String id) {
        try {

            scheduler.pauseJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new CustomException(SchedulerExceptionEnum.SCHEDULER_JOB_PAUSE_FAILURE);
        }

    }


    @Override
    public void resume(String id) {
        try {
            scheduler.resumeJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new CustomException(SchedulerExceptionEnum.SCHEDULER_JOB_RESUME_FAILURE);
        }

    }


    @Override
    public void pauseAll() {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new CustomException(SchedulerExceptionEnum.SCHEDULER_JOB_PAUSE_FAILURE);
        }

    }


    @Override
    public void resumeAll() {
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new CustomException(SchedulerExceptionEnum.SCHEDULER_JOB_RESUME_FAILURE);
        }

    }


    @Override
    public void execute(String id) {
        try {
            scheduler.triggerJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            throw new CustomException(SchedulerExceptionEnum.EXECUTE_ERROR);
        }

    }


    private static Job getClass(String className) {
        try {
            Class<?> jobClass = Class.forName(className);
            return (Job) jobClass.newInstance();
        } catch (Exception ex) {
            log.error("未找到任务类", ex);
            throw new CustomException(CommonException.CLASS_NOT_FOUND);
        }

    }


}
