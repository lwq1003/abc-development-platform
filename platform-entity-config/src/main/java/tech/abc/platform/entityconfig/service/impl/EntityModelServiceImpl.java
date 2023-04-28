package tech.abc.platform.entityconfig.service.impl;

import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.entityconfig.entity.EntityModel;
import tech.abc.platform.entityconfig.mapper.EntityModelMapper;
import tech.abc.platform.entityconfig.service.EntityModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体模型 服务实现类
 *
 * @author wqliu
 * @date 2023-04-13
 */
@Service
@Slf4j
public class EntityModelServiceImpl extends BaseServiceImpl<EntityModelMapper, EntityModel> implements EntityModelService {
    @Override
    public EntityModel init() {
        EntityModel entity = new EntityModel();
        // 默认值处理
        entity.setParentModel("BUSINESS_MODEL");
        entity.setMainModelFlag("NO");
        entity.setSelfReferenceFlag("NO");
        return entity;
    }

    @Override
    public void beforeAdd(EntityModel entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(EntityModel::getName, entity.getName())
                .eq(EntityModel::getEntity, entity.getEntity()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(EntityModel::getCode, entity.getCode())
                .eq(EntityModel::getEntity, entity.getEntity()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public void beforeModify(EntityModel entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(EntityModel::getName, entity.getName())
                .eq(EntityModel::getEntity, entity.getEntity())
                .ne(EntityModel::getId, entity.getId()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(EntityModel::getCode, entity.getCode())
                .eq(EntityModel::getEntity, entity.getEntity())
                .ne(EntityModel::getId, entity.getId()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<EntityModel> list = this.lambdaQuery().in(EntityModel::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    public List<EntityModel> getByEntityId(String entityId) {
        return this.lambdaQuery().eq(EntityModel::getEntity, entityId).list();
    }

    @Override
    public void createTable(String tableName, String tableComment, List tableFieldInfoList) {
        this.baseMapper.createTable(tableName, tableComment, tableFieldInfoList);
    }

    @Override
    public String getIdByCode(String code) {
        return this.lambdaQuery().eq(EntityModel::getCode, code).one().getId();
    }

    @Override
    protected void copyPropertyHandle(EntityModel entity, String... value) {
        // 名称后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

}

