package tech.abc.platform.scheduler.config;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * 扩展SchedulerFactoryBean
 *
 * @author wqliu
 * @date 2020-03-28 8:28
 * : SpringBoot的QuartzAutoConfiguration类会自动配置SchedulerFactoryBean
 */
@Component
public class MySchedulerFactoryBean implements SchedulerFactoryBeanCustomizer {


    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        // 更新trigger的 表达式时，同步数据到数据库qrtz_cron_triggers表 开启
        // TODO:待确认下面语句是否有用，以及是否需要其他配置
        schedulerFactoryBean.setOverwriteExistingJobs(true);

        // TODO：为加快其他模块开发和调试速度，临时将quartz设置为非自动启动
        schedulerFactoryBean.setAutoStartup(true);

    }

}