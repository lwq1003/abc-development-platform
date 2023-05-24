package tech.abc.platform.scheduler.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.quartz.listeners.SchedulerListenerSupport;
import org.springframework.stereotype.Component;

/**
 * 调度引擎监听器
 *
 * @author wqliu
 * @date: 2020-04-03 11:23
 */
@Slf4j
@Component("schedulerListener")
public class MySchedulerListener extends SchedulerListenerSupport {

    @Override
    public void schedulerError(String s, SchedulerException e) {
        log.error(s, e);
    }

    @Override
    public void schedulerStarted() {

        //启动顺序的关系，该日志实际不会输出
        log.info("调度引擎启动");
    }

    @Override
    public void schedulerShutdown() {
        log.info("调度引擎关闭");

    }
}
