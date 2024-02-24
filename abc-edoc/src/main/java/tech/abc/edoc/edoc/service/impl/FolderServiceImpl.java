package tech.abc.edoc.edoc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.edoc.edoc.entity.Document;
import tech.abc.edoc.edoc.entity.Folder;
import tech.abc.edoc.edoc.entity.PopDocument;
import tech.abc.edoc.edoc.enums.DocumentPermissionItemEnum;
import tech.abc.edoc.edoc.enums.PopDocumentTypeEnum;
import tech.abc.edoc.edoc.exception.FolderExceptionEnum;
import tech.abc.edoc.edoc.mapper.FolderMapper;
import tech.abc.edoc.edoc.service.DocumentService;
import tech.abc.edoc.edoc.service.FolderPermissionService;
import tech.abc.edoc.edoc.service.FolderService;
import tech.abc.platform.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.constant.TreeDefaultConstant;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import tech.abc.platform.common.utils.CommonUtil;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.system.service.OrganizationService;

/**
* 文件夹 服务实现类
*
* @author wqliu
* @date 2024-02-01
*/
@Service
@Slf4j
public class FolderServiceImpl extends BaseServiceImpl<FolderMapper, Folder> implements FolderService {

    public static final String DOCUMENT_SIZE_PROPERTY = "size";
    @Autowired
    private DocumentService documentService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private FolderPermissionService folderPermissionService;

    @Autowired
    private MapperFacade mapperFacade;
    @Override
    public Folder init() {
        Folder entity=new Folder();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        return entity;
    }

