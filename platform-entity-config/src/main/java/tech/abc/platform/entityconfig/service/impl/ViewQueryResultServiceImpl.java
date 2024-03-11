package tech.abc.platform.entityconfig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.entityconfig.entity.EntityModelProperty;
import tech.abc.platform.entityconfig.entity.EntityView;
import tech.abc.platform.entityconfig.entity.ViewQueryResult;
import tech.abc.platform.entityconfig.enums.EntityModelPropertyTypeEnum;
import tech.abc.platform.entityconfig.mapper.ViewQueryResultMapper;
import tech.abc.platform.entityconfig.service.EntityModelPropertyService;
import tech.abc.platform.entityconfig.service.EntityViewService;
import tech.abc.platform.entityconfig.service.ViewQueryResultService;
import tech.abc.platform.entityconfig.vo.SortedObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 视图查询结果 服务实现类
 *
 * @author wqliu
 * @date 2023-04-16
 */
@Service
@Slf4j
public class ViewQueryResultServiceImpl extends BaseServiceImpl<ViewQueryResultMapper, ViewQueryResult> implements ViewQueryResultService {

    @Autowired
    private EntityViewService entityViewService;

    @Autowired
    private EntityModelPropertyService entityModelPropertyService;

    @Override
    public ViewQueryResult init() {
        ViewQueryResult entity = new ViewQueryResult();
        // 默认值处理
        entity.setDataType("STRING");
        entity.setSortableFlag("YES");
        entity.setShowOverflowTooltipFlag("YES");
        entity.setShowFlag("YES");
        return entity;
    }

    @Override
    public void beforeAdd(ViewQueryResult entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(ViewQueryResult::getName, entity.getName())
                .eq(ViewQueryResult::getView, entity.getView()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(ViewQueryResult::getCode, entity.getCode())
                .eq(ViewQueryResult::getView, entity.getView()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public void beforeModify(ViewQueryResult entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        long countName = this.lambdaQuery().eq(ViewQueryResult::getName, entity.getName())
                .eq(ViewQueryResult::getView, entity.getView())
                .ne(ViewQueryResult::getId, entity.getId()).count();
        if (countName > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
        }
        // 验证 编码 同节点下唯一
        long countCode = this.lambdaQuery().eq(ViewQueryResult::getCode, entity.getCode())
                .eq(ViewQueryResult::getView, entity.getView())
                .ne(ViewQueryResult::getId, entity.getId()).count();
        if (countCode > 0) {
            throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<ViewQueryResult> list = this.lambdaQuery().in(ViewQueryResult::getId, idList).list();
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
            // 参考实体模型属性，创建视图查询条件
            addFromEntityModelProperty(viewId, entityModelProperty);
        }


    }

    private void addFromEntityModelProperty(String viewId, EntityModelProperty entityModelProperty) {
        // 参考实体模型属性，创建视图查询条件
        ViewQueryResult entity = init();
        entity.setName(entityModelProperty.getName());
        entity.setCode(entityModelProperty.getCode());
        entity.setView(viewId);
        entity.setDataType(entityModelProperty.getDataType());
        entity.setDictionaryType(entityModelProperty.getDictionaryType());

        // 数据类型为数据字典或实体类，会转换成显示名称，默认设置不支持远程排序
        if (entityModelProperty.getDataType().equals(EntityModelPropertyTypeEnum.DATA_DICTIONARY.name()) ||
                entityModelProperty.getDataType().equals(EntityModelPropertyTypeEnum.ENTITY.name())) {
            entity.setSortableFlag(YesOrNoEnum.NO.name());
        }


        this.add(entity);
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
    public List<ViewQueryResult> listByView(String viewId) {
        return this.lambdaQuery().eq(ViewQueryResult::getView, viewId)
                .orderByAsc(ViewQueryResult::getOrderNo).list();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSort(String viewId, List<SortedObject> list) {
        list.forEach(x -> {
            ViewQueryResult entity = this.lambdaQuery().eq(ViewQueryResult::getView, viewId)
                    .eq(ViewQueryResult::getCode, x.getCode()).one();
            entity.setOrderNo(StringUtils.leftPad(x.getIndex().toString(), 2, "0"));
            modify(entity);

        });

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void clear(String viewId) {
        QueryWrapper<ViewQueryResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ViewQueryResult::getView, viewId);
        this.remove(queryWrapper);
    }

    /**
     * 检查查询条件是否已存在
     *
     * @param viewId 视图id
     * @param code   查询结果编码
     */
    private boolean checkExist(String viewId, String code) {

        Long count = this.lambdaQuery().eq(ViewQueryResult::getView, viewId)
                .eq(ViewQueryResult::getCode, code).count();
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    protected void copyPropertyHandle(ViewQueryResult entity, String... value) {
        if (ArrayUtils.isNotEmpty(value)) {
            // 复制父级
            // 设置关联的视图标识
            entity.setView(value[0]);
        } else {
            // 直接复制
            // 名称后附加“副本”用于区分
            entity.setName(entity.getName() + " 副本");
        }
    }
}

