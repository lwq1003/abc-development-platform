package tech.abc.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.system.entity.UserProfile;
import tech.abc.platform.system.mapper.UserProfileMapper;
import tech.abc.platform.system.service.UserProfileService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户配置 服务实现类
 *
 * @author wqliu
 * @date 2023-06-14
 */
@Service
@Slf4j
public class UserProfileServiceImpl extends BaseServiceImpl<UserProfileMapper, UserProfile> implements UserProfileService {
    @Override
    public UserProfile init() {
        UserProfile entity = new UserProfile();
        // 默认值处理
        entity.setLanguage("zh_CN");
        entity.setTimeZone("BEIJING");
        return entity;
    }

    @Override
    public void beforeAdd(UserProfile entity) {
        // 唯一性验证
        // 验证 用户 全局唯一
        if (StringUtils.isNotBlank(entity.getUser())) {
            long countUser = this.lambdaQuery().eq(UserProfile::getUser, entity.getUser()).count();
            if (countUser > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【用户】");
            }
        }
    }

    @Override
    public void beforeModify(UserProfile entity) {
        // 唯一性验证
        // 验证 用户 全局唯一
        if (StringUtils.isNotBlank(entity.getUser())) {
            long countUser = this.lambdaQuery().eq(UserProfile::getUser, entity.getUser())
                    .ne(UserProfile::getId, entity.getId()).count();
            if (countUser > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【用户】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<UserProfile> list = this.lambdaQuery().in(UserProfile::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getUser());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(UserProfile entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setUser(entity.getUser() + " 副本");
    }

    @Override
    public UserProfile getOrInit() {
        // 获取当前用户id
        String userId = UserUtil.getId();
        // 查询是否存在配置记录
        QueryWrapper<UserProfile> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserProfile::getUser, userId);
        UserProfile entity = getOne(queryWrapper);
        if (entity == null) {
            // 不存在则初始化
            entity = init();
            entity.setUser(userId);
            // 添加到库表
            add(entity);
        }
        return entity;
    }
}

