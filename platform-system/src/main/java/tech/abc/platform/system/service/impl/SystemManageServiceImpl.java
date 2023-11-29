package tech.abc.platform.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.constant.CacheConstant;
import tech.abc.platform.common.utils.CacheUtil;
import tech.abc.platform.system.entity.DictionaryItem;
import tech.abc.platform.system.entity.DictionaryType;
import tech.abc.platform.system.service.DictionaryItemService;
import tech.abc.platform.system.service.DictionaryTypeService;
import tech.abc.platform.system.service.SystemManageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统管理服务实现
 *
 * @author wqliu
 * @date 2022-8-1
 */
@Service
public class SystemManageServiceImpl implements SystemManageService {
    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    @Autowired
    private DictionaryItemService dictionaryItemService;


    @Override

    public void rebuildSystemCache() {


        // 移除字典类型所有的缓存
        cacheUtil.removePrefix(CacheConstant.DICTIONARY_TYPE_CACHE_PREFIX);
        // 获取所有的字典类型数据
        List<DictionaryType> dictionaryTypeList = dictionaryTypeService.list();

        Map<String, String> dictionaryTypeMap = new HashMap<String, String>(200);
        for (DictionaryType entity : dictionaryTypeList) {
            dictionaryTypeMap.put(CacheConstant.DICTIONARY_TYPE_CACHE_PREFIX + entity.getId(), entity.getName());
        }
        // 批量缓存字典类型数据
        cacheUtil.setBatch(dictionaryTypeMap);

        // 移除字典项目所有的缓存
        cacheUtil.removePrefix(CacheConstant.DICTIONARY_ITEM_CACHE_PREFIX);
        // 获取所有的字典项目数据
        List<DictionaryItem> dictionaryItemList = dictionaryItemService.list();

        Map<String, String> dictionaryItemMap = new HashMap<String, String>(2000);
        for (DictionaryItem entity : dictionaryItemList) {
            dictionaryItemMap.put(CacheConstant.DICTIONARY_ITEM_CACHE_PREFIX + entity.getId(), entity.getName());
        }
        // 批量缓存字典项目数据
        cacheUtil.setBatch(dictionaryItemMap);


        // 移除字典项目编码所有的缓存
        cacheUtil.removePrefix(CacheConstant.DICTIONARY_ITEM_CODE_CACHE_PREFIX);
        Map<String, String> dictionaryItemCodeMap = new HashMap<String, String>(2000);
        for (DictionaryItem entity : dictionaryItemList) {
            String typeCode = dictionaryTypeService.query(entity.getDictionaryType()).getCode();
            dictionaryItemCodeMap.put(CacheConstant.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_" + entity.getCode(), entity.getName());
            dictionaryItemCodeMap.put(CacheConstant.DICTIONARY_ITEM_CODE_CACHE_PREFIX + "_" + typeCode + "_"
                    + entity.getName(), entity.getCode());

        }
        // 批量缓存字典项目数据
        cacheUtil.setBatch(dictionaryItemCodeMap);


    }

    @Override
    public String getUniqueId() {
        return IdWorker.getIdStr();
    }
}
