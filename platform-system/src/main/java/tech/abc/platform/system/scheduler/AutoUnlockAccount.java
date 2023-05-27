package tech.abc.platform.system.scheduler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import tech.abc.platform.system.constant.SystemConstant;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.enums.UserStatusEnum;
import tech.abc.platform.system.service.ParamService;
import tech.abc.platform.system.service.UserService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 自动解锁账号
 *
 * @author wqliu
 * @date 2023-05-24
 */
@Component
@Slf4j
@DisallowConcurrentExecution
public class AutoUnlockAccount extends QuartzJobBean {

    @Autowired
    private UserService userService;

    @Autowired
    private ParamService paramService;


    @Override
    @SystemLog(value = "解锁用户", logType = LogTypeEnum.SCHEDULER, logRequestParam = false, executeResult = CommonConstant.YES)
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {


        // 获取所有锁定的账户
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getStatus, UserStatusEnum.LOCK.toString());
        List<User> userList = userService.list(userQueryWrapper);
        if (userList != null && !userList.isEmpty()) {
            int lockTime = Integer.parseInt(paramService.getParamValue(SystemConstant.ACCOUNT_UNLOCK_INTERVAL));
            for (User user : userList) {
                long intervalMin = Duration.between(user.getLockTime(), LocalDateTime.now()).toMinutes();
                if (lockTime <= intervalMin) {
                    // 自动解锁用户
                    userService.unlock(user.getId());

                }
            }
        }

    }
}
