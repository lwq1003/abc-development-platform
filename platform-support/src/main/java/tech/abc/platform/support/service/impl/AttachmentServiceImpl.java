package tech.abc.platform.support.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.utils.FileUtil;
import tech.abc.platform.oss.constant.FileConstant;
import tech.abc.platform.oss.entity.FileChunk;
import tech.abc.platform.oss.entity.FileInfo;
import tech.abc.platform.oss.service.ObjectStoreService;
import tech.abc.platform.support.entity.Attachment;
import tech.abc.platform.support.mapper.AttachmentMapper;
import tech.abc.platform.support.service.AttachmentService;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 附件 服务实现类
 *
 * @author wqliu
 * @date 2023-05-20
 */
@Service
@Slf4j
public class AttachmentServiceImpl extends BaseServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    @Autowired
    private ObjectStoreService objectStoreService;


    @Override
    public void beforeAdd(Attachment entity) {
        // 唯一性验证
    }

    @Override
    public void beforeModify(Attachment entity) {
        // 唯一性验证
    }

    @Override
    protected void afterRemove(Attachment entity) {
        // 清理磁盘文件
        objectStoreService.deleteFile(entity.getPath());
    }


    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Attachment> list = this.lambdaQuery().in(Attachment::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(Attachment entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadChunk(FileChunk fileChunk) {
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
        // 生成附件信息
        return create(fileInfo);

    }

    @Override
    public String uploadImage(MultipartFile image) {
        //生成唯一性标识
        String entityId = IdWorker.getIdStr();
        // 存储文件
        objectStoreService.uploadImage(image, entityId);
        String realName = entityId + image.getOriginalFilename();
        // 生成附件信息
        Attachment entity = new Attachment();
        entity.setName(image.getOriginalFilename());
        // 设置友好显示大小
        entity.setSize(FileUtil.getFileSize(image.getSize()));
        entity.setLength(image.getSize());
        // 设置存储相对路径
        entity.setPath(FileConstant.IMAGE_PATH+realName);
        entity.setType(image.getContentType());
        entity.setRealName(realName);
        entity.setEntity(entityId);
        add(entity);
        return entity.getId();
    }


    @Override
    public InputStream getFile(String id) {
        Attachment entity = query(id);
        return objectStoreService.getFile(entity.getPath());

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

        Attachment entity = new Attachment();
        entity.setName(fileInfo.getFilename());
        // 设置友好显示大小
        if (fileInfo.getTotalSize() != null) {
            entity.setSize(FileUtil.getFileSize(fileInfo.getTotalSize()));
            entity.setLength(fileInfo.getTotalSize());
        }
        // 设置存储相对路径
        entity.setPath(FilenameUtils.concat(relativePath, realName));
        entity.setType(fileInfo.getType());
        entity.setRealName(realName);
        entity.setEntity(fileInfo.getEntityId());
        add(entity);
        return entity.getId();
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


        Attachment entity = new Attachment();
        entity.setName(fileChunk.getFilename());
        // 设置友好显示大小
        if (fileChunk.getTotalSize() != null) {
            entity.setSize(FileUtil.getFileSize(fileChunk.getTotalSize()));
            entity.setLength(fileChunk.getTotalSize());
        }
        // 设置存储相对路径
        entity.setPath(FilenameUtils.concat(relativePath, realName));
        entity.setType(fileChunk.getFile().getContentType());
        entity.setRealName(realName);
        entity.setEntity(fileChunk.getEntityId());
        add(entity);

        return entity.getId();
    }


}


