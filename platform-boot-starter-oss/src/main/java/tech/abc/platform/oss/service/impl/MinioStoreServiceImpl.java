package tech.abc.platform.oss.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.minio.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.oss.constant.FileConstant;
import tech.abc.platform.oss.entity.FileChunk;
import tech.abc.platform.oss.entity.FileInfo;
import tech.abc.platform.oss.exception.FileExceptionEnum;
import tech.abc.platform.oss.service.BaseObjectStoreService;

import java.io.*;
import java.util.*;

/**
 * 使用minio 对象存储服务
 *
 * @author wqliu
 * @date 2023-11-21
 */
@Slf4j
public class MinioStoreServiceImpl extends BaseObjectStoreService {


    @Autowired
    private MinioClient minioClient;



    @Override
    public void uploadChunk(FileChunk fileChunk) {
        // 默认前缀使用唯一性编号id
        String filePrefix = fileChunk.getIdentifier();
        // 默认是正式目录
        String relativePath = generateRelativePath(fileChunk.getModuleCode(),fileChunk.getEntityType());
        String path = getFullPath(relativePath);
        // 如进行了分块
        if (fileChunk.getTotalChunks() > 1) {
            // 路径附加临时目录
            path = path + FileConstant.TEMP_PATH;
            // 前缀附加块编号
            filePrefix = filePrefix +  StringUtils.leftPad(fileChunk.getChunkNumber().toString(), 3, "0");
        }

        try {
            storeFile(fileChunk.getFile(),path + filePrefix + fileChunk.getFilename());
        } catch (Exception e) {

            throw new CustomException(FileExceptionEnum.FILE_CHUNK_STORE_ERROR);
        }
    }

    @Override
    public InputStream getFile(String relativePath) {

        String fullPath = getFullPath(relativePath);

        try {

            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(ossConfig.getMinioConfig().getBucketName())
                    .object(fullPath)
                    .build();
            GetObjectResponse response = minioClient.getObject(args);
            return response;
        }catch (Exception ex){
            log.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public void deleteFile(String relativePath) {
        try {
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                    .bucket(ossConfig.getMinioConfig().getBucketName())
                    .object(relativePath)
                    .build();
            minioClient.removeObject(args);
        }catch (Exception e){
            log.error("删除文件出错", e);

        }

    }

    @Override
    public void mergeChunks(FileInfo fileInfo) {
        TreeSet<String> objectList = new TreeSet<>();
        // 获取临时文件全路径
        String relativePath = generateRelativePath(fileInfo.getModuleCode(),fileInfo.getEntityType());
        String tempPath = relativePath+ FileConstant.TEMP_PATH;
        String tempFullPath = getFullPath(tempPath);
        try {
            // 获取该路径下以id开始的文件
            Iterable<Result<Item>> fileList = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(ossConfig.getMinioConfig().getBucketName())
                            .prefix(tempFullPath+fileInfo.getIdentifier())
                            .build());
            //定义合并数据源
            List<ComposeSource> sourceObjectList = new ArrayList<ComposeSource>();
            Iterator<Result<Item>> it = fileList.iterator();
            while (it.hasNext()) {
                Item item = it.next().get();
                objectList.add(item.objectName());
            }
            //合并文件
            String fileStoreName = fileInfo.getIdentifier() + fileInfo.getFilename();
            String fullPath = getFullPath(relativePath);
            // 添加合并数据源
            for (String object : objectList) {
                sourceObjectList.add(
                        ComposeSource.builder().bucket(ossConfig.getMinioConfig().getBucketName()).object(object).build());
            }
            minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(ossConfig.getMinioConfig().getBucketName())
                            .object(fullPath + fileStoreName)
                            .sources(sourceObjectList)
                            .build());


        } catch (Exception e) {
            log.error("合并文件块出错", e);
            throw new CustomException(FileExceptionEnum.FILE_CHUNK_MERGE_ERROR);
        } finally {
            // 删除临时文件
            for (String object : objectList) {
                deleteFile(object);
            }
        }
    }


    @Override
    public void uploadImage(MultipartFile image, String id) {

        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(ossConfig.getMinioConfig().getBucketName())
                    .contentType(image.getContentType())
                    .object(FileConstant.IMAGE_PATH+id + image.getOriginalFilename())
                    .stream(image.getInputStream(),image.getSize(), -1)
                    .build();
            minioClient.putObject(args);
        }catch (Exception e){
            log.error("存储文件块出错", e);
            throw new CustomException(FileExceptionEnum.FILE_CHUNK_STORE_ERROR);
        }

    }

    /**
     * 存储文件
     * @param file 文件
     * @param filePath 文件路径
     */
    private void storeFile(MultipartFile file, String filePath) {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(ossConfig.getMinioConfig().getBucketName())
                    .contentType(file.getContentType())
                    .object(filePath)
                    .stream(file.getInputStream(),file.getSize(),-1)
                    .build();
            minioClient.putObject(args);

        } catch (Exception e) {
            log.error("文件存储出错", e);
            throw new CustomException(FileExceptionEnum.FILE_STORE_ERROR);
        }
    }
}
