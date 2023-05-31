package tech.abc.platform.support.scheduler;


import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.constant.CommonConstant;
import tech.abc.platform.common.enums.LogTypeEnum;
import tech.abc.platform.support.service.SerialNoService;


/**
 * 重置流水号
 *
 * @author wqliu
 * @date 2023-05-30
 */
@Component
@Slf4j
@DisallowConcurrentExecution
public class ResetSerialNo extends QuartzJobBean {

    @Autowired
    private SerialNoService serialNoService;


    @Override
    @SystemLog(value = "重置流水号", logType = LogTypeEnum.SCHEDULER, logRequestParam = false, executeResult =
            CommonConstant.NO)
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {
            serialNoService.resetSerialNo();
        } catch (Exception e) {
            log.error("重置流水号失败", e);
            throw new JobExecutionException(e);
        }

    }


}
