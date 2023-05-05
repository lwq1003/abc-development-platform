package tech.abc.platform.cip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.entity.App;
import tech.abc.platform.cip.entity.AppDataPermission;
import tech.abc.platform.cip.exception.AppDataPermissionExceptionEnum;
import tech.abc.platform.cip.mapper.AppDataPermissionMapper;
import tech.abc.platform.cip.service.AppDataPermissionService;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用数据权限 服务实现类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Service
@Slf4j
public class AppDataPermissionServiceImpl extends BaseServiceImpl<AppDataPermissionMapper, AppDataPermission> implements AppDataPermissionService {

    @Autowired
    private AppService appService;

    @Override
    public AppDataPermission init() {
        AppDataPermission entity = new AppDataPermission();
        // 默认值处理
        entity.setRoleCode("");
        return entity;
    }

    @Override
    protected void beforeAdd(AppDataPermission entity) {
        // 验证不同的应用，不能数据权限角色和业务编码完全一样
        QueryWrapper<AppDataPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AppDataPermission::getRoleCode, entity.getRoleCode())
                .eq(AppDataPermission::getBusinessCode, entity.getBusinessCode())
                .ne(AppDataPermission::getApp, entity.getApp());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(AppDataPermissionExceptionEnum.DATA_ROLE_AND_BUSINESS_CODE_EXIST);
        }

        // 验证是否存在所有属性一致的记录
        queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(AppDataPermission::getApp, entity.getApp())
                .eq(AppDataPermission::getRoleCode, entity.getRoleCode())
                .eq(AppDataPermission::getBusinessCode, entity.getBusinessCode());
        // 只有不为空的时候再比较，不加该条件，数据库中为null，前端传过来的是空串，比较会失败
        if (StringUtils.isNotBlank(entity.getSecondBusinessCode())) {
            queryWrapper.lambda().eq(AppDataPermission::getSecondBusinessCode, entity.getSecondBusinessCode());
        }

        count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.RECORD_EXIST);
        }
    }

    @Override
    protected void beforeModify(AppDataPermission entity) {
        // 验证不同的应用，不能数据权限角色和业务编码完全一样
        QueryWrapper<AppDataPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AppDataPermission::getRoleCode, entity.getRoleCode())
                .eq(AppDataPermission::getBusinessCode, entity.getBusinessCode())
                .ne(AppDataPermission::getApp, entity.getApp())
                .ne(AppDataPermission::getId, entity.getId());
        long count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(AppDataPermissionExceptionEnum.DATA_ROLE_AND_BUSINESS_CODE_EXIST);
        }

        // 验证是否存在所有属性一致的记录
        queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(AppDataPermission::getApp, entity.getApp())
                .eq(AppDataPermission::getRoleCode, entity.getRoleCode())
                .eq(AppDataPermission::getBusinessCode, entity.getBusinessCode())
                .eq(AppDataPermission::getSecondBusinessCode, entity.getSecondBusinessCode())
                .ne(AppDataPermission::getId, entity.getId());
        count = count(queryWrapper);
        if (count > 0) {
            throw new CustomException(CommonException.RECORD_EXIST);
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<AppDataPermission> list = this.lambdaQuery().in(AppDataPermission::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getApp());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(AppDataPermission entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setApp(entity.getApp() + " 副本");
    }

    @Override
    public List<AppDataPermission> getPermissionByRoleCode(String roleCode, String appCode) {
        // 通过应用编码获取应用标识
        App app = appService.getByCode(appCode);
        String appId = app.getId();
        // 查找满足条件的数据
        List<AppDataPermission> list = this.lambdaQuery().eq(AppDataPermission::getRoleCode, roleCode)
                .eq(AppDataPermission::getApp, appId).list();
        return list;
    }

}

