package tech.abc.platform.system.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.system.entity.UserProfile;

import java.util.List;
import java.util.Map;

/**
 * 用户配置 服务接口类
 *
 * @author wqliu
 * @date 2023-06-14
 */
public interface UserProfileService extends BaseService<UserProfile> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 获取或初始化
     *
     * @return
     */
    UserProfile getOrInit();
}

