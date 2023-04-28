package tech.abc.platform.system.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.system.entity.UserProfile;


/**
 * 用户配置 服务类
 *
 * @author wqliu
 * @date 2023-03-08
 */
public interface UserProfileService extends BaseService<UserProfile> {

    /**
     * 获取或初始化
     *
     * @return
     */
    UserProfile getOrInit();
}
