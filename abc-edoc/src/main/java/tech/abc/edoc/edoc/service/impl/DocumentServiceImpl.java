package tech.abc.edoc.edoc.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.edoc.edoc.config.FileTypeConfig;
import tech.abc.edoc.edoc.entity.Document;
import tech.abc.edoc.edoc.entity.DocumentVersion;
import tech.abc.edoc.edoc.enums.DocumentPermissionItemEnum;
import tech.abc.edoc.edoc.enums.DocumentStatusEnum;
import tech.abc.edoc.edoc.esrepository.DocumentRepository;
import tech.abc.edoc.edoc.exception.DocumentExceptionEnum;
import tech.abc.edoc.edoc.exception.DocumentVersionExceptionEnum;
import tech.abc.edoc.edoc.mapper.DocumentMapper;
import tech.abc.edoc.edoc.reader.FileReader;
import tech.abc.edoc.edoc.reader.FileReaderFactory;
import tech.abc.edoc.edoc.service.DocumentService;
import tech.abc.edoc.edoc.service.DocumentVersionService;
import tech.abc.edoc.edoc.service.FolderPermissionService;
import tech.abc.platform.cip.config.AppConfig;
import tech.abc.platform.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import tech.abc.platform.common.exception.FileException;
import tech.abc.platform.common.utils.FileUtil;
import tech.abc.platform.common.utils.JwtUtil;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.oss.config.OssConfig;
import tech.abc.platform.oss.entity.FileChunk;
import tech.abc.platform.oss.entity.FileInfo;
import tech.abc.platform.oss.service.ObjectStoreService;

/**
* 文档 服务实现类
*
* @author wqliu
* @date 2024-02-03
*/
@Service
@Slf4j
public class DocumentServiceImpl extends BaseServiceImpl<DocumentMapper, Document> implements DocumentService {

    public static final String DOCUMENT_INIT_VERSION = "1.0.0";
    @Autowired
    private FolderPermissionService folderPermissionService;

    @Autowired
    private ObjectStoreService objectStoreService;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentVersionService documentVersionService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OssConfig ossConfig;

    @Override
    public Document init() {
        Document entity=new Document();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        entity.setName("");
        entity.setLength(0L);
        entity.setReadCount(0);
        entity.setDownloadCount(0);
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(Document entity) {
        //唯一性验证
        //验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(Document::getName, entity.getName())
            .eq(Document::getParentId, entity.getParentId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【名称】");
            }
        }

    }

