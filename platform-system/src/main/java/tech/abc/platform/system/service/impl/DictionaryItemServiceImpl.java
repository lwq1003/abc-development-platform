package tech.abc.platform.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.constant.CacheConstant;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.system.entity.DictionaryItem;
import tech.abc.platform.system.mapper.DictionaryItemMapper;
import tech.abc.platform.system.service.DictionaryItemService;
import tech.abc.platform.system.service.DictionaryTypeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典项 服务实现类
 *
 * @author wqliu
 * @date 2023-04-26
 */
@Service
@Slf4j
public class DictionaryItemServiceImpl extends BaseServiceImpl<DictionaryItemMapper, DictionaryItem> implements DictionaryItemService {

    @Autowired
    private DictionaryTypeService dictionaryTypeService;


    @Override
    public DictionaryItem init() {
        DictionaryItem entity = new DictionaryItem();
        // 默认值处理
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(DictionaryItem entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(DictionaryItem::getName, entity.getName())
                    .eq(DictionaryItem::getDictionaryType, entity.getDictionaryType()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(DictionaryItem::getCode, entity.getCode())
                    .eq(DictionaryItem::getDictionaryType, entity.getDictionaryType()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
            }
        }
    }

    @Override
    public void beforeModify(DictionaryItem entity) {
        // 唯一性验证
        // 验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(DictionaryItem::getName, entity.getName())
                    .eq(DictionaryItem::getDictionaryType, entity.getDictionaryType())
                    .ne(DictionaryItem::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【名称】");
            }
        }
        // 验证 编码 同节点下唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(DictionaryItem::getCode, entity.getCode())
                    .eq(DictionaryItem::getDictionaryType, entity.getDictionaryType())
                    .ne(DictionaryItem::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE, "【编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<DictionaryItem> list = this.lambdaQuery().in(DictionaryItem::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(DictionaryItem entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }


    @Override
    public void afterAdd(DictionaryItem entity) {
        cacheUtil.set(CacheConstant.DICTIONARY_ITEM_CACHE_PREFIX + entity.getId(), entity.getName());
        String typeCode = getTypeCode(entity.getDictionaryType());
        cacheUtil.set(CacheConstant.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getCode(), entity.getName());
        cacheUtil.set(CacheConstant.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getName(),
                entity.getCode());

    }

    @Override
    public void afterModify(DictionaryItem entity, DictionaryItem originEntity) {
        cacheUtil.set(CacheConstant.DICTIONARY_ITEM_CACHE_PREFIX + entity.getId(), entity.getName());
        String typeCode = getTypeCode(entity.getDictionaryType());
        cacheUtil.set(CacheConstant.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getCode(), entity.getName());
        cacheUtil.set(CacheConstant.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getName(),
                entity.getCode());
    }

    @Override
    public void afterRemove(DictionaryItem entity) {
        cacheUtil.remove(CacheConstant.DICTIONARY_ITEM_CACHE_PREFIX + entity.getId());
        String typeCode = getTypeCode(entity.getDictionaryType());
        cacheUtil.remove(CacheConstant.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getCode());
        cacheUtil.remove(CacheConstant.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getName());
    }


    private String getTypeCode(String typeId) {
        return dictionaryTypeService.query(typeId).getCode();
    }

    @Override
    public void enable(String id) {
        DictionaryItem entity = query(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        DictionaryItem entity = query(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }

}

