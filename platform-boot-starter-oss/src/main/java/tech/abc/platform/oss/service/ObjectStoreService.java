package tech.abc.platform.oss.service;

import org.springframework.web.multipart.MultipartFile;
import tech.abc.platform.oss.entity.FileChunk;

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
     * 获取文件流
     *
     * @param relativePath 文件相对路径
     * @return 文件流
     */
    InputStream getFile(String relativePath);


    /**
     * 删除文件
     *
     * @param relativePath 文件相对路径
     * @return
     */
    void deleteFile(String relativePath);


    /**
     * 合并
     *
     * @param fileChunk
     */
    void mergeChunks(FileChunk fileChunk);

    /**
     * 判断是否为最后一块
     *
     * @param fileChunk
     * @return true 是 false 否
     */
    boolean checkIsLastChunk(FileChunk fileChunk);


    /**
     * 合并文件块
     *
     * @param id          文件标识
     * @param totalChunks 总块数
     * @param path        路径
     * @param fileName    文件名
     */
    void mergeChunks(String id, Integer totalChunks, String path, String fileName);


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

    /**
     * 上传文件
     *
     * @param file       文件对象
     * @param moduleCode 模块编码
     * @param entityType 实体类型
     * @return 相对路径
     */
    String uploadFile(MultipartFile file, String moduleCode, String entityType);


}
