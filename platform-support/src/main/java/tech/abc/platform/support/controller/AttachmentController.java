package tech.abc.platform.support.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.FileChunkVO;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.oss.entity.FileChunk;
import tech.abc.platform.oss.exception.FileExceptionEnum;
import tech.abc.platform.support.entity.Attachment;
import tech.abc.platform.support.service.AttachmentService;
import tech.abc.platform.support.vo.AttachmentVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 附件 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-20
 */
@RestController
@RequestMapping("/support/attachment")
@Slf4j
public class AttachmentController extends BaseController {


    public static final String IMAGE_PATH = "image/";
    @Autowired
    private AttachmentService attachmentService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Attachment entity = attachmentService.init();
        AttachmentVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "附件-新增")
    @PreAuthorize("hasPermission(null,'support:attachment:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody AttachmentVO vo) {
        Attachment entity = convert2Entity(vo);
        attachmentService.add(entity);
        AttachmentVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "附件-修改")
    @PreAuthorize("hasPermission(null,'support:attachment:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody AttachmentVO vo) {
        Attachment entity = convert2Entity(vo);
        attachmentService.modify(entity);
        AttachmentVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "附件-删除")
    @PreAuthorize("hasPermission(null,'support:attachment:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        attachmentService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "附件-分页")
    @PreAuthorize("hasPermission(null,'support:attachment:query')")
    public ResponseEntity<Result> page(AttachmentVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<Attachment> page = new Page<Attachment>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<Attachment> queryWrapper = QueryGenerator.generateQueryWrapper(Attachment.class, queryVO, sortInfo);

        // 查询数据
        attachmentService.page(page, queryWrapper);
        // 转换vo
        IPage<AttachmentVO> pageVO = mapperFacade.map(page, IPage.class);
        List<AttachmentVO> attachmentVOList = convert2VO(page.getRecords());
        pageVO.setRecords(attachmentVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "附件-详情")
    @PreAuthorize("hasPermission(null,'support:attachment:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Attachment entity = attachmentService.query(id);
        AttachmentVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "附件-复制新增")
    @PreAuthorize("hasPermission(null,'support:attachment:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        attachmentService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

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
            attachmentService.uploadChunk(fileChunk);
        }
        return ResultUtil.success();
    }

    /**
     * 根据实体标识获取附件列表
     */
    @GetMapping("/list")
    public ResponseEntity<Result> list(String entityId) {
        // 构造查询条件
        QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Attachment::getEntity, entityId)
                .orderByAsc(Attachment::getCreateTime);

        List<Attachment> attachmentList = attachmentService.list(queryWrapper);
        //  转化vo对象
        List<AttachmentVO> attachmentVOList = mapperFacade.mapAsList(attachmentList, AttachmentVO.class);
        return ResultUtil.success(attachmentVOList);
    }


    /**
     * 下载
     *
     * @param id
     * @param response
     */
    @GetMapping("/{id}/download")
    public void downloadFile(@PathVariable String id, HttpServletResponse response) {

        try (InputStream is = attachmentService.getFile(id);
             OutputStream os = response.getOutputStream();
        ) {
            Attachment attachment = attachmentService.getById(id);

            // 设置响应信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String fileName = URLEncoder.encode(attachment.getName(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);


            // 复制
            IOUtils.copy(is, os);
            os.flush();
        } catch (Exception e) {
            throw new CustomException(FileExceptionEnum.FILE_DOWNLOAD_FAILURE);
        }
    }


    @PostMapping("/uploadImage")
    public ResponseEntity<Result> uploadImage(MultipartFile image) {
        String id = attachmentService.uploadImage(image);
        String url = MessageFormat.format("http://localhost:4000/support/attachment/{0}/download", id);
        return ResultUtil.success(id);
        // ImageVO imageVO = new ImageVO();
        // imageVO.setUrl(url);
        // ImageUploadReturnVO imageUploadReturnVO = new ImageUploadReturnVO();
        // imageUploadReturnVO.setErrno(0);
        // imageUploadReturnVO.setData(imageVO);


        // Map<String, Object> result = new HashMap<>();
        // result.put("errno", 0);
        // Map<String, String> data = new HashMap<>();
        // data.put("url", url);
        // result.put("data", data);

        // return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}/getImage")
    public void getImage(@PathVariable("id") String id, HttpServletResponse response) {
        Attachment attachment = attachmentService.getById(id);
        String fileName = attachment.getName();
        String mimeType = HttpURLConnection.getFileNameMap().getContentTypeFor(fileName);
        mimeType = StringUtils.isBlank(mimeType) ? "application/octet-stream;" : mimeType;
        response.setContentType(mimeType);
        if (!mimeType.startsWith(IMAGE_PATH)) {
            response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName(fileName));
        }
        try (OutputStream os = response.getOutputStream();
             InputStream fileStream = attachmentService.getFile(id)) {
            IOUtils.copy(fileStream, os);
            os.flush();
        } catch (IOException e) {
            throw new CustomException(FileExceptionEnum.FILE_DOWNLOAD_FAILURE);
        }
    }

    /**
     * 前端正常上传文件（不分片）
     *
     * @param fileChunkVO
     * @param request
     * @return
     */
    @PostMapping("/uploadFile")
    public ResponseEntity<Result> uploadFile(AttachmentVO vo, HttpServletRequest request) {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        FileChunk fileChunk = new FileChunk();
        MultipartFile file = multiRequest.getFile("file");
        fileChunk.setFile(file);
        fileChunk.setFilename(file.getOriginalFilename());
        long size = file.getSize();
        fileChunk.setChunkSize(size);
        fileChunk.setTotalSize(size);
        fileChunk.setCurrentChunkSize(size);
        fileChunk.setEntityType(multiRequest.getParameter("entityType"));
        fileChunk.setEntityId(multiRequest.getParameter("entityId"));
        fileChunk.setModuleCode(multiRequest.getParameter("moduleCode"));

        fileChunk.setChunkNumber(1);
        fileChunk.setTotalChunks(1);
        fileChunk.setIdentifier(IdWorker.getIdStr());
        String id = attachmentService.uploadChunk(fileChunk);
        return ResultUtil.success(id);


    }

    private String encodeFileName(String fileName) {
        String encodeName = fileName;
        try {
            encodeName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            log.error("格式化文件名出错", e);
        }
        return encodeName;
    }


    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private AttachmentVO convert2VO(Attachment entity) {
        AttachmentVO vo = mapperFacade.map(entity, AttachmentVO.class);
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<AttachmentVO> convert2VO(List<Attachment> entityList) {
        List<AttachmentVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            AttachmentVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private Attachment convert2Entity(AttachmentVO vo) {
        Attachment entity = mapperFacade.map(vo, Attachment.class);
        return entity;
    }

    // endregion
}