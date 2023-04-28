package tech.abc.platform.system.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.system.entity.GroupPermissionItem;

import java.util.List;


/**
 * 用户组与权限项对应表 服务类
 *
 * @author wqliu
 * @date 2023-03-08
 */
public interface GroupPermissionItemService extends BaseService<GroupPermissionItem> {

    /**
     * 保存用户组权限项
     *
     * @param groupId              用户组标识
     * @param permissionItemIdList 权限项标识列表
     */
    void savePermission(String groupId, List<String> permissionItemIdList);


}
