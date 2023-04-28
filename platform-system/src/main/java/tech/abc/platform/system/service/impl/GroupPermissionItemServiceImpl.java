package tech.abc.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.system.entity.GroupPermissionItem;
import tech.abc.platform.system.mapper.GroupPermissionItemMapper;
import tech.abc.platform.system.service.GroupPermissionItemService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户组与权限项对应表 服务实现类
 *
 * @author wqliu
 * @date 2021-03-12
 */
@Service
public class GroupPermissionItemServiceImpl extends BaseServiceImpl<GroupPermissionItemMapper, GroupPermissionItem> implements GroupPermissionItemService {
    @Override
    public GroupPermissionItem init() {
        GroupPermissionItem entity = new GroupPermissionItem();

        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePermission(String groupId, List<String> permissionItemIdList) {
        // 先清空组拥有的所有权限
        QueryWrapper<GroupPermissionItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupPermissionItem::getGroupId, groupId);
        remove(queryWrapper);
        // 再重新插入当前选择的权限
        if (CollectionUtils.isNotEmpty(permissionItemIdList)) {
            for (String permissionItemId : permissionItemIdList) {
                GroupPermissionItem groupPermissionItem = new GroupPermissionItem();
                groupPermissionItem.setGroupId(groupId);
                groupPermissionItem.setPermissionItemId(permissionItemId);
                add(groupPermissionItem);
            }
        }
    }
}
