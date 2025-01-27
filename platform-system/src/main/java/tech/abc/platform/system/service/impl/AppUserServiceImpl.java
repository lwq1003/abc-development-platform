package tech.abc.platform.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.service.AppUserService;
import tech.abc.platform.system.service.UserService;

import javax.annotation.Resource;

/**
 * 应用用户服务
 *
 * @author wqliu
 * @date 2024-06-25
 */
@Service
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    @Resource
    private UserService userService;


    /**
     * 初始化
     *
     * @param user 用户
     */
    @Override
    public void init(User user) {
        //  默认为空处理
        log.info("执行平台级初始化");

    }

    @Override
    public void deleteAccount() {
        //  默认为空处理
        log.info("执行平台级注销操作");
        // 移除用户
        userService.remove(UserUtil.getId());
    }
}
