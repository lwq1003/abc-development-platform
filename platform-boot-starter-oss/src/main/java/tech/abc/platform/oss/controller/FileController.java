package tech.abc.platform.oss.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.FileChunkVO;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.oss.config.OssConfig;
import tech.abc.platform.oss.entity.FileChunk;
import tech.abc.platform.oss.exception.FileExceptionEnum;
import tech.abc.platform.oss.service.ObjectStoreService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 对象上传控制器
 *
 * @author wqliu
 * @date 2023-05-20
 */
@RestController
@RequestMapping("/oss/file")
@Slf4j
public class FileController {

    public static final String DEMO = "demo/";
    @Autowired
    private ObjectStoreService objectStoreService;

    @Autowired
    private OssConfig ossConfig;

    /**
     * 分片上传
     */
    @PostMapping("/upload")
    public ResponseEntity<Result> upload(FileChunkVO fileChunkVO, HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            FileChunk fileChunk = new FileChunk();
            BeanUtils.copyProperties(fileChunkVO, fileChunk);
            MultipartFile file = fileChunk.getFile();
            if (file == null) {
                throw new CustomException(FileExceptionEnum.FILE_CHUNK_IS_NULL);
            }
            fileChunk.setPath(DEMO);
            objectStoreService.uploadChunk(fileChunk);
        }
        return ResultUtil.success();
    }

    @PostMapping("/{id}/mergeChunks")
    public ResponseEntity<Result> mergeChunks(@PathVariable String id, @RequestBody FileChunkVO chunkVO) {

        objectStoreService.mergeChunks(id, chunkVO.getTotalChunks(),
                ossConfig.getBasePath() + DEMO,
                chunkVO.getFilename());
        return ResultUtil.success();
    }

    /**
     * 下载
     *
     * @param id
     * @param response
     */
    @GetMapping("/{id}/download")
    public void downloadFile(@PathVariable String id, String name, HttpServletResponse response) {

        try (InputStream is = objectStoreService.getFile(DEMO + id + name);
             OutputStream os = response.getOutputStream();
        ) {
            response.setContentType("application/octet-stream;charset=UTF-8");
            // 复制
            IOUtils.copy(is, os);
            os.flush();
        } catch (Exception e) {
            throw new CustomException(FileExceptionEnum.FILE_DOWNLOAD_FAILURE);
        }
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> remove(@PathVariable("id") String id, String name) {
        objectStoreService.deleteFile(DEMO + id + name);
        return ResultUtil.success();
    }

}
