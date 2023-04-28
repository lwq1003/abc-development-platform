package tech.abc.platform.entityconfig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.query.QueryRuleEnum;
import tech.abc.platform.entityconfig.entity.EntityModelProperty;
import tech.abc.platform.entityconfig.entity.EntityView;
import tech.abc.platform.entityconfig.entity.ViewQueryCondition;
import tech.abc.platform.entityconfig.enums.EntityModelPropertyTypeEnum;
import tech.abc.platform.entityconfig.mapper.ViewQueryConditionMapper;
import tech.abc.platform.entityconfig.service.EntityModelPropertyService;
import tech.abc.platform.entityconfig.service.EntityViewService;
import tech.abc.platform.entityconfig.service.ViewQueryConditionService;
import tech.abc.platform.entityconfig.vo.SortedObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 视图查询条件 服务实现类
 *
 * @author wqliu
 * @date 2023-04-15
 */
@Service
@Slf4j
public class ViewQueryConditionServiceImpl extends BaseServiceImpl<ViewQueryConditionMapper, ViewQueryCondition> implements ViewQueryConditionService {

    @Autowired
    private EntityViewService entityViewService;

    @Autowired
    private EntityModelPropertyService entityModelPropertyService;


    @Override
    public ViewQueryCondition init() {
        ViewQueryCondition entity = new ViewQueryCondition();
        // 默认值处理
        entity.setDataType("STRING");
        entity.setMatchRule("LK");
        entity.setReadonlyFlag("YES");
        entity.setShowFlag("YES");
        return entity;
    }

    @Override
    public void beforeAdd(ViewQueryCondition entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(ViewQueryCondition::getName, entity.getName())
                .eq(ViewQueryCondition::getView, entity.getView()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(ViewQueryCondition::getCode, entity.getCode())
                .eq(ViewQueryCondition::getView, entity.getView()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public void beforeModify(ViewQueryCondition entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(ViewQueryCondition::getName, entity.getName())
                .eq(ViewQueryCondition::getView, entity.getView())
                .ne(ViewQueryCondition::getId, entity.getId()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(ViewQueryCondition::getCode, entity.getCode())
                .eq(ViewQueryCondition::getView, entity.getView())
                .ne(ViewQueryCondition::getId, entity.getId()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<ViewQueryCondition> list = this.lambdaQuery().in(ViewQueryCondition::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }


    @Override
    public void addFromModelProperty(String viewId, String code) {
        // 存在不做处理，不存在则新增
        if (checkExist(viewId, code) == false) {
            // 获取视图对象
            EntityView entityView = entityViewService.query(viewId);
            // 获取实体属性完整列表
            List<EntityModelProperty> entityModelPropertyList = entityModelPropertyService.getFullPropertyByEntityModelId(entityView.getEntityModel());
            // 获取实体模型属性
            List<EntityModelProperty> list = entityModelPropertyList.stream().filter(x -> x.getCode().equals(code)).collect(Collectors.toList());
            EntityModelProperty entityModelProperty = list.get(0);
            addFromEntityModelProperty(viewId, entityModelProperty);
        }

    }

    private void addFromEntityModelProperty(String viewId, EntityModelProperty entityModelProperty) {
        // 参考实体模型属性，创建视图查询条件
        ViewQueryCondition entity = new ViewQueryCondition();
        entity.setName(entityModelProperty.getName());
        entity.setCode(entityModelProperty.getCode());
        entity.setView(viewId);
        entity.setDataType(entityModelProperty.getDataType());
        entity.setReadonlyFlag(YesOrNoEnum.NO.name());
        entity.setWidgetType(entityModelProperty.getWidgetType());
        entity.setFormatPattern(entityModelProperty.getFormatPattern());
        entity.setDictionaryType(entityModelProperty.getDictionaryType());
        entity.setDefaultValue(entityModelProperty.getDefaultValue());
        // 默认将字符串类型设置匹配模式为模糊
        if (entityModelProperty.getDataType().equals(EntityModelPropertyTypeEnum.STRING.name())) {
            entity.setMatchRule(QueryRuleEnum.LK.name());
        }
        this.add(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSort(String viewId, List<SortedObject> list) {
        list.forEach(x -> {
            ViewQueryCondition entity = this.lambdaQuery().eq(ViewQueryCondition::getView, viewId)
                    .eq(ViewQueryCondition::getCode, x.getCode()).one();
            entity.setOrderNo(StringUtils.leftPad(x.getIndex().toString(), 2, "0"));
            modify(entity);

        });

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clear(String viewId) {
        QueryWrapper<ViewQueryCondition> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ViewQueryCondition::getView, viewId);
        this.remove(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatchFromModelProperty(String viewId) {
        // 获取视图对象
        EntityView entityView = entityViewService.query(viewId);
        // 获取实体属性完整列表
        List<EntityModelProperty> entityModelPropertyList = entityModelPropertyService.getByEntityModelId(entityView.getEntityModel());
        for (EntityModelProperty entity : entityModelPropertyList) {
            if (checkExist(viewId, entity.getCode()) == false) {
                addFromEntityModelProperty(viewId, entity);

            }
        }
    }

    @Override
    public List<ViewQueryCondition> listByView(String viewId) {
        return this.lambdaQuery().eq(ViewQueryCondition::getView, viewId)
                .orderByAsc(ViewQueryCondition::getOrderNo).list();
    }

    /**
     * 检查查询条件是否已存在
     *
     * @param viewId 视图id
     * @param code   查询条件编码
     */
    private boolean checkExist(String viewId, String code) {

        Long count = this.lambdaQuery().eq(ViewQueryCondition::getView, viewId)
                .eq(ViewQueryCondition::getCode, code).count();
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    protected void copyPropertyHandle(ViewQueryCondition entity, String... value) {
        entity.setView(value[0]);
    }
}

