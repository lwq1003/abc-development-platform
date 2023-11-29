package tech.abc.platform.support.service;

import org.springframework.web.multipart.MultipartFile;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.oss.entity.FileChunk;
import tech.abc.platform.oss.entity.FileInfo;
import tech.abc.platform.support.entity.Attachment;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 附件 服务接口类
 *
 * @author wqliu
 * @date 2023-05-20
 */
public interface AttachmentService extends BaseService<Attachment> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 上传文件块
     *
     * @param fileChunk
     * @return 如是最后一块, 返回附件实体实体标识, 否则返回null
     */
    String uploadChunk(FileChunk fileChunk);

    /**
     * 合并文件块
     * @param fileInfo 文件信息
     * @return {@link String} 文件标识
     */
    String mergeChunks(FileInfo fileInfo);

    /**
     * 上传图片
     *
     * @param image
     * @return 附件实体实体标识
     */
    String uploadImage(MultipartFile image);


    /**
     * 获取文件流
     *
     * @param id
     * @return 文件流
     */
    InputStream getFile(String id);


}

