package tech.abc.platform.entityconfig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import tech.abc.platform.entityconfig.entity.EntityView;
import tech.abc.platform.entityconfig.enums.EntityModelPropertyTypeEnum;
import tech.abc.platform.entityconfig.exception.EntityModelException;
import tech.abc.platform.entityconfig.mapper.EntityModelMapper;
import tech.abc.platform.entityconfig.service.EntityModelPropertyService;
import tech.abc.platform.entityconfig.service.EntityModelService;
import tech.abc.platform.entityconfig.service.EntityViewService;
import tech.abc.platform.support.service.SerialNoService;

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

    @Autowired
    private EntityModelPropertyService entityModelPropertyService;

    @Autowired
    private EntityViewService entityViewService;

    @Autowired
    private SerialNoService serialNoService;

    @Autowired
    private EntityModelService entityModelService;


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
    protected void afterRemove(EntityModel entity) {
        // 移除关联的实体模型属性
        QueryWrapper<EntityModelProperty> entityModelPropertyQueryWrapper = new QueryWrapper<>();
        entityModelPropertyQueryWrapper.lambda().eq(EntityModelProperty::getEntityModel, entity.getId());
        entityModelPropertyService.remove(entityModelPropertyQueryWrapper);

        // 移除关联的实体视图
        QueryWrapper<EntityView> queryWrapperEntityView = new QueryWrapper<>();
        queryWrapperEntityView.lambda().eq(EntityView::getEntityModel, entity.getId());
        entityViewService.remove(queryWrapperEntityView);

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
    public String generateSerialNo(String code) {
        String entityModelId = entityModelService.getIdByCode(code);

        List<EntityModelProperty> list = entityModelPropertyService.lambdaQuery()
                .eq(EntityModelProperty::getEntityModel, entityModelId)
                .eq(EntityModelProperty::getDataType, EntityModelPropertyTypeEnum.SERIAL_NO.name()).list();

        if (CollectionUtils.isNotEmpty(list)) {
            String serialNoId = list.get(0).getSerialNo();
            String serialNo = serialNoService.generateSingleById(serialNoId);
            log.info(serialNo);
            return serialNo;

        } else {
            throw new CustomException(EntityModelException.SERIAL_CONFIG_NOT_FOUND, code);
        }


    }

    @Override
    protected void copyPropertyHandle(EntityModel entity, String... value) {

        if (ArrayUtils.isNotEmpty(value)) {
            // 复制父级
            // 设置关联的实体标识
            entity.setEntity(value[0]);
        } else {
            // 直接复制
            // 名称后附加“副本”用于区分
            entity.setName(entity.getName() + " 副本");
        }

    }

    @Override
    protected void afterAddByCopy(EntityModel sourceEntity, EntityModel entityModel) {
        String sourceId = sourceEntity.getId();
        String entityModelId = entityModel.getId();

        // 复制模型属性
        List<EntityModelProperty> entityModelPropertyList = entityModelPropertyService.getByEntityModelId(sourceId);
        for (EntityModelProperty item : entityModelPropertyList) {
            entityModelPropertyService.addByCopy(item.getId(), entityModelId);
        }

        // 复制实体视图
        List<EntityView> entityViewList = entityViewService.getByModelId(sourceId);
        for (EntityView item : entityViewList) {
            // 此处需要同时传入实体标识和实体模型标识
            entityViewService.addByCopy(item.getId(), entityModel.getEntity(), entityModelId);
        }
    }
}

