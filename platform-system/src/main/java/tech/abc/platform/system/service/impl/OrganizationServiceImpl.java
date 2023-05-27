package tech.abc.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.notification.service.SystemMessageService;
import tech.abc.platform.system.entity.Organization;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.exception.OrganizationExceptionEnum;
import tech.abc.platform.system.mapper.OrganizationMapper;
import tech.abc.platform.system.service.OrganizationService;
import tech.abc.platform.system.service.UserService;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织机构 服务实现类
 *
 * @author wqliu
 * @date 2023-04-19
 */
@Service
@Slf4j
public class OrganizationServiceImpl extends BaseServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemMessageService systemMessageService;

    @Override
    public Organization init() {
        Organization entity = new Organization();
        // 默认值处理
        entity.setType("DEPARTMENT");
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(Organization entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(Organization::getName, entity.getName())
                .eq(Organization::getOrganization, entity.getOrganization()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 全局唯一
        long countCode = this.lambdaQuery().eq(Organization::getCode, entity.getCode()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
        }
    }

    @Override
    public void beforeModify(Organization entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(Organization::getName, entity.getName())
                .eq(Organization::getOrganization, entity.getOrganization())
                .ne(Organization::getId, entity.getId()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 全局唯一
        long countCode = this.lambdaQuery().eq(Organization::getCode, entity.getCode())
                .ne(Organization::getId, entity.getId()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Organization> list = this.lambdaQuery().in(Organization::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void afterModify(Organization entity, Organization orginEntity) {
        // TODO 测试数据，当组织机构名称变更后，发送系统通知
        if (!entity.getName().equals(orginEntity.getName())) {
            String content = MessageFormat.format("{0}变更为{1}", orginEntity.getName(), entity.getName());
            systemMessageService.sendMessage("admin", "组织机构修改", content);
        }

    }

    @Override
    public void beforeRemove(Organization entity) {
        // 验证是否有下级
        if (super.lambdaQuery().eq(Organization::getOrganization, entity.getId()).count() > 0) {
            throw new CustomException(CommonException.HAS_CHILDREN);
        }

        // 验证是否存在人员
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getOrganization, entity.getId());
        long count = userService.count(queryWrapper);
        if (count > 0) {
            throw new CustomException(OrganizationExceptionEnum.HAS_USER);
        }
    }

    @Override
    protected void copyPropertyHandle(Organization entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

    @Override
    public void enable(String id) {
        Organization entity = query(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        Organization entity = query(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }


}

