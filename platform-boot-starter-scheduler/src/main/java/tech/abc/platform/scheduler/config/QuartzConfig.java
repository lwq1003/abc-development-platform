package tech.abc.platform.scheduler.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.EverythingMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import tech.abc.platform.scheduler.listener.MyJobListener;
import tech.abc.platform.scheduler.listener.MySchedulerListener;
import tech.abc.platform.scheduler.listener.MyTriggerListener;

/**
 * 任务调度配置类
 *
 * @author wqliu
 * @date 2020-03-28 16:59
 */
@Configuration
@Slf4j
public class QuartzConfig implements ApplicationRunner {
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private MySchedulerListener mySchedulerListener;

    @Override
    public void run(ApplicationArguments args) throws SchedulerException {


        // 添加调度引擎监听器
        scheduler.getListenerManager().addSchedulerListener(mySchedulerListener);

        // 添加任务监听器
        MyJobListener jobListener = new MyJobListener();
        jobListener.setName("全局任务监听器");
        scheduler.getListenerManager().addJobListener(jobListener, EverythingMatcher.allJobs());
        // 添加触发器监听器
        MyTriggerListener triggerListener = new MyTriggerListener();
        triggerListener.setName("全局触发监听器");
        scheduler.getListenerManager().addTriggerListener(triggerListener, EverythingMatcher.allTriggers());


    }


}
