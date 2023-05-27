package tech.abc.platform.scheduler.listener;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

/**
 * 任务监听器
 *
 * @author wqliu
 * @date: 2020-04-03 11:23
 */
@Slf4j
@Data
public class MyJobListener extends JobListenerSupport {

    private String name;

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {


    }
}
