package tech.abc.platform.entityconfig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.entityconfig.entity.*;
import tech.abc.platform.entityconfig.exception.EntityViewException;
import tech.abc.platform.entityconfig.mapper.EntityViewMapper;
import tech.abc.platform.entityconfig.service.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 实体视图 服务实现类
 *
 * @author wqliu
 * @date 2023-04-13
 */
@Service
@Slf4j
public class EntityViewServiceImpl extends BaseServiceImpl<EntityViewMapper, EntityView> implements EntityViewService {

    @Autowired
    private ViewPropertyService viewPropertyService;

    @Autowired
    private ViewQueryConditionService viewQueryConditionService;


    @Autowired
    private ViewQueryResultService viewQueryResultService;

    @Autowired
    private ViewButtonService viewButtonService;

    @Autowired
    private EntityService entityService;


    @Override
    public EntityView init() {
        EntityView entity = new EntityView();
        // 默认值处理
        entity.setMainReferenceViewFlag(YesOrNoEnum.NO.name());
        return entity;
    }

    @Override
    public void beforeAdd(EntityView entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(EntityView::getName, entity.getName())
                .eq(EntityView::getEntityModel, entity.getEntityModel()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(EntityView::getCode, entity.getCode())
                .eq(EntityView::getEntityModel, entity.getEntityModel()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public void beforeModify(EntityView entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(EntityView::getName, entity.getName())
                .eq(EntityView::getEntityModel, entity.getEntityModel())
                .ne(EntityView::getId, entity.getId()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(EntityView::getCode, entity.getCode())
                .eq(EntityView::getEntityModel, entity.getEntityModel())
                .ne(EntityView::getId, entity.getId()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<EntityView> list = this.lambdaQuery().in(EntityView::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void beforeRemove(EntityView entity) {
        String viewId = entity.getId();
        // 视图属性
        QueryWrapper<ViewProperty> queryWrapperViewProperty = new QueryWrapper<>();
        queryWrapperViewProperty.lambda().eq(ViewProperty::getView, viewId);
        viewPropertyService.remove(queryWrapperViewProperty);
        // 查询条件
        QueryWrapper<ViewQueryCondition> queryWrapperViewQueryCondition = new QueryWrapper<>();
        queryWrapperViewQueryCondition.lambda().eq(ViewQueryCondition::getView, viewId);
        viewQueryConditionService.remove(queryWrapperViewQueryCondition);
        // 查询结果
        QueryWrapper<ViewQueryResult> queryWrapperViewQueryResult = new QueryWrapper<>();
        queryWrapperViewQueryResult.lambda().eq(ViewQueryResult::getView, viewId);
        viewQueryResultService.remove(queryWrapperViewQueryResult);
        // 视图按钮
        QueryWrapper<ViewButton> queryWrapperViewButton = new QueryWrapper<>();
        queryWrapperViewButton.lambda().eq(ViewButton::getView, viewId);
        viewButtonService.remove(queryWrapperViewButton);

    }

    @Override
    public List<EntityView> getByModelId(String modelId) {
        return this.lambdaQuery().eq(EntityView::getEntityModel, modelId).list();
    }


    @Override
    public List<EntityView> getByEntityId(String entityId) {
        return this.lambdaQuery().eq(EntityView::getEntity, entityId).list();
    }


    @Override
    public String getMainReferenceViewCode(String entityCode) {
        Entity entity = entityService.getByCode(entityCode);
        List<EntityView> list = getByEntityId(entity.getId());
        Optional<EntityView> entityView = list.stream().filter(x -> x.getMainReferenceViewFlag().equals(YesOrNoEnum.YES.name())).findFirst();
        if (entityView.isPresent()) {
            return entityView.get().getCode();
        } else {
            throw new CustomException(EntityViewException.MAIN_REFERENTITY_VIEW_NOT_SET, entity.getName());
        }


    }


    @Override
    protected void copyPropertyHandle(EntityView entity, String... value) {
        if (ArrayUtils.isNotEmpty(value)) {
            // 复制父级
            // 设置关联的实体标识和实体模型标识
            entity.setEntity(value[0]);
            entity.setEntityModel(value[1]);

        } else {
            // 直接复制
            // 名称后附加“副本”用于区分
            entity.setName(entity.getName() + " 副本");
        }
    }

    @Override
    protected void afterAddByCopy(EntityView source, EntityView entity) {
        String sourceViewId = source.getId();
        String viewId = entity.getId();
        // 视图属性
        viewPropertyHandle(sourceViewId, viewId);

        // 查询条件
        viewQueryConditionHandle(sourceViewId, viewId);

        // 查询结果
        viewQueryResultHandle(sourceViewId, viewId);

        // 按钮
        viewButtonHandle(sourceViewId, viewId);

    }

    private void viewPropertyHandle(String sourceViewId, String viewId) {
        QueryWrapper<ViewProperty> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ViewProperty::getView, sourceViewId);
        List<ViewProperty> list = viewPropertyService.list(queryWrapper);
        for (ViewProperty item : list) {
            viewPropertyService.addByCopy(item.getId(), viewId);
        }
    }

    private void viewQueryConditionHandle(String sourceViewId, String viewId) {
        QueryWrapper<ViewQueryCondition> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ViewQueryCondition::getView, sourceViewId);
        List<ViewQueryCondition> list = viewQueryConditionService.list(queryWrapper);
        for (ViewQueryCondition item : list) {
            viewQueryConditionService.addByCopy(item.getId(), viewId);
        }
    }

    private void viewQueryResultHandle(String sourceViewId, String viewId) {
        QueryWrapper<ViewQueryResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ViewQueryResult::getView, sourceViewId);
        List<ViewQueryResult> list = viewQueryResultService.list(queryWrapper);
        for (ViewQueryResult item : list) {
            viewQueryResultService.addByCopy(item.getId(), viewId);
        }
    }

    private void viewButtonHandle(String sourceViewId, String viewId) {
        QueryWrapper<ViewButton> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ViewButton::getView, sourceViewId);
        List<ViewButton> list = viewButtonService.list(queryWrapper);
        for (ViewButton item : list) {
            viewButtonService.addByCopy(item.getId(), viewId);
        }
    }


}

