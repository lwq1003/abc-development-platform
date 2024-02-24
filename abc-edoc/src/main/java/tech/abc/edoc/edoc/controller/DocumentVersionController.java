package tech.abc.edoc.edoc.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.edoc.edoc.vo.DocumentVO;
import tech.abc.platform.common.annotation.AllowAll;
import tech.abc.platform.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.edoc.edoc.entity.DocumentVersion;
import tech.abc.edoc.edoc.service.DocumentVersionService;
import tech.abc.edoc.edoc.vo.DocumentVersionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.system.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 文档版本 前端控制器类
*
* @author wqliu
* @date 2024-02-04
*/
@RestController
@RequestMapping("/edoc/documentVersion")
@Slf4j
public class DocumentVersionController extends BaseController {
    @Autowired
    private DocumentVersionService documentVersionService;

    @Autowired
    private UserService userService;

    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        DocumentVersion entity=documentVersionService.init();
        DocumentVersionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "文档版本-新增")
    @PreAuthorize("hasPermission(null,'edoc:documentVersion:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody DocumentVersionVO vo) {
        DocumentVersion entity=convert2Entity(vo);
        documentVersionService.add(entity);
        DocumentVersionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "文档版本-修改")
    @PreAuthorize("hasPermission(null,'edoc:documentVersion:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody DocumentVersionVO vo) {
        DocumentVersion entity=convert2Entity(vo);
        documentVersionService.modify(entity);
        DocumentVersionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "文档版本-删除")
    @PreAuthorize("hasPermission(null,'edoc:documentVersion:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        documentVersionService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "文档版本-分页")
    @PreAuthorize("hasPermission(null,'edoc:documentVersion:query')")
    public ResponseEntity<Result> page(DocumentVersionVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<DocumentVersion> page = new Page<DocumentVersion>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<DocumentVersion> queryWrapper = QueryGenerator.generateQueryWrapper(DocumentVersion.class,queryVO,sortInfo);

        //查询数据
        documentVersionService.page(page, queryWrapper);
        //转换vo
        IPage<DocumentVersionVO> pageVO = mapperFacade.map(page, IPage.class);
        List<DocumentVersionVO>  documentVersionVOList=convert2VO(page.getRecords());
        pageVO.setRecords(documentVersionVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "文档版本-列表")
    @PreAuthorize("hasPermission(null,'edoc:documentVersion:query')")
    public ResponseEntity<Result> list(DocumentVersionVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<DocumentVersion> queryWrapper = QueryGenerator.generateQueryWrapper(DocumentVersion.class, queryVO,sortInfo);
        List<DocumentVersion> list= documentVersionService.list(queryWrapper);
        //转换vo
        List<DocumentVersionVO>  documentVersionVOList=convert2VO(list);
        return ResultUtil.success(documentVersionVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "文档版本-详情")
    @PreAuthorize("hasPermission(null,'edoc:documentVersion:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        DocumentVersion entity = documentVersionService.query(id);
        DocumentVersionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "文档版本-复制新增")
    @PreAuthorize("hasPermission(null,'edoc:documentVersion:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        documentVersionService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作
    /**
     * 获取最新版本
     */

    @GetMapping("/getNewestVersion")
    @AllowAll
    public ResponseEntity<Result> getNewestVersion(String documentId) {
        DocumentVersion entity = documentVersionService.getNewestVersion(documentId);
        DocumentVersionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 获取版本列表，降序排列
     */

    @GetMapping("/getList")
    @PreAuthorize("hasPermission(null,'edoc:document:viewVersion')")
    public ResponseEntity<Result> getList(String documentId) {
        List<DocumentVersion> list = documentVersionService.getList(documentId);
        //转换vo
        List<DocumentVersionVO>  voList=convert2VO(list);
        return ResultUtil.success(voList);
    }
    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    protected DocumentVersionVO convert2VO(DocumentVersion entity){
        DocumentVersionVO vo=mapperFacade.map(entity,DocumentVersionVO.class);
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    protected List<DocumentVersionVO> convert2VO(List<DocumentVersion> entityList) {
        List<DocumentVersionVO> voList = new ArrayList<>(entityList.size());

        // 获取 创建人 集合
        List<String> createIdList = entityList.stream().map(x -> x.getCreateId()).collect(Collectors.toList());
        Map<String,String> createIdNameMap = userService.getNameMap(createIdList);


        entityList.stream().forEach(x -> {
            DocumentVersionVO vo = convert2VO(x);
            // 设置 上传人
            vo.setUploadUserName(createIdNameMap.get(x.getCreateId()));
            voList.add(vo);
        });
        return voList;
    }


    private DocumentVersion convert2Entity(DocumentVersionVO vo){
        DocumentVersion entity=mapperFacade.map(vo,DocumentVersion.class);
        return entity;
    }

    //endregion
 }