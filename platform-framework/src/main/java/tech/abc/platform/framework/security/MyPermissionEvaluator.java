package tech.abc.platform.framework.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.framework.config.PlatformConfig;
import tech.abc.platform.system.entity.PermissionItem;
import tech.abc.platform.system.exception.PermissionItemExceptionEnum;
import tech.abc.platform.system.service.PermissionItemService;
import tech.abc.platform.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 自定义权限表达式
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Component
public class MyPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private PermissionItemService permissionItemService;

    @Autowired
    private PlatformConfig appConfig;

    @Autowired
    private UserService userService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        return hasPermission(authentication, permission);
    }


    private boolean hasPermission(Authentication authentication, Object permission) {

        if (appConfig.getSystem().isEnablePermission()) {
            // 首先判断该资源
            QueryWrapper<PermissionItem> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(PermissionItem::getPermissionCode, permission.toString());
            PermissionItem entity = permissionItemService.getOne(queryWrapper);
            // 不存在
            if (entity == null) {
                throw new CustomException(PermissionItemExceptionEnum.NOT_EXIST);
            }
            // 停用
            if (entity.getStatus().equals(StatusEnum.DEAD.name())) {
                throw new CustomException(PermissionItemExceptionEnum.STATUS_IS_DEAD);
            }

            // 权限验证
            userService.checkPermission(UserUtil.getId(), entity.getPermissionCode());
            return true;

        } else {
            return true;
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
