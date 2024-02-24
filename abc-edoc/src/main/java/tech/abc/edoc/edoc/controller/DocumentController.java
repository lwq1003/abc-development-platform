package tech.abc.edoc.edoc.controller;


import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.abc.edoc.edoc.service.FolderService;
import tech.abc.platform.common.annotation.AllowAll;
import tech.abc.platform.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.*;
import tech.abc.edoc.edoc.entity.Document;
import tech.abc.edoc.edoc.service.DocumentService;
import tech.abc.edoc.edoc.vo.DocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.oss.entity.FileChunk;
import tech.abc.platform.oss.entity.FileInfo;
import tech.abc.platform.oss.exception.FileExceptionEnum;
import tech.abc.platform.system.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 文档 前端控制器类
*
* @author wqliu
* @date 2024-02-03
*/
@RestController
@RequestMapping("/edoc/document")
@Slf4j
public class DocumentController extends BaseController {
    @Autowired
    private DocumentService documentService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private UserService userService;

    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Document entity=documentService.init();
        DocumentVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "文档-新增")
    @PreAuthorize("hasPermission(null,'edoc:document:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody DocumentVO vo) {
        Document entity=convert2Entity(vo);
        documentService.add(entity);
        DocumentVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "文档-修改")
    @PreAuthorize("hasPermission(null,'edoc:document:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody DocumentVO vo) {
        Document entity=convert2Entity(vo);
        documentService.modify(entity);
        DocumentVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "文档-删除")
    @PreAuthorize("hasPermission(null,'edoc:document:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        documentService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "文档-分页")
    @PreAuthorize("hasPermission(null,'edoc:document:query')")
    public ResponseEntity<Result> page(DocumentVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<Document> page = new Page<Document>(pageInfo.getPageNum(), pageInfo.getPageSize());

        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setParentId(null);
        }

        //构造查询条件
        QueryWrapper<Document> queryWrapper = QueryGenerator.generateQueryWrapper(Document.class,queryVO,sortInfo);

        //查询数据
        documentService.page(page, queryWrapper);
        //转换vo
        IPage<DocumentVO> pageVO = mapperFacade.map(page, IPage.class);
        List<DocumentVO>  documentVOList=convert2VO(page.getRecords());
        pageVO.setRecords(documentVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "文档-列表")
    @PreAuthorize("hasPermission(null,'edoc:document:query')")
    public ResponseEntity<Result> list(DocumentVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<Document> queryWrapper = QueryGenerator.generateQueryWrapper(Document.class, queryVO,sortInfo);
        List<Document> list= documentService.list(queryWrapper);
        //转换vo
        List<DocumentVO>  documentVOList=convert2VO(list);
        return ResultUtil.success(documentVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "文档-详情")
    @PreAuthorize("hasPermission(null,'edoc:document:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Document entity = documentService.query(id);
        DocumentVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "文档-复制新增")
    @PreAuthorize("hasPermission(null,'edoc:document:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        documentService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion


    //region 扩展操作

    /**
     * 上传文件块
     */
    @PostMapping("/upload")
    @PreAuthorize("hasPermission(null,'edoc:document:upload')")
    public ResponseEntity<Result> upload(FileChunkVO fileChunkVO, HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            FileChunk fileChunk = new FileChunk();
            BeanUtils.copyProperties(fileChunkVO, fileChunk);
            MultipartFile file = fileChunk.getFile();
            if (file == null) {
                throw new CustomException(FileExceptionEnum.FILE_CHUNK_IS_NULL);
            }
            documentService.uploadChunk(fileChunk);
        }
        return ResultUtil.success();
    }

    /**
     * 合并文件块
     */
    @PostMapping("/mergeChunks")
    @PreAuthorize("hasPermission(null,'edoc:document:upload')")
    public ResponseEntity<Result> mergeChunks(@RequestBody FileInfoVO fileInfoVO) {
        FileInfo fileInfo = new FileInfo();
        BeanUtils.copyProperties(fileInfoVO, fileInfo);
        documentService.mergeChunks(fileInfo);
        return ResultUtil.success();
    }


    /**
     * 更新文档
     */
    @PostMapping("/update")
    @PreAuthorize("hasPermission(null,'edoc:document:update')")
    public ResponseEntity<Result> update(FileChunkVO fileChunkVO, HttpServletRequest request,String documentId,
                                         String documentVersion,String versionTag) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            FileChunk fileChunk = new FileChunk();
            BeanUtils.copyProperties(fileChunkVO, fileChunk);
            MultipartFile file = fileChunk.getFile();
            if (file == null) {
                throw new CustomException(FileExceptionEnum.FILE_CHUNK_IS_NULL);
            }
            documentService.updateVersion(fileChunk,documentId,documentVersion,versionTag);
        }
        return ResultUtil.success();
    }


    /**
     * 下载
     *
     * @param id
     * @param response
     */
    @GetMapping("/{id}/download")
    @PreAuthorize("hasPermission(null,'edoc:document:download')")
    public void downloadFile(@PathVariable String id, HttpServletResponse response) {

        try (InputStream is = documentService.getFile(id);
             OutputStream os = response.getOutputStream();
        ) {
            Document document = documentService.getById(id);
            // 设置响应信息
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String fileName = URLEncoder.encode(document.getName(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(fileName));
            response.setHeader(HttpHeaders.CONTENT_LENGTH, document.getLength().toString());
            //复制
            IOUtils.copy(is, os);
            os.flush();

        } catch (Exception e) {
            throw new CustomException(FileExceptionEnum.FILE_DOWNLOAD_FAILURE);
        }
    }

    /**
     * 下载某一版本
     * @param id
     * @version 版本号
     * @param response
     */
    @GetMapping("/{id}/downloadVersion")
    @PreAuthorize("hasPermission(null,'edoc:document:download')")
    public void downloadFile(@PathVariable String id, String version,HttpServletResponse response) {

        try (InputStream is = documentService.getFile(id,version);
             OutputStream os = response.getOutputStream();
        ) {
            Document document = documentService.getById(id);
            // 设置响应信息
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String fileName = URLEncoder.encode(document.getName(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + encodeFileName(fileName));
            long fileSize = documentService.getFileSize(id, version);
            response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize));
            //复制
            IOUtils.copy(is, os);

            os.flush();

        } catch (Exception e) {
            throw new CustomException(FileExceptionEnum.FILE_DOWNLOAD_FAILURE);
        }
    }


    /**
     * 更名
     */
    @ApiOperation(value = "更名")
    @PutMapping("/{id}/rename")
    @SystemLog(value = "文档-更名")
    @PreAuthorize("hasPermission(null,'edoc:document:rename')")
    public ResponseEntity<Result> rename(@PathVariable String id, String name) {
        documentService.rename(id, name);
        return ResultUtil.success();
    }

    /**
     * 复制
     */
    @ApiOperation(value = "复制")
    @PostMapping("/copy")
    @SystemLog(value = "文档-复制")
    @PreAuthorize("hasPermission(null,'edoc:document:copy')")
    public ResponseEntity<Result> copy(String documentId,String targetFolderId) {
        documentService.copy(documentId, targetFolderId);
        return ResultUtil.success();
    }

    /**
     * 移动
     */
    @ApiOperation(value = "移动")
    @PutMapping("/move")
    @SystemLog(value = "文档-移动")
    @PreAuthorize("hasPermission(null,'edoc:document:move')")
    public ResponseEntity<Result> move(String documentId,String targetFolderId) {
        documentService.move(documentId, targetFolderId);
        return ResultUtil.success();
    }

    /**
     * 锁定
     */
    @PutMapping("/{id}/lock")
    @PreAuthorize("hasPermission(null,'edoc:document:unlock')")
    public ResponseEntity<Result> lock(@PathVariable String id) {
        documentService.lock(id);
        return ResultUtil.success();
    }

    /**
     * 解锁
     */
    @PutMapping("/{id}/unlock")
    @PreAuthorize("hasPermission(null,'edoc:document:unlock')")
    public ResponseEntity<Result> unlock(@PathVariable String id) {
        documentService.unlock(id);
        return ResultUtil.success();
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

    //endregion

    //region 文档预览相关

    /**
     * 获取访问令牌
     *
     * @param id 文档标识
     */
    @GetMapping("/{id}/getToken")
    @PreAuthorize("hasPermission(null,'edoc:document:preview')")
    public ResponseEntity<Result> getToken(@PathVariable String id) {
        String token = documentService.getToken(id);
        return ResultUtil.success(token);
    }

    /**
     * 获取访问令牌
     *
     * @param id 文档标识
     */
    @GetMapping("/{id}/getTokenWithTime")
    @PreAuthorize("hasPermission(null,'edoc:document:preview')")
    public ResponseEntity<Result> getToken(@PathVariable String id, BigDecimal validHours) {
        String token = documentService.getToken(id,validHours);
        return ResultUtil.success(token);
    }


    /**
     * 获取文件流
     *
     * @param id
     * @param response
     */
    @GetMapping("/getStream")
    @AllowAll
    public void getStream(String token, HttpServletResponse response) {
        try (InputStream is = documentService.getStreamByToken(token);
             OutputStream os = response.getOutputStream();
        ) {
            //复制
            IOUtils.copy(is, os);
            os.flush();
        } catch (Exception e) {
            throw new CustomException(FileExceptionEnum.FILE_READ_FAILURE, e.getMessage());
        }
    }

    /**
     * 获取指定版本文件流
     *
     * @param id
     * @param response
     */
    @GetMapping("/getStreamVersion")
    @AllowAll
    public void getStreamVersion(String token, String version,HttpServletResponse response) {
        try (InputStream is = documentService.getStreamByToken(token,version);
             OutputStream os = response.getOutputStream();
        ) {
            //复制
            IOUtils.copy(is, os);
            os.flush();
        } catch (Exception e) {
            throw new CustomException(FileExceptionEnum.FILE_READ_FAILURE, e.getMessage());
        }
    }

    /**
     * 恢复版本
     */
    @ApiOperation(value = "恢复版本")
    @PostMapping("/{id}/restore")
    @SystemLog(value = "文档-恢复版本")
    @PreAuthorize("hasPermission(null,'edoc:document:restore')")
    public ResponseEntity<Result> restore(@PathVariable  String id,String version) {
        documentService.restore(id, version);
        return ResultUtil.success();
    }


    //endregion


    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    protected DocumentVO convert2VO(Document entity){
        DocumentVO vo=mapperFacade.map(entity,DocumentVO.class);
        vo.setStatusName(dictionaryUtil.getNameByCode("DocumentStatus", entity.getStatus()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    protected List<DocumentVO> convert2VO(List<Document> entityList) {
        List<DocumentVO> voList = new ArrayList<>(entityList.size());

        // 获取 文件夹 集合
        List<String> folderList = entityList.stream().map(x -> x.getParentId()).collect(Collectors.toList());
        Map<String,String> folderNameMap = folderService.getNameMap(folderList);

        // 获取 锁定人 集合
        List<String> lockIdList = entityList.stream().map(x -> x.getLockId()).collect(Collectors.toList());
        Map<String,String> lockIdNameMap = userService.getNameMap(lockIdList);


        entityList.stream().forEach(x -> {
            DocumentVO vo = convert2VO(x);
            // 设置 文件夹
            vo.setFolderIdName(folderNameMap.get(x.getParentId()));
            // 设置 锁定人
            vo.setLockIdName(lockIdNameMap.get(x.getLockId()));
            voList.add(vo);
        });
        return voList;
    }


    private Document convert2Entity(DocumentVO vo){
        Document entity=mapperFacade.map(vo,Document.class);
        return entity;
    }

    //endregion
 }