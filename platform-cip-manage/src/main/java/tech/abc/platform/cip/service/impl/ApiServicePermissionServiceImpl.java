package tech.abc.platform.cip.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.entity.ApiServicePermission;
import tech.abc.platform.cip.mapper.ApiServicePermissionMapper;
import tech.abc.platform.cip.service.ApiServicePermissionService;
import tech.abc.platform.cip.service.ApiServiceService;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.common.base.BaseServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API服务权限 服务实现类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Service
@Slf4j
public class ApiServicePermissionServiceImpl extends BaseServiceImpl<ApiServicePermissionMapper, ApiServicePermission> implements ApiServicePermissionService {

    @Autowired
    private ApiServiceService apiServiceService;

    @Autowired
    private AppService appService;

    @Override
    public ApiServicePermission init() {
        ApiServicePermission entity = new ApiServicePermission();
        // 默认值处理
        return entity;
    }

    @Override
    public void beforeAdd(ApiServicePermission entity) {
        // 唯一性验证
    }

    @Override
    public void beforeModify(ApiServicePermission entity) {
        // 唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<ApiServicePermission> list = this.lambdaQuery().in(ApiServicePermission::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getApp());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(ApiServicePermission entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setApp(entity.getApp() + " 副本");
    }

    @Override
    public boolean hasPermission(String appCode, String serviceCode) {

        // 获取应用标识
        String appId = appService.getByCode(appCode).getId();
        // 获取服务标识
        String serviceId = apiServiceService.getByCode(serviceCode).getId();

        List<ApiServicePermission> list = this.lambdaQuery()
                .eq(ApiServicePermission::getApp, appId)
                .eq(ApiServicePermission::getApiService, serviceId)
                .list();
        if (CollectionUtils.isNotEmpty(list)) {
            return true;
        }

        return false;
    }
}