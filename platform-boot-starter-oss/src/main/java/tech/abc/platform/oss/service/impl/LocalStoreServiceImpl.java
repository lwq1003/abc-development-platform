package tech.abc.platform.oss.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.oss.config.OssConfig;
import tech.abc.platform.oss.constant.FileConstant;
import tech.abc.platform.oss.entity.FileChunk;
import tech.abc.platform.oss.entity.FileInfo;
import tech.abc.platform.oss.exception.FileExceptionEnum;
import tech.abc.platform.oss.service.BaseObjectStoreService;
import tech.abc.platform.oss.service.ObjectStoreService;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;


/**
 * 本地磁盘模式 对象存储服务
 *
 * @author wqliu
 * @date 2023-05-20
 */
@Slf4j
public class LocalStoreServiceImpl extends BaseObjectStoreService {

    @Autowired
    private OssConfig ossConfig;



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
            filePrefix = filePrefix + "-" + StringUtils.leftPad(fileChunk.getChunkNumber().toString(), 3, "0");
        }
        try {
            File file = new File(path + filePrefix + fileChunk.getFilename());
            FileUtils.copyInputStreamToFile(fileChunk.getFile().getInputStream(), file);
        } catch (IOException e) {
            log.error("存储文件块出错", e);
            throw new CustomException(FileExceptionEnum.FILE_CHUNK_STORE_ERROR);
        }
    }

    @Override
    public void mergeChunks(FileInfo fileInfo) {
        // 生成目标文件
        String relativePath = generateRelativePath(fileInfo.getModuleCode(),fileInfo.getEntityType());
        String fullPath = getFullPath(relativePath);

        File targetFile = new File(fullPath + fileInfo.getIdentifier() + fileInfo.getFilename());
        // 获取临时文件全路径
        String tempFullPath = fullPath + FileConstant.TEMP_PATH;

        // 获取该路径下以id开始的文件
        File dir = FileUtils.getFile(tempFullPath);
        FilenameFilter filenameFilter = new PrefixFileFilter(fileInfo.getIdentifier());
        String[] fileList = dir.list(filenameFilter);
        Arrays.sort(fileList);
        try {
            // 合并文件
            for (String file : fileList) {
                Path chunkPath = Paths.get(tempFullPath, file);
                FileUtils.writeByteArrayToFile(targetFile, Files.readAllBytes(chunkPath), true);
            }
        } catch (IOException e) {
            log.error("合并文件块出错", e);
            throw new CustomException(FileExceptionEnum.FILE_CHUNK_MERGE_ERROR);
        } finally {
            // 删除临时文件
            for (String file : fileList) {
                FileUtils.deleteQuietly(new File(tempFullPath + file));
            }
        }
    }


    @Override
    public InputStream getFile(String relativePath) {
        String fullPath = getFullPath(relativePath);
        try {
            return FileUtils.openInputStream(new File(fullPath));
        } catch (IOException e) {
            log.error("读取附件出错", e);
            throw new CustomException(FileExceptionEnum.FILE_READ_ERROR);
        }
    }

    @Override
    public void deleteFile(String relativePath) {
        String fullPath = getFullPath(relativePath);
        FileUtils.deleteQuietly(new File(fullPath));
    }


    @Override
    public void uploadImage(MultipartFile image, String id) {

        // 默认是正式目录
        String basePath = ossConfig.getBasePath();

        String path = FilenameUtils.concat(basePath, FileConstant.IMAGE_PATH);

        try {
            File file = new File(path + id + image.getOriginalFilename());
            FileUtils.copyInputStreamToFile(image.getInputStream(), file);
        } catch (IOException e) {
            log.error("存储文件块出错", e);
            throw new CustomException(FileExceptionEnum.FILE_CHUNK_STORE_ERROR);
        }
    }


}
