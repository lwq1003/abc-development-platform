package tech.abc.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.system.entity.GroupUser;
import tech.abc.platform.system.mapper.GroupUserMapper;
import tech.abc.platform.system.service.GroupUserService;

import java.util.List;

/**
 * 用户组与用户对应表 服务实现类
 *
 * @author wqliu
 * @date 2021-03-12
 */
@Service
public class GroupUserServiceImpl extends BaseServiceImpl<GroupUserMapper, GroupUser> implements GroupUserService {
    @Override
    public GroupUser init() {
        GroupUser entity = new GroupUser();

        return entity;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveGroup(String userId, List<String> groupIdList) {
        // 先清空用户拥有的所有用户组
        QueryWrapper<GroupUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupUser::getUserId, userId);
        remove(queryWrapper);
        // 再重新插入当前选择角色
        if (CollectionUtils.isNotEmpty(groupIdList)) {
            for (String groupId : groupIdList) {
                GroupUser groupUser = new GroupUser();
                groupUser.setUserId(userId);
                groupUser.setGroupId(groupId);
                add(groupUser);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(String groupId, List<String> userIdList) {
        for (String userId : userIdList) {
            // 判断是否已存在
            QueryWrapper<GroupUser> roleUserQueryWrapper = new QueryWrapper<>();
            roleUserQueryWrapper.lambda().eq(GroupUser::getUserId, userId).eq(GroupUser::getGroupId, groupId);
            long count = count(roleUserQueryWrapper);
            if (count == 0) {
                // 不存在，则新增
                GroupUser groupUser = new GroupUser();
                groupUser.setGroupId(groupId);
                groupUser.setUserId(userId);
                add(groupUser);
            }
        }
    }
}
