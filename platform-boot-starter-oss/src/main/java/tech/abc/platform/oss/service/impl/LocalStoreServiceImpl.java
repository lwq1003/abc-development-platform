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
import tech.abc.platform.oss.exception.FileExceptionEnum;
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
 * 对象存储服务
 *
 * @author wqliu
 * @date 2023-05-20
 */
@Slf4j
public class LocalStoreServiceImpl implements ObjectStoreService {

    @Autowired
    private OssConfig ossConfig;

    @Autowired
    private ObjectStoreService objectStoreService;


    @Override
    public void uploadChunk(FileChunk fileChunk) {
        // 默认前缀使用唯一性编号id
        String filePrefix = fileChunk.getIdentifier();
        // 默认是正式目录
        String path = getFullPath(fileChunk.getPath());
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
    public String uploadFile(MultipartFile file, String moduleCode, String entityType) {
        try {
            String relativePath = objectStoreService.generateRelativePath(moduleCode, entityType);
            String fullPath = objectStoreService.getFullPath(relativePath);
            String fileStoreName = IdWorker.getId() + file.getOriginalFilename();
            File storeFile = new File(fullPath + fileStoreName);
            FileUtils.copyInputStreamToFile(file.getInputStream(), storeFile);
            return relativePath + fileStoreName;
        } catch (IOException e) {
            log.error("存储文件出错", e);
            throw new CustomException(FileExceptionEnum.FILE_STORE_ERROR);
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

    /**
     * 获取文件全路径
     *
     * @param relativePath
     * @return
     */
    @Override
    public String getFullPath(String relativePath) {
        String basePath = ossConfig.getBasePath();
        return FilenameUtils.concat(basePath, relativePath);
    }


    @Override
    public void mergeChunks(FileChunk fileChunk) {
        // 生成目标文件
        String fullPath = getFullPath(fileChunk.getPath());
        File targetFile = new File(fullPath + fileChunk.getIdentifier() + fileChunk.getFilename());
        // 获取临时文件全路径
        String tempPath = fileChunk.getPath() + FileConstant.TEMP_PATH;
        String tempFullPath = getFullPath(tempPath);

        // 获取该路径下以id开始的文件
        File dir = FileUtils.getFile(tempFullPath);
        FilenameFilter filenameFilter = new PrefixFileFilter(fileChunk.getIdentifier());
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
    public boolean checkIsLastChunk(FileChunk fileChunk) {
        // TODO 此种处理方式不太踏实，是否会遇到并发问题？？？
        // 获取临时文件路径
        String tempPath = fileChunk.getPath() + FileConstant.TEMP_PATH;
        String fullPath = getFullPath(tempPath);
        // 获取该路径下以id开始的文件
        File dir = FileUtils.getFile(fullPath);
        FilenameFilter filenameFilter = new PrefixFileFilter(fileChunk.getIdentifier());
        String[] fileList = dir.list(filenameFilter);
        // 验证块数量是否匹配
        if (fileList != null && fileList.length == fileChunk.getTotalChunks()) {
            return true;
        }
        return false;
    }


    @Override
    public void mergeChunks(String id, Integer totalChunks, String path, String fileName) {
        // 获取文件列表
        // 获取临时文件路径
        String tempPath = path + FileConstant.TEMP_PATH;
        // 获取该路径下以id开始的文件
        File dir = FileUtils.getFile(tempPath);
        FilenameFilter filenameFilter = new PrefixFileFilter(id);
        String[] fileList = dir.list(filenameFilter);
        // 验证块数量是否匹配
        if (fileList == null || fileList.length != totalChunks) {
            throw new CustomException(FileExceptionEnum.FILE_CHUNK_NOT_EQUAL);
        }
        // 合并文件
        try {
            File targetFile = new File(path + id + fileName);
            Arrays.sort(fileList);
            for (String file : fileList) {
                Path chunkPath = Paths.get(tempPath, file);
                FileUtils.writeByteArrayToFile(targetFile, Files.readAllBytes(chunkPath), true);

            }
        } catch (IOException e) {
            log.error("合并文件块出错", e);
            throw new CustomException(FileExceptionEnum.FILE_CHUNK_MERGE_ERROR);
        } finally {
            // 删除临时文件
            for (String file : fileList) {
                FileUtils.deleteQuietly(new File(tempPath + file));
            }
        }

    }

    @Override
    public void uploadImage(MultipartFile image, String id) {

        // 默认是正式目录
        String basePath = ossConfig.getBasePath();

        String path = FilenameUtils.concat(basePath, "image/");

        try {
            File file = new File(path + id + image.getOriginalFilename());
            FileUtils.copyInputStreamToFile(image.getInputStream(), file);
        } catch (IOException e) {
            log.error("存储文件块出错", e);
            throw new CustomException(FileExceptionEnum.FILE_CHUNK_STORE_ERROR);
        }
    }


    @Override
    public String generateRelativePath(String moduleCode, String entityType) {

        // 生成附件上传路径 根路径/模块名/实体类型名/年份/月份
        Calendar calendar = Calendar.getInstance();
        StringBuilder sbRelativePath = new StringBuilder()
                .append(moduleCode).append("/")
                .append(entityType).append("/")
                .append(calendar.get(Calendar.YEAR)).append("/")
                // 月份左边补零到2位
                .append(StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MONTH) + 1), 2, "0"))
                .append("/");
        return sbRelativePath.toString().toLowerCase();
    }


}