    @Override
    public void beforeModify(Document entity) {
        //唯一性验证
        //验证 名称 同节点下唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(Document::getName, entity.getName())
                .eq(Document::getParentId, entity.getParentId())
                .ne(Document::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST_IN_SAME_NODE,"【名称】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Document> list = this.lambdaQuery().in(Document::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(Document entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName (entity.getName() + " 副本");
    }

    @Override
    protected void afterAdd(Document entity) {
        //创建版本
        createInitVersion(entity);

        //异步处理索引创建，避免耗时及错误影响
        handleIndex(entity);
    }

    private void createInitVersion(Document entity) {
        DocumentVersion documentVersion=new DocumentVersion();
        documentVersion.setDocumentId(entity.getId());
        documentVersion.setDocumentVersion(DOCUMENT_INIT_VERSION);
        documentVersion.setPath(entity.getPath());
        documentVersionService.add(documentVersion);
    }

    private void createNewVersion(Document entity,String version,String versionTag) {
        DocumentVersion documentVersion=new DocumentVersion();
        documentVersion.setDocumentId(entity.getId());
        documentVersion.setPath(entity.getPath());
        documentVersion.setDocumentVersion(version);
        documentVersion.setVersionTag(versionTag);
        documentVersionService.add(documentVersion);
    }


    /**
     * 读取文件内容，建立或更新全文搜索索引
     * @param entity
     */
    private void handleIndex(Document entity) {
        //建立全文索引不应该影响到文档上传和保存流程，此处采用线程方式，并且不关注执行结果(某文档加入全文索引失败对业务影响极小）
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                createOrUpdateIndex(entity);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void createOrUpdateIndex(Document entity) {
        //读取文件内容
        String fileName = entity.getName();
        FileReader reader = FileReaderFactory.getReader(fileName);
        if (reader != null) {
            String content = reader.readFile(objectStoreService.getFullPath(entity.getPath()));
            entity.setContent(content);
        }
        documentRepository.save(entity);

    }

    @Override
    protected void afterModify(Document entity,Document originEntity) {

        //异步处理索引创建，避免耗时及错误影响
        handleIndex(entity);

    }

    @Override
    protected void beforeRemove(Document entity) {
        //权限检查
        folderPermissionService.checkPermission(entity.getParentId(), DocumentPermissionItemEnum.REMOVE_DOCUMENT.name());

    }

    @Override
    protected void afterRemove(Document entity) {
        //删除版本
        QueryWrapper<DocumentVersion> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(DocumentVersion::getDocumentId,entity.getId());
        documentVersionService.remove(queryWrapper);


        //删除全文索引不应该影响到主流程，此处采用线程方式，并且不关注执行结果(某文档加入全文索引失败对业务影响极小）
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                documentRepository.delete(entity);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadChunk(FileChunk fileChunk) {
        //权限检查
        folderPermissionService.checkPermission(fileChunk.getEntityId(), DocumentPermissionItemEnum.UPLOAD_DOCUMENT.name());

        // 附件上传比较特殊，传输的数据是文件块，先根据文件块处理文件，然后生成附件实体数据

        // 上传文件块
        objectStoreService.uploadChunk(fileChunk);
        // 如只有一块，直接生成附件
        if (fileChunk.getTotalChunks() == 1) {
            // 生成附件信息
            return create(fileChunk);
        }
        return null;



    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String mergeChunks(FileInfo fileInfo) {

        // 合并文件
        objectStoreService.mergeChunks(fileInfo);

        if(StringUtils.isBlank(fileInfo.getDocumentId())){
            // 正常上传，生成附件信息
            return create(fileInfo);
        }else{
            // 版本更新
            return modify(fileInfo);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateVersion(FileChunk fileChunk, String documentId, String documentVersion, String versionTag) {
        //权限检查
        folderPermissionService.checkPermission(fileChunk.getEntityId(),
                DocumentPermissionItemEnum.UPDATE_DOCUMENT.name());

        //判断版本是否高于最新版本
        DocumentVersion version=documentVersionService.getNewestVersion(documentId);
        if(version!=null && version.getDocumentVersion().compareTo(documentVersion)>=0){
            throw new CustomException(DocumentVersionExceptionEnum.VERSION_MUST_GREATER_THAN_NEWEST,
                    version.getDocumentVersion());

        }

        // 上传比较特殊，传输的数据是文件块，先根据文件块处理文件，然后生成实体数据

        //生成存储路径
        String relativePath = objectStoreService.generateRelativePath(fileChunk.getModuleCode(), fileChunk.getEntityType());
        //设置路径
        fileChunk.setPath(relativePath);
        //存储文件
        objectStoreService.uploadChunk(fileChunk);
        // 如只有一块，直接生成附件
        if (fileChunk.getTotalChunks() == 1) {
            //生成附件信息
            modify(fileChunk, relativePath, documentId,documentVersion,versionTag);
        }

    }

    @Override
    public InputStream getFile(String id, String version) {
        Document entity = query(id);
        //权限检查
        folderPermissionService.checkPermission(entity.getParentId(), DocumentPermissionItemEnum.DOWNLOAD_DOCUMENT.name());

        DocumentVersion documentVersion = documentVersionService.lambdaQuery().eq(DocumentVersion::getDocumentId, id)
                .eq(DocumentVersion::getDocumentVersion, version).one();
        if(documentVersion==null){
            throw new CustomException(DocumentVersionExceptionEnum.VERSION_NOT_FOUND);
        }
        return objectStoreService.getFile(documentVersion.getPath());
    }

    @Override
    public long getFileSize(String id, String version) {
        Document entity = query(id);

        DocumentVersion documentVersion = documentVersionService.lambdaQuery().eq(DocumentVersion::getDocumentId, id)
                .eq(DocumentVersion::getDocumentVersion, version).one();
        if(documentVersion==null){
            throw new CustomException(DocumentVersionExceptionEnum.VERSION_NOT_FOUND);
        }
        String filePath=objectStoreService.getFullPath(documentVersion.getPath());
        return FileUtils.sizeOf(new File(filePath));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void restore(String id, String version) {
        //获取最新版本
        Document entity = getEntity(id);
        //权限检查
        folderPermissionService.checkPermission(entity.getParentId(), DocumentPermissionItemEnum.RESTORE_DOCUMENT_VERSION.name());


        //获取要恢复的版本信息
        DocumentVersion documentVersion = documentVersionService.lambdaQuery().eq(DocumentVersion::getDocumentId, id)
                .eq(DocumentVersion::getDocumentVersion, version).one();
        if(documentVersion==null){
            throw new CustomException(DocumentVersionExceptionEnum.VERSION_NOT_FOUND);
        }

        //设置路径、长度、体积
        entity.setPath(documentVersion.getPath());
        long fileSize = getFileSize(id, version);
        entity.setLength(fileSize);
        entity.setSize(FileUtil.getFileSize(fileSize));
        //保存文档
        modify(entity);
        //获取最新版本
        DocumentVersion newestVersion = documentVersionService.getNewestVersion(id);
        //生成新版本
        String newVersion=generateNewVersion(newestVersion.getDocumentVersion());
        String versionTag="恢复版本"+version;
        createNewVersion(entity,newVersion,versionTag);

    }

    /**
     * 生成新版本
     * @param version 当前版本号
     * @return
     */
    private String generateNewVersion(String version) {
        String[] versionArray = StringUtils.split(version,".");
        return versionArray[0]+"."+versionArray[1]+"."+(Integer.valueOf(versionArray[2])+1);
    }


    @Override
    public InputStream getStreamByToken(String token, String version) {
        //验证令牌
        jwtUtil.verifyToken(token);
        //从令牌中获取文件标识
        String documentId = jwtUtil.getSubject(token);
        Document entity = getEntity(documentId);

        DocumentVersion documentVersion = documentVersionService.lambdaQuery().eq(DocumentVersion::getDocumentId, documentId)
                .eq(DocumentVersion::getDocumentVersion, version).one();
        if(documentVersion==null){
            throw new CustomException(DocumentVersionExceptionEnum.VERSION_NOT_FOUND);
        }
        return objectStoreService.getFile(documentVersion.getPath());

    }


    @Override
    public InputStream getFile(String id) {
        Document entity = query(id);
        //权限检查
        folderPermissionService.checkPermission(entity.getParentId(), DocumentPermissionItemEnum.DOWNLOAD_DOCUMENT.name());
        //更新下载量
        entity.setDownloadCount(entity.getDownloadCount() + 1);
        modify(entity);
        return objectStoreService.getFile(entity.getPath());

    }




    /**
     * 创建附件——依据文件块信息
     * @param fileChunk 文件块
     * @return {@link String} 附件标识
     */
    private String create(FileChunk fileChunk) {
        String realName = fileChunk.getIdentifier() + fileChunk.getFilename();
        // 存储相对路径
        String relativePath = objectStoreService.generateRelativePath(fileChunk.getModuleCode(),fileChunk.getEntityType());


        Document entity = new Document();
        entity.setName(fileChunk.getFilename());
        // 设置友好显示大小
        if (fileChunk.getTotalSize() != null) {
            entity.setSize(FileUtil.getFileSize(fileChunk.getTotalSize()));
            entity.setLength(fileChunk.getTotalSize());
        }
        // 设置存储相对路径
        entity.setPath(FilenameUtils.concat(relativePath, realName));
        //设置文档类型
        String suffix = FilenameUtils.getExtension(fileChunk.getFilename());
        String typeName = FileTypeConfig.getInstance().getTypeName(suffix);
        entity.setType(typeName);
        entity.setRealName(realName);
        entity.setParentId(fileChunk.getEntityId());
        entity.setReadCount(0);
        entity.setDownloadCount(0);
        entity.setStatus(DocumentStatusEnum.NORMAL.name());
        entity.setUploadTime(LocalDateTime.now());
        add(entity);

        return entity.getId();
    }

    /**
     * 创建附件——依据文件信息
     * @param fileInfo 文件
     * @return {@link String} 附件标识
     */
    private String create(FileInfo fileInfo) {
        //实际存储文件名
        String realName = fileInfo.getIdentifier() + fileInfo.getFilename();
        // 存储相对路径
        String relativePath = objectStoreService.generateRelativePath(fileInfo.getModuleCode(),fileInfo.getEntityType());

        Document entity = new Document();
        entity.setName(fileInfo.getFilename());
        // 设置友好显示大小
        if (fileInfo.getTotalSize() != null) {
            entity.setSize(FileUtil.getFileSize(fileInfo.getTotalSize()));
            entity.setLength(fileInfo.getTotalSize());
        }
        // 设置存储相对路径
        entity.setPath(FilenameUtils.concat(relativePath, realName));
        //设置文档类型
        String suffix = FilenameUtils.getExtension(fileInfo.getFilename());
        String typeName = FileTypeConfig.getInstance().getTypeName(suffix);
        entity.setType(typeName);
        entity.setRealName(realName);
        entity.setParentId(fileInfo.getEntityId());
        entity.setReadCount(0);
        entity.setDownloadCount(0);
        entity.setStatus(DocumentStatusEnum.NORMAL.name());
        entity.setUploadTime(LocalDateTime.now());
        add(entity);

        return entity.getId();
    }


    private String modify(FileChunk fileChunk, String relativePath,String documentId,String version,String versionTag) {
        String realName = fileChunk.getIdentifier() + fileChunk.getFilename();
        Document entity =getEntity(documentId);
        //设置友好显示大小
        if (fileChunk.getTotalSize() != null) {
            entity.setSize(FileUtil.getFileSize(fileChunk.getTotalSize()));
            entity.setLength(fileChunk.getTotalSize());
        }
        //设置文档类型
        String suffix = FilenameUtils.getExtension(fileChunk.getFilename());
        String typeName = FileTypeConfig.getInstance().getTypeName(suffix);
        entity.setType(typeName);
        //设置存储相对路径
        entity.setPath(FilenameUtils.concat(relativePath, realName));
        entity.setRealName(realName);

        modify(entity);

        //插入版本
        createNewVersion(entity,version,versionTag);

        return entity.getId();
    }

    /**
     * 更新版本——依据文件信息
     * @param fileInfo 文件
     * @return {@link String} 附件标识
     */
    private String modify(FileInfo fileInfo) {
        //实际存储文件名
        String realName = fileInfo.getIdentifier() + fileInfo.getFilename();
        // 存储相对路径
        String relativePath = objectStoreService.generateRelativePath(fileInfo.getModuleCode(),fileInfo.getEntityType());

        Document entity =getEntity(fileInfo.getDocumentId());

        // 设置友好显示大小
        if (fileInfo.getTotalSize() != null) {
            entity.setSize(FileUtil.getFileSize(fileInfo.getTotalSize()));
            entity.setLength(fileInfo.getTotalSize());
        }
        // 设置存储相对路径
        entity.setPath(FilenameUtils.concat(relativePath, realName));
        //设置文档类型
        entity.setRealName(realName);
        entity.setParentId(fileInfo.getEntityId());
        entity.setUploadTime(LocalDateTime.now());
        modify(entity);

        //插入版本
        createNewVersion(entity,fileInfo.getDocumentVersion(),fileInfo.getVersionTag());

        return entity.getId();
    }



    @Override
    public IPage<Document> pageWithPermission(IPage<Document> page, Wrapper<Document> queryWrapper, String folderId) {

        //增加权限验证
        if (StringUtils.isNotBlank(folderId)) {
            folderPermissionService.checkPermission(folderId,
                    DocumentPermissionItemEnum.VIEW_FOLDER.name());
            return super.page(page, queryWrapper);
        } else {
            throw new CustomException(DocumentExceptionEnum.FOLDER_ID_IS_NULL);
        }


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copy(String documentId, String folderId) {
        //权限检查
        Document srcDocument = getEntity(documentId);
        folderPermissionService.checkPermission(srcDocument.getParentId(),
                DocumentPermissionItemEnum.COPY_DOCUMENT.name());
        folderPermissionService.checkPermission(folderId,
                DocumentPermissionItemEnum.UPLOAD_DOCUMENT.name());


        //构建存储路径
        String basePath = ossConfig.getBasePath();
        String relativePath = objectStoreService.generateRelativePath("edoc", "document");
        String fullPath = FilenameUtils.concat(basePath, relativePath);
        String id = IdWorker.getIdStr();
        String realName = id + srcDocument.getName();
        String path = FilenameUtils.concat(fullPath, realName);
        File targetFile = new File(path);
        //获取源文档
        InputStream srcFileStream = getFile(documentId);
        //磁盘文档复制
        try {
            FileUtils.copyToFile(srcFileStream, targetFile);
        } catch (IOException e) {
            throw new CustomException(FileException.COPY_FAILURE, e.getMessage());
        }

        //库表记录复制
        Document document = new Document();
        BeanUtils.copyProperties(srcDocument, document, "id");
        document.setParentId(folderId);
        document.setPath(FilenameUtils.concat(relativePath, realName));
        document.setRealName(realName);
        //阅读量和下载量置为0
        document.setReadCount(0);
        document.setDownloadCount(0);
        add(document);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void move(String documentId, String folderId) {

        //权限检查
        Document srcDocument = getEntity(documentId);
        folderPermissionService.checkPermission(srcDocument.getParentId(),
                DocumentPermissionItemEnum.MOVE_DOCUMENT.name());
        folderPermissionService.checkPermission(folderId,
                DocumentPermissionItemEnum.UPLOAD_DOCUMENT.name());

        // 文档修改
        srcDocument.setParentId(folderId);
        modify(srcDocument);

    }

    @Override
    public String getToken(String id) {
        //权限检查,注意，这里是系统内预览，因此验证预览权限
        Document document = getEntity(id);
        folderPermissionService.checkPermission(document.getParentId(),
                DocumentPermissionItemEnum.PREVIEW_DOCUMENT.name());

        //生成令牌,有效期5分钟
        String token = jwtUtil.generateTokenWithSubject(id, 60 * 5);
        return token;
    }

    @Override
    public String getToken(String id, BigDecimal validHours) {
        //权限检查，注意，这里是系统外分享，因此验证分享权限
        Document document = getEntity(id);
        folderPermissionService.checkPermission(document.getParentId(),
                DocumentPermissionItemEnum.SHARE_DOCUMENT.name());

        //生成令牌
        String token = jwtUtil.generateTokenWithSubject(id, validHours.multiply(new BigDecimal(3600)).longValue() );
        return token;
    }

    @Override
    public InputStream getStreamByToken(String token) {
        //验证令牌
        jwtUtil.verifyToken(token);
        //从令牌中获取文件标识
        String documentId = jwtUtil.getSubject(token);
        Document entity = getEntity(documentId);
        //更新浏览次数
        entity.setReadCount(entity.getReadCount() + 1);
        modify(entity);
        return objectStoreService.getFile(entity.getPath());

    }

    @Override
    public void lock(String id) {
        //权限检查
        Document document = getEntity(id);
        folderPermissionService.checkPermission(document.getParentId(),
                DocumentPermissionItemEnum.LOCK_DOCUMENT.name());

        document.setStatus(DocumentStatusEnum.LOCKED.name());
        document.setLockId(UserUtil.getId());
        document.setLockTime(LocalDateTime.now());

        modify(document);



    }

    @Override
    public void unlock(String id) {
        //权限检查
        Document document = getEntity(id);
        folderPermissionService.checkPermission(document.getParentId(),
                DocumentPermissionItemEnum.UNLOCK_DOCUMENT.name());

        document.setStatus(DocumentStatusEnum.NORMAL.name());
        document.setLockId(null);
        document.setLockTime(null);

        modify(document);

    }



    @Override
    public void rename(String id, String name) {
        Document entity = getEntity(id);
        //权限检查
        folderPermissionService.checkPermission(entity.getParentId(), DocumentPermissionItemEnum.RENAME_DOCUMENT.name());
        //更名
        entity.setName(name);
        modify(entity);

        //异步处理索引创建，避免耗时及错误影响
        handleIndex(entity);
    }





}

