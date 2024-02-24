package tech.abc.edoc.edoc.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.edoc.edoc.entity.Document;
import tech.abc.edoc.edoc.entity.FolderPermission;
import tech.abc.edoc.edoc.enums.DocumentPermissionItemEnum;
import tech.abc.edoc.edoc.enums.FolderGrantPermissionModeEnum;
import tech.abc.edoc.edoc.mapper.FolderPermissionMapper;
import tech.abc.edoc.edoc.service.DocumentService;
import tech.abc.edoc.edoc.service.FolderPermissionService;
import tech.abc.edoc.edoc.service.FolderService;
import tech.abc.platform.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.system.service.OrganizationService;

/**
* 文件夹权限 服务实现类
*
* @author wqliu
* @date 2024-02-04
*/
@Service
@Slf4j
public class FolderPermissionServiceImpl extends BaseServiceImpl<FolderPermissionMapper, FolderPermission> implements FolderPermissionService {
    @Autowired
    private FolderService folderService;
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private FolderPermissionMapper folderPermissionMapper;

    @Autowired
    private DocumentService documentService;
    @Override
    public FolderPermission init() {
        FolderPermission entity=new FolderPermission();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        return entity;
    }

    @Override
    public void beforeAdd(FolderPermission entity) {
        //唯一性验证

    }

    @Override
    public void beforeModify(FolderPermission entity) {
        //唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        return result;
    }


    @Override
    protected void copyPropertyHandle(FolderPermission entity, String... value) {
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePermission(FolderPermission entity, List<String> permissionCodeList) {
        //权限检查
        if (entity.getObjectType().equals(FolderGrantPermissionModeEnum.USER_GROUP)) {
            checkPermission(entity.getFolderId(), DocumentPermissionItemEnum.GRANT_PERMISSION_BY_USER_GROUP.name());
        } else if (entity.getObjectType().equals(FolderGrantPermissionModeEnum.ORGANIZATION)) {
            checkPermission(entity.getFolderId(), DocumentPermissionItemEnum.GRANT_PERMISSION_BY_ORGANIZATION.name());
        }

        //先清空
        QueryWrapper<FolderPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(FolderPermission::getFolderId, entity.getFolderId())
                .eq(FolderPermission::getObjectId, entity.getObjectId())
                .eq(FolderPermission::getObjectType, entity.getObjectType());
        remove(queryWrapper);
        //再重新插入
        if (CollectionUtils.isNotEmpty(permissionCodeList)) {
            for (String permissionCode : permissionCodeList) {
                FolderPermission folderPermission = new FolderPermission();
                folderPermission.setFolderId(entity.getFolderId());
                folderPermission.setObjectId(entity.getObjectId());
                folderPermission.setObjectType(entity.getObjectType());
                folderPermission.setPermissionCode(permissionCode);
                add(folderPermission);
            }
        }
    }

    @Override
    public List<String> getFolderPermission(String folderId, String objectId, String objectType) {

        //获取当前文件夹所有上级
        List<String> folderList = folderService.getParentFolderId(folderId);

        //查询所有数据
        List<FolderPermission> list = this.lambdaQuery()
                .eq(FolderPermission::getObjectId, objectId)
                .eq(FolderPermission::getObjectType, objectType)
                .in(FolderPermission::getFolderId, folderList)
                .list();

        //结果处理
        return list.stream().map(x -> x.getPermissionCode()).distinct().collect(Collectors.toList());

    }

    @Override
    public List<String> getFolderPermission(String id) {
        //获取当前用户标识
        String userId = UserUtil.getId();
        //获取当前用户所有组织机构
        String organizationId = UserUtil.getOrganizationId();
        List<String> organizationIdList = organizationService.getParentId(organizationId);

        //获取当前文件夹所有父级文件夹
        List<String> folderIdList = folderService.getParentFolderId(id);
        log.info("文件夹标识列表：{}", JSON.toJSONString(folderIdList));
        // 按用户组授权模式查找
        List<String> listByUserGroup = folderPermissionMapper.getPermissionByUserGroup(folderIdList, userId);
        log.info("按用户组授权：{}", JSON.toJSONString(listByUserGroup));


        // 按组织机构授权模式
        List<String> listByOrganization = folderPermissionMapper.getPermissionByOrganization(folderIdList, organizationIdList);
        log.info("按组织就授权：{}", JSON.toJSONString(listByOrganization));

        //合并去重
        List<String> permissionCodeList = Stream.of(listByUserGroup, listByOrganization)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        return permissionCodeList;

    }

    @Override
    public List<String> getDocumentPermission(String id) {
        // 获取文件夹权限
        Document document=documentService.getById(id);
        String folderId=document.getParentId();
        List<String> folderPermission = getFolderPermission(folderId);
        //获取文档权限（包括关键字DOCUMENT且排除上传文档
        List<String> result = folderPermission.stream().filter(x -> x.contains("DOCUMENT") &&
                !x.equals(DocumentPermissionItemEnum.UPLOAD_DOCUMENT.name())).collect(Collectors.toList());
        return result;
    }

    @Override
    public void checkPermission(String folderId, String permissionCode) {
        List<String> permissionCodeList = getFolderPermission(folderId);
        if (CollectionUtils.isNotEmpty(permissionCodeList)) {
            if (!permissionCodeList.contains(permissionCode)) {
                throw new CustomException(CommonException.NO_PRIVILEGE);
            }
        } else {
            throw new CustomException(CommonException.NO_PRIVILEGE);
        }

    }

    @Override
    public boolean checkPermissionFlag(String folderId, String permissionCode) {
        List<String> permissionCodeList = getFolderPermission(folderId);
        if (CollectionUtils.isNotEmpty(permissionCodeList)) {
            if (permissionCodeList.contains(permissionCode)) {
                return true;
            }
            return  false;
        } else {
            return  false;
        }
    }

    @Override
    public void clearPermission(String folderId) {
        QueryWrapper<FolderPermission> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(FolderPermission::getFolderId,folderId);
        remove(queryWrapper);
    }

}

