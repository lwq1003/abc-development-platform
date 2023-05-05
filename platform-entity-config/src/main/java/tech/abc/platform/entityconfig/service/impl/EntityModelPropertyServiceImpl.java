package tech.abc.platform.entityconfig.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.entityconfig.entity.EntityModel;
import tech.abc.platform.entityconfig.entity.EntityModelProperty;
import tech.abc.platform.entityconfig.mapper.EntityModelPropertyMapper;
import tech.abc.platform.entityconfig.service.EntityModelPropertyService;
import tech.abc.platform.entityconfig.service.EntityModelService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体模型属性 服务实现类
 *
 * @author wqliu
 * @date 2023-04-15
 */
@Service
@Slf4j
public class EntityModelPropertyServiceImpl extends BaseServiceImpl<EntityModelPropertyMapper, EntityModelProperty> implements EntityModelPropertyService {
    @Autowired
    private EntityModelService entityModelService;

    @Override
    public EntityModelProperty init() {
        EntityModelProperty entity = new EntityModelProperty();
        // 默认值处理
        entity.setDataType("STRING");
        entity.setNullFlag("YES");
        entity.setUniqueFlag("NO");
        entity.setMainFlag("NO");
        entity.setParentPropertyFlag("NO");
        return entity;
    }

    @Override
    public void beforeAdd(EntityModelProperty entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(EntityModelProperty::getName, entity.getName())
                .eq(EntityModelProperty::getEntityModel, entity.getEntityModel()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(EntityModelProperty::getCode, entity.getCode())
                .eq(EntityModelProperty::getEntityModel, entity.getEntityModel()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public void beforeModify(EntityModelProperty entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(EntityModelProperty::getName, entity.getName())
                .eq(EntityModelProperty::getEntityModel, entity.getEntityModel())
                .ne(EntityModelProperty::getId, entity.getId()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(EntityModelProperty::getCode, entity.getCode())
                .eq(EntityModelProperty::getEntityModel, entity.getEntityModel())
                .ne(EntityModelProperty::getId, entity.getId()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<EntityModelProperty> list = this.lambdaQuery().in(EntityModelProperty::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }


    @Override
    protected void copyPropertyHandle(EntityModelProperty entity, String... value) {
        if (ArrayUtils.isNotEmpty(value)) {
            // 复制父级
            // 设置关联的实体标识
            entity.setEntityModel(value[0]);
        } else {
            // 直接复制
            // 名称后附加“副本”用于区分
            entity.setName(entity.getName() + " 副本");
        }
    }

    @Override
    public List<EntityModelProperty> getByEntityModelId(String entityModelId) {
        return this.lambdaQuery().eq(EntityModelProperty::getEntityModel, entityModelId)
                .orderByAsc(EntityModelProperty::getOrderNo)
                .list();
    }

    @Override
    public List<EntityModelProperty> getFullPropertyByEntityModelId(String entityModelId) {
        log.info("模型标识:{}", entityModelId);
        EntityModel entityModel = entityModelService.query(entityModelId);
        String parentModelId = entityModelService.getIdByCode(entityModel.getParentModel());
        log.info("上级模型标识:{}", parentModelId);


        // 获取父级模型属性列表
        List<EntityModelProperty> parentModelPropertyList = getByEntityModelId(parentModelId);

        // 获取实体模型属性列表
        List<EntityModelProperty> entityModelPropertyList = getByEntityModelId(entityModelId);

        CollectionUtils.addAll(entityModelPropertyList, parentModelPropertyList.iterator());
        return entityModelPropertyList;
    }

    @Override
    public List<EntityModelProperty> getByEntityModelCode(String entityModelCode) {
        return this.lambdaQuery().eq(EntityModelProperty::getCode, entityModelCode).list();
    }
}
