package tech.abc.platform.system.service;

import tech.abc.platform.system.entity.User;

/**
 * 应用用户服务接口
 *
 * @author wqliu
 * @date 2024-06-25
 */
public interface AppUserService {


    /**
     * 初始化
     *
     * @param user 用户
     */
    void init(User user);


    /**
     * 注销账号
     */
    void deleteAccount();


    /**
     * 找回密码
     */
    void retrievePassword(String email);
}
