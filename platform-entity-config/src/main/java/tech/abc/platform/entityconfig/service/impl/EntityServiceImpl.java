package tech.abc.platform.entityconfig.service.impl;

import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.entityconfig.codegenerator.service.CodeGenerateService;
import tech.abc.platform.entityconfig.entity.Entity;
import tech.abc.platform.entityconfig.entity.EntityModel;
import tech.abc.platform.entityconfig.mapper.EntityMapper;
import tech.abc.platform.entityconfig.service.EntityModelService;
import tech.abc.platform.entityconfig.service.EntityService;
import tech.abc.platform.entityconfig.service.EntityViewService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实体 服务实现类
 *
 * @author wqliu
 * @date 2023-04-11
 */
@Service
@Slf4j
public class EntityServiceImpl extends BaseServiceImpl<EntityMapper, Entity> implements EntityService {

    @Autowired
    private CodeGenerateService codeGenerateService;

    @Autowired
    private EntityModelService entityModelService;

    @Autowired
    private EntityViewService entityViewService;


    @Override
    public Entity init() {
        Entity entity = new Entity();
        entity.setAuthor("wqliu");
        // 默认值处理
        return entity;
    }

    @Override
    public void beforeAdd(Entity entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        long countName = this.lambdaQuery().eq(Entity::getName, entity.getName()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
        }
        // 验证 编码 全局唯一
        long countCode = this.lambdaQuery().eq(Entity::getCode, entity.getCode()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
        }
    }

    @Override
    protected void afterAdd(Entity entity) {
        // 新建实体时自动创建一个同名的主模型
        EntityModel entityModel = entityModelService.init();
        entityModel.setEntity(entity.getId());
        entityModel.setName(entity.getName());
        entityModel.setCode(entity.getCode());
        entityModel.setMainModelFlag(YesOrNoEnum.YES.name());
        entityModel.setOrderNo("01");

        entityModelService.add(entityModel);
    }

    @Override
    public void beforeModify(Entity entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        long countName = this.lambdaQuery().eq(Entity::getName, entity.getName())
                .ne(Entity::getId, entity.getId()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
        }
        // 验证 编码 全局唯一
        long countCode = this.lambdaQuery().eq(Entity::getCode, entity.getCode())
                .ne(Entity::getId, entity.getId()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
        }
    }

    @Override
    public Entity getByCode(String code) {
        Entity entity = this.lambdaQuery().eq(Entity::getCode, code).one();
        if (entity == null) {
            throw new CustomException(CommonException.NOT_EXIST);
        }
        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void generateTable(String id) {
        String[] idArray = StringUtils.split(id.toString(), ",");
        for (String item : idArray) {
            Entity entity = getEntity(item);
            codeGenerateService.generateTable(entity.getCode());

        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateCode(String id) {
        String[] idArray = StringUtils.split(id.toString(), ",");
        for (String item : idArray) {
            Entity entity = getEntity(item);
            codeGenerateService.generateCode(entity.getCode());

        }
    }


}

