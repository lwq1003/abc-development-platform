package tech.abc.platform.oss.service;

import org.springframework.web.multipart.MultipartFile;
import tech.abc.platform.oss.entity.FileChunk;
import tech.abc.platform.oss.entity.FileInfo;

import java.io.InputStream;


/**
 * 对象存储服务接口
 *
 * @author wqliu
 * @date 2023-05-20
 */
public interface ObjectStoreService {

    /**
     * 上传文件块
     *
     * @param fileChunk 文件块
     */
    void uploadChunk(FileChunk fileChunk);

    /**
     * 合并文件块
     *
     * @param fileInfo 文件信息
     */
    void mergeChunks(FileInfo fileInfo);



    /**
     * 删除文件
     *
     * @param relativePath 文件相对路径
     * @return
     */
    void deleteFile(String relativePath);

    /**
     * 获取文件流
     *
     * @param relativePath 文件相对路径
     * @return 文件流
     */
    InputStream getFile(String relativePath);

    /**
     * 上传图片
     *
     * @param image 图像
     * @param id    id
     */
    void uploadImage(MultipartFile image, String id);


    /**
     * 获取文件全路径
     *
     * @param relativePath 相对路径
     * @return 完整路径
     */
    String getFullPath(String relativePath);


    /**
     * 生成相对存储路径
     *
     * @param moduleCode 模板编码
     * @param entityType 实体类型
     * @return 相对路径
     */
    String generateRelativePath(String moduleCode, String entityType);

}
