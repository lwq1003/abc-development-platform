package tech.abc.platform.support.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.utils.FileUtil;
import tech.abc.platform.oss.entity.FileChunk;
import tech.abc.platform.oss.service.ObjectStoreService;
import tech.abc.platform.support.entity.Attachment;
import tech.abc.platform.support.mapper.AttachmentMapper;
import tech.abc.platform.support.service.AttachmentService;

import java.io.InputStream;
import java.util.Calendar;
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

        // 生成存储路径
        String relativePath = generatePath(fileChunk);
        // 设置路径
        fileChunk.setPath(relativePath);
        // 存储文件
        objectStoreService.uploadChunk(fileChunk);
        // 如不分块
        if (fileChunk.getTotalChunks() == 1) {
            // 生成附件信息
            return create(fileChunk, relativePath);
        } else {
            // 如分块，判断是否是最后一块
            if (objectStoreService.checkIsLastChunk(fileChunk)) {
                // 合并文件
                objectStoreService.mergeChunks(fileChunk);
                // 生成附件信息
                return create(fileChunk, relativePath);
            }
        }
        return null;

    }

    @Override
    public String uploadImage(MultipartFile image) {

        String relativePath = "image/";

        String entityId = IdWorker.getIdStr();
        // 存储文件
        objectStoreService.uploadImage(image, entityId);


        String realName = entityId + image.getOriginalFilename();


        Attachment entity = new Attachment();
        entity.setName(image.getOriginalFilename());
        // 设置友好显示大小
        entity.setSize(FileUtil.getFileSize(image.getSize()));
        entity.setLength(image.getSize());
        // 设置存储相对路径
        entity.setPath(FilenameUtils.concat(relativePath, realName));
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

    @Override
    protected void afterRemove(Attachment entity) {
        // 清理磁盘文件
        objectStoreService.deleteFile(entity.getPath());
    }


    /**
     * 生成相对存储路径
     */
    private String generatePath(FileChunk fileChunk) {

        // 生成附件上传路径 根路径/模块名/实体类型名/年份/月份
        Calendar calendar = Calendar.getInstance();
        StringBuilder sbRelativePath = new StringBuilder()
                .append(fileChunk.getModuleCode()).append("/")
                .append(fileChunk.getEntityType()).append("/")
                .append(calendar.get(Calendar.YEAR)).append("/")
                // 月份左边补零到2位
                .append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, "0"))
                .append("/");
        return sbRelativePath.toString().toLowerCase();
    }


    private String create(FileChunk fileChunk, String relativePath) {
        String realName = fileChunk.getIdentifier() + fileChunk.getFilename();
        Attachment entity = new Attachment();
        entity.setName(fileChunk.getFilename());
        // 设置友好显示大小
        if (fileChunk.getTotalSize() != null) {
            entity.setSize(FileUtil.getFileSize(fileChunk.getTotalSize()));
            entity.setLength(fileChunk.getTotalSize());
        }
        // 设置存储相对路径
        entity.setPath(FilenameUtils.concat(relativePath, realName));
        entity.setType(fileChunk.getType());
        entity.setRealName(realName);
        entity.setEntity(fileChunk.getEntityId());
        add(entity);

        return entity.getId();
    }


}


