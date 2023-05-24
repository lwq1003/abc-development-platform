package tech.abc.platform.scheduler.listener;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

/**
 * 触发器监听器
 *
 * @author wqliu
 * @date: 2020-04-03 11:23
 */
@Slf4j
@Data
public class MyTriggerListener extends TriggerListenerSupport {

    private String name;

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {

    }
}
