package tech.abc.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.system.entity.UserProfile;
import tech.abc.platform.system.mapper.UserProfileMapper;
import tech.abc.platform.system.service.UserProfileService;
import org.springframework.stereotype.Service;

/**
 * 用户配置 服务实现类
 *
 * @author wqliu
 * @date 2020-05-25
 */
@Service
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfileMapper, UserProfile> implements UserProfileService {
    @Override
    public UserProfile init() {
        UserProfile entity = new UserProfile();
        entity.setTimeZone("UTC+8");
        entity.setLanguage("zh_CN");
        return entity;
    }


    @Override
    public UserProfile getOrInit() {
        // 获取当前用户id
        String userId = UserUtil.getId();
        // 查询是否存在配置记录
        QueryWrapper<UserProfile> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserProfile::getUserId, userId);
        UserProfile entity = getOne(queryWrapper);
        if (entity == null) {
            // 不存在则初始化
            entity = init();
            entity.setUserId(userId);
            // 添加到库表
            add(entity);
        }
        return entity;
    }
}