    @Override
    public void beforeAdd(Folder entity) {
        //权限检查
        folderPermissionService.checkPermission(entity.getParentId(), DocumentPermissionItemEnum.CREATE_FOLDER.name());

        //唯一性验证
        //验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(Folder::getName, entity.getName())
            .eq(Folder::getParentId, entity.getParentId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【名称】");
            }
        }

    }

    @Override
    public void beforeModify(Folder entity) {
        //唯一性验证
        //验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(Folder::getName, entity.getName())
                .eq(Folder::getParentId, entity.getParentId())
                .ne(Folder::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【名称】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Folder> list = this.lambdaQuery().in(Folder::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(Folder entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName (entity.getName() + " 副本");
    }

    @Override
    public void beforeRemove(Folder entity) {
        //权限检查
        folderPermissionService.checkPermission(entity.getId(), DocumentPermissionItemEnum.REMOVE_FOLDER.name());

        //删除文件
        QueryWrapper<Document> documentQueryWrapper = new QueryWrapper<>();
        documentQueryWrapper.lambda().eq(Document::getParentId, entity.getId());
        documentService.remove(documentQueryWrapper);
        //删除下级
        List<Folder> child = this.lambdaQuery().eq(Folder::getParentId, entity.getId()).list();
        for (Folder item : child) {
            remove(item.getId());
        }
    }



    @Override
    protected void afterRemove(Folder entity) {
        // 删除权限,这里放到删除后事件中是因为在删除子文件夹及子文件时需要先验证是否具备删除权限
        folderPermissionService.clearPermission(entity.getId());

    }

    @Override
    public List<String> getParentFolderId(String folderId) {
        List<String> list = new ArrayList<>(10);

        Folder folder = null;
        do {
            //获取当前实体
            folder = getEntity(folderId);
            //添加到集合
            list.add(folderId);
            //将当前实体父标识设置为实体标识
            folderId = folder.getParentId();
        }
        while (!TreeDefaultConstant.DEFAULT_TREE_ROOT_PARENT_ID.equals(folderId));
        return list;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void move(String srcId, String targetId, String retainPermission) {
        Folder entity=getEntity(srcId);
        //权限检查,有当前文件夹移动权限
        folderPermissionService.checkPermission(entity.getId(), DocumentPermissionItemEnum.MOVE_FOLDER.name());
        //权限检查，有目标文件夹的创建文件夹权限
        folderPermissionService.checkPermission(targetId, DocumentPermissionItemEnum.CREATE_FOLDER.name());

        //验证目标文件夹不能为当前文件夹的子文件夹，否则相关节点会从树中“断裂消失”
        List<String> parentFolderId = getParentFolderId(targetId);
        if(parentFolderId.contains(srcId)){
            throw new CustomException(FolderExceptionEnum.CANT_SELECT_CHILDREN);
        }


        //变更上级后保存
        entity.setParentId(targetId);
        modify(entity);
        //如不保留权限，则清空权限配置表中当前文件夹标识对应的权限信息
        if(StringUtils.isNotBlank(retainPermission) && YesOrNoEnum.NO.name().equals(retainPermission)){
            folderPermissionService.clearPermission(srcId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copy(String srcId, String targetId) {
        Folder entity=getEntity(srcId);
        //权限检查,有当前文件夹移动权限
        folderPermissionService.checkPermission(entity.getId(), DocumentPermissionItemEnum.COPY_FOLDER.name());
        //权限检查，有目标文件夹的创建文件夹权限
        folderPermissionService.checkPermission(targetId, DocumentPermissionItemEnum.CREATE_FOLDER.name());

        //为避免反复查询数据库，一次性加载到内存
        List<Folder> allFolderList=list();
        copyFolder(srcId, targetId,  allFolderList);


    }

    @Override
    public void rename(String id, String name) {
        Folder entity = getEntity(id);
        //权限检查
        folderPermissionService.checkPermission(entity.getId(), DocumentPermissionItemEnum.RENAME_FOLDER.name());
        //更名
        entity.setName(name);
        modify(entity);
    }

    @Override
    public List<PopDocument> getChildren(String id, String name, Boolean ignoreParent, SortInfo sortInfo) {
        Folder entity = getEntity(id);
        //权限检查
        folderPermissionService.checkPermission(entity.getId(), DocumentPermissionItemEnum.VIEW_FOLDER.name());


        // 获取子文件夹操作中会修改排序对象sortInfo，影响到获取文档的操作，因此先进行文档查询，再进行文件夹操作
        List<PopDocument> documentList=getDocumentList(id,name,ignoreParent,sortInfo);
        documentList.stream().forEach(x->x.setObjectType(PopDocumentTypeEnum.DOCUMENT.name()));


        //获取子文件夹
        List<PopDocument> folderList=getFolderList(id,name,ignoreParent,sortInfo);
        folderList.stream().forEach(x->{
            x.setObjectType(PopDocumentTypeEnum.FOLDER.name());
            x.setType("文件夹");

        });

        //合并
        folderList.addAll(documentList);
        //返回
        return folderList;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mixRemove(List<PopDocument> list) {
        list.stream().forEach(x->{
            if(x.getObjectType().equals(PopDocumentTypeEnum.FOLDER.name())){
                remove(x.getId());
            }else if(x.getObjectType().equals(PopDocumentTypeEnum.DOCUMENT.name())) {
                documentService.remove(x.getId());
            }
        });
    }

    @Override
    public void mixCopy(List<PopDocument> list, String targetFolderId) {
        list.stream().forEach(x->{
            if(x.getObjectType().equals(PopDocumentTypeEnum.FOLDER.name())){
                copy(x.getId(),targetFolderId);
            }else if(x.getObjectType().equals(PopDocumentTypeEnum.DOCUMENT.name())) {
                documentService.copy(x.getId(),targetFolderId);
            }
        });
    }

    @Override
    public void mixMove(List<PopDocument> list, String targetFolderId, String retainPermission) {
        list.stream().forEach(x->{
            if(x.getObjectType().equals(PopDocumentTypeEnum.FOLDER.name())){
                move(x.getId(),targetFolderId,retainPermission);
            }else if(x.getObjectType().equals(PopDocumentTypeEnum.DOCUMENT.name())) {
                documentService.move(x.getId(),targetFolderId);
            }
        });
    }

    /**
     * 获取下级文件夹
     * @param id 文件夹标识
     * @param sortInfo 排序
     * @return 下级文件夹列表
     */
    private List<PopDocument> getFolderList(String id,String name, Boolean ignoreParent, SortInfo sortInfo){
        QueryWrapper<Folder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(Folder::getName,name);
        if(!ignoreParent){
            queryWrapper.lambda().eq(Folder::getParentId,id);
        }

        //文件夹只支持按名称、创建时间、更新时间排序，其他排序方式重置为按名称
        List<String> sortTypeList=new ArrayList<>();
        sortTypeList.add("name");
        sortTypeList.add("createTime");
        sortTypeList.add("updateTime");
        if(!sortTypeList.contains(sortInfo.getField())){
            sortInfo.setField("name");
        }
        appendSort(sortInfo, queryWrapper);
        List<Folder> list=list(queryWrapper);
        return mapperFacade.mapAsList(list,PopDocument.class);
    }

    /**
     * 获取文档
     * @param id 文件夹标识
     * @param sortInfo 排序
     * @return 文档列表
     */
    private List<PopDocument> getDocumentList(String id,String name, Boolean ignoreParent, SortInfo sortInfo){
        QueryWrapper<Document> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(Document::getName,name);
        if(!ignoreParent){
            queryWrapper.lambda().eq(Document::getParentId,id);
        }
        // 如按大小排序，将其转换为按长度排序（因为4M会比200K还大）
        if(DOCUMENT_SIZE_PROPERTY.equals(sortInfo.getField())){
            sortInfo.setField("length");
        }

        appendSort(sortInfo, queryWrapper);
        List<Document> list=documentService.list(queryWrapper);
        return mapperFacade.mapAsList(list,PopDocument.class);
    }

    private void appendSort(SortInfo sortInfo, QueryWrapper queryWrapper) {
        //附加排序
        if(sortInfo !=null && StringUtils.isNotBlank(sortInfo.getField())) {
            //此处未使用注解，而是按照约定的规则，将驼峰命名转换为下划线，从而获取到数据库表字段名
            String orderField= CommonUtil.camelToUnderline(sortInfo.getField());
            if( sortInfo.getAscType()){
                queryWrapper.orderByAsc(orderField);
            }
            else {
                queryWrapper.orderByDesc(orderField);
            }

        }
    }

    /**
     * 文件夹复制
     * @param srcId 源标识
     * @param targetId 目标标识
     * @param allFolderList 系统所有文件夹列表
     */
    private void copyFolder(String srcId, String targetId,List<Folder> allFolderList) {
        //获取源文件夹
        Folder entity=getEntity(srcId);
        //复制文件夹
        Folder folder=new Folder();
        BeanUtils.copyProperties(entity,folder,"id");
        folder.setParentId(targetId);
        add(folder);
        //获取复制产生的新文件夹标识
        String parentId=folder.getId();
        //复制文档
        List<Document> documentList = documentService.lambdaQuery().eq(Document::getParentId, srcId).list();
        if(CollectionUtils.isNotEmpty(documentList)){
            documentList.stream().forEach(x->documentService.copy(x.getId(),parentId));
        }
        //子文件夹处理
        List<Folder> folderChildrenList= allFolderList.stream().filter(x->x.getParentId().equals(srcId))
                .collect(Collectors.toList());

        for(Folder item:folderChildrenList){
            //递归调用
            copyFolder(item.getId(),parentId,allFolderList);
        }
    }

}

