package tech.abc.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.system.entity.GroupPermissionItem;
import tech.abc.platform.system.entity.UserGroup;
import tech.abc.platform.system.mapper.UserGroupMapper;
import tech.abc.platform.system.service.GroupPermissionItemService;
import tech.abc.platform.system.service.UserGroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户组 服务实现类
 *
 * @author wqliu
 * @date 2023-04-24
 */
@Service
@Slf4j
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {

    @Autowired
    private GroupPermissionItemService groupPermissionItemService;


    @Override
    public UserGroup init() {
        UserGroup entity = new UserGroup();
        // 默认值处理
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(UserGroup entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(UserGroup::getName, entity.getName())
                    .eq(UserGroup::getUserGroup, entity.getUserGroup()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(UserGroup::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(UserGroup entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(UserGroup::getName, entity.getName())
                    .eq(UserGroup::getUserGroup, entity.getUserGroup())
                    .ne(UserGroup::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(UserGroup::getCode, entity.getCode())
                    .ne(UserGroup::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }


    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<UserGroup> list = this.lambdaQuery().in(UserGroup::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(UserGroup entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

    @Override
    public void beforeRemove(UserGroup entity) {
        // 移除用户组对应的权限
        QueryWrapper<GroupPermissionItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupPermissionItem::getGroupId, entity.getId());
        groupPermissionItemService.remove(queryWrapper);
    }

    @Override
    public void enable(String id) {
        UserGroup entity = getEntity(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        UserGroup entity = getEntity(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }

}

