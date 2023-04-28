package tech.abc.platform.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.constant.CacheConstant;
import tech.abc.platform.common.utils.CacheUtil;
import tech.abc.platform.system.entity.*;
import tech.abc.platform.system.service.*;

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
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionItemService permissionService;

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    @Autowired
    private DictionaryItemService dictionaryItemService;


    @Override

    public void rebuildSystemCache() {
        // TODO:拆分为小颗粒的方法


        // 移除组织机构所有缓存
        cacheUtil.removePrefix(CacheConstant.ORGANIZATION_CACHE_PREFIX);
        // 获取所有组织结构数据
        List<Organization> orgList = organizationService.list();
        Map<String, String> orgMap = new HashMap<String, String>(128);
        for (Organization entity : orgList) {
            orgMap.put(CacheConstant.ORGANIZATION_CACHE_PREFIX + entity.getId(), entity.getName());
        }
        // 批量缓存组织机构
        cacheUtil.setBatch(orgMap);

        // 移除用户所有的缓存
        cacheUtil.removePrefix(CacheConstant.USER_CACHE_PREFIX);
        // 获取所有的用户数据
        List<User> userList = userService.list();

        Map<String, String> userMap = new HashMap<String, String>(100);
        for (User entity : userList) {
            userMap.put(CacheConstant.USER_CACHE_PREFIX + entity.getId(), entity.getName());
        }
        // 批量缓存用户数据
        cacheUtil.setBatch(userMap);

        // 移除权限所有的缓存
        cacheUtil.removePrefix(CacheConstant.PERMISSION_CACHE_PREFIX);
        // 获取所有的权限数据
        List<PermissionItem> permissionList = permissionService.list();

        Map<String, String> permissionMap = new HashMap<String, String>(2000);
        for (PermissionItem entity : permissionList) {
            permissionMap.put(CacheConstant.PERMISSION_CACHE_PREFIX + entity.getId(), entity.getName());
        }
        // 批量缓存权限数据
        cacheUtil.setBatch(permissionMap);

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


        // 移除内容模板分类所有缓存
        // jedisUtil.removePrefix(CacheConstant.CONTENT_TEMPLATE_CATEGORY_CACHE_PREFIX);
        // //获取所有数据
        // List<ContentTemplateCategory> contentTemplateCategoryList = contentTemplateCategoryService.list();
        // Map<String, String> contentTemplateCategoryMap = new HashMap<String, String>(200);
        // for (ContentTemplateCategory entity : contentTemplateCategoryList) {
        //     contentTemplateCategoryMap.put(CacheConstant.CONTENT_TEMPLATE_CATEGORY_CACHE_PREFIX + entity.getId(), entity.getName());
        // }
        // 批量缓存内容模板
        // jedisUtil.setBatch(contentTemplateCategoryMap);
    }
}
