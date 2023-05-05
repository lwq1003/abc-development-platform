package tech.abc.platform.cip.service;

import tech.abc.platform.cip.entity.AppDataPermission;
import tech.abc.platform.common.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 应用数据权限 服务接口类
 *
 * @author wqliu
 * @date 2023-05-03
 */
public interface AppDataPermissionService extends BaseService<AppDataPermission> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 根据角色编码获取数据权限信息
     *
     * @param roleCode 角色编码
     * @param appCode  应用编码
     * @return 数据权限列表
     */
    List<AppDataPermission> getPermissionByRoleCode(String roleCode, String appCode);

}

