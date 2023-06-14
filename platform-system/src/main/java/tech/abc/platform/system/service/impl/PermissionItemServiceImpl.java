package tech.abc.platform.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.constant.TreeDefaultConstant;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.CacheUtil;
import tech.abc.platform.system.entity.GroupPermissionItem;
import tech.abc.platform.system.entity.PermissionItem;
import tech.abc.platform.system.enums.PermissionTypeEnum;
import tech.abc.platform.system.mapper.PermissionItemMapper;
import tech.abc.platform.system.service.GroupPermissionItemService;
import tech.abc.platform.system.service.PermissionItemService;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限项 服务实现类
 *
 * @author wqliu
 * @date 2023-04-25
 */
@Service
@Slf4j
public class PermissionItemServiceImpl extends BaseServiceImpl<PermissionItemMapper, PermissionItem> implements PermissionItemService {

    @Autowired
    private GroupPermissionItemService groupPermissionItemService;

    @Autowired
    private CacheUtil cacheUtil;


    @Override
    public PermissionItem init() {
        PermissionItem entity = new PermissionItem();
        // 默认值处理
        entity.setType("");
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(PermissionItem entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(PermissionItem::getName, entity.getName())
                    .eq(PermissionItem::getPermissionItem, entity.getPermissionItem()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(PermissionItem::getCode, entity.getCode())
                    .eq(PermissionItem::getPermissionItem, entity.getPermissionItem()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(PermissionItem entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(PermissionItem::getName, entity.getName())
                    .eq(PermissionItem::getPermissionItem, entity.getPermissionItem())
                    .ne(PermissionItem::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(PermissionItem::getCode, entity.getCode())
                    .eq(PermissionItem::getPermissionItem, entity.getPermissionItem())
                    .ne(PermissionItem::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
            }
        }


    }

    @Override
    protected void afterModify(PermissionItem entity, PermissionItem orginEntity) {
        // 若编码或上级发生变化，则级联修改下级权限编码
        if (entity.getCode().equals(orginEntity.getCode()) == false || entity.getPermissionItem().equals(orginEntity.getPermissionItem()) == false) {
            List<PermissionItem> list = this.lambdaQuery().eq(PermissionItem::getPermissionItem, entity.getId()).list();
            if (CollectionUtils.isNotEmpty(list)) {
                for (PermissionItem item : list) {
                    modify(item);
                }
            }
        }
    }

    @Override
    public void beforeRemove(PermissionItem entity) {

        // 检查权限树节点是否有下级
        if (super.lambdaQuery().eq(PermissionItem::getPermissionItem, entity.getId()).count() > 0) {
            throw new CustomException(CommonException.HAS_CHILDREN);
        }

        // 若权限已分配用户组，先删除相应的用户组与权限对应关系数据
        QueryWrapper<GroupPermissionItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GroupPermissionItem::getPermissionItemId, entity.getId());
        long count = groupPermissionItemService.count(queryWrapper);
        if (count > 0) {
            groupPermissionItemService.remove(queryWrapper);
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<PermissionItem> list = this.lambdaQuery().in(PermissionItem::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(PermissionItem entity, String... value) {

        if (ArrayUtils.isNotEmpty(value)) {
            // 复制父级
            // 设置父级标识
            entity.setPermissionItem(value[0]);

        } else {
            // 直接复制
            // 名称后附加“副本”用于区分
            entity.setName(entity.getName() + " 副本");
        }
    }

    @Override
    protected void afterAddByCopy(PermissionItem sourceEntity, PermissionItem entity) {
        // 拷贝下级
        List<PermissionItem> list = this.lambdaQuery().eq(PermissionItem::getPermissionItem, sourceEntity.getId()).list();
        if (CollectionUtils.isNotEmpty(list)) {
            for (PermissionItem item : list) {
                addByCopy(item.getId(), entity.getId());
            }
        }
    }

    @Override
    public void enable(String id) {
        PermissionItem entity = query(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        PermissionItem entity = query(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }

    @Override
    protected void beforeAddOrModifyOp(PermissionItem entity) {
        String permissionCode = generatePermissionCode(entity);
        entity.setPermissionCode(permissionCode);

        if (entity.getType().equals(PermissionTypeEnum.MENU.name()) ||
                entity.getType().equals(PermissionTypeEnum.PAGE.name())
        ) {
            String componentPath = generateComponentPath(entity);
            entity.setComponent(componentPath);
        } else if (entity.getType().equals(PermissionTypeEnum.MODULE.name())) {
            entity.setComponent("#");
        }


    }

    /**
     * 自动生成权限编码
     *
     * @param entity
     * @return
     */
    private String generatePermissionCode(PermissionItem entity) {

        StringBuffer sb = new StringBuffer();

        do {
            sb.insert(0, entity.getCode()).insert(0, ":");
            entity = getById(entity.getPermissionItem());

        } while (!entity.getId().equals(TreeDefaultConstant.DEFAULT_TREE_ROOT_ID));
        return sb.substring(1);
    }

    /**
     * 自动生成组件路径
     *
     * @param entity
     * @return
     */
    private String generateComponentPath(PermissionItem entity) {


        PermissionItem tempEntity = null;

        do {
            tempEntity = getById(entity.getPermissionItem());
            if (tempEntity.getType().equals(PermissionTypeEnum.MODULE)) {
                break;
            }

        } while (!tempEntity.getPermissionItem().equals(TreeDefaultConstant.DEFAULT_TREE_ROOT_ID));
        return MessageFormat.format("{0}/view/{1}/{2}", tempEntity.getCode(), entity.getCode(), entity.getViewCode());

    }

}

