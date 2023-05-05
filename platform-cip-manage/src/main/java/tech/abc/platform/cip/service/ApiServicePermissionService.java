package tech.abc.platform.cip.service;

import tech.abc.platform.cip.entity.ApiServicePermission;
import tech.abc.platform.common.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * API服务权限 服务接口类
 *
 * @author wqliu
 * @date 2023-05-03
 */
public interface ApiServicePermissionService extends BaseService<ApiServicePermission> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 是否有权限
     *
     * @param appCode     应用程序代码
     * @param serviceCode 服务代码
     * @return boolean
     */
    boolean hasPermission(String appCode, String serviceCode);

}

