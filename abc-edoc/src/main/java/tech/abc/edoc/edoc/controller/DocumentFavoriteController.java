package tech.abc.edoc.edoc.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.platform.common.annotation.AllowAll;
import tech.abc.platform.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.edoc.edoc.entity.DocumentFavorite;
import tech.abc.edoc.edoc.service.DocumentFavoriteService;
import tech.abc.edoc.edoc.vo.DocumentFavoriteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* 文档收藏夹 前端控制器类
*
* @author wqliu
* @date 2024-02-04
*/
@RestController
@RequestMapping("/edoc/documentFavorite")
@Slf4j
public class DocumentFavoriteController extends BaseController {
    @Autowired
    private DocumentFavoriteService documentFavoriteService;


    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        DocumentFavorite entity=documentFavoriteService.init();
        DocumentFavoriteVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "文档收藏夹-新增")
    @PreAuthorize("hasPermission(null,'edoc:documentFavorite:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody DocumentFavoriteVO vo) {
        DocumentFavorite entity=convert2Entity(vo);
        //添加当前用户
        entity.setUserId(UserUtil.getId());
        documentFavoriteService.add(entity);
        DocumentFavoriteVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "文档收藏夹-修改")
    @PreAuthorize("hasPermission(null,'edoc:documentFavorite:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody DocumentFavoriteVO vo) {
        DocumentFavorite entity=convert2Entity(vo);
        documentFavoriteService.modify(entity);
        DocumentFavoriteVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "文档收藏夹-删除")
    @PreAuthorize("hasPermission(null,'edoc:documentFavorite:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        documentFavoriteService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "文档收藏夹-分页")
    @PreAuthorize("hasPermission(null,'edoc:documentFavorite:query')")
    public ResponseEntity<Result> page(DocumentFavoriteVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<DocumentFavorite> page = new Page<DocumentFavorite>(pageInfo.getPageNum(), pageInfo.getPageSize());


        //构造查询条件
        QueryWrapper<DocumentFavorite> queryWrapper = QueryGenerator.generateQueryWrapper(DocumentFavorite.class,queryVO,sortInfo);

        //查询数据
        documentFavoriteService.page(page, queryWrapper);
        //转换vo
        IPage<DocumentFavoriteVO> pageVO = mapperFacade.map(page, IPage.class);
        List<DocumentFavoriteVO>  documentFavoriteVOList=convert2VO(page.getRecords());
        pageVO.setRecords(documentFavoriteVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "文档收藏夹-列表")
    @PreAuthorize("hasPermission(null,'edoc:documentFavorite:query')")
    public ResponseEntity<Result> list(DocumentFavoriteVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<DocumentFavorite> queryWrapper = QueryGenerator.generateQueryWrapper(DocumentFavorite.class, queryVO,sortInfo);
        List<DocumentFavorite> list= documentFavoriteService.list(queryWrapper);
        //转换vo
        List<DocumentFavoriteVO>  documentFavoriteVOList=convert2VO(list);
        return ResultUtil.success(documentFavoriteVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "文档收藏夹-详情")
    @PreAuthorize("hasPermission(null,'edoc:documentFavorite:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        DocumentFavorite entity = documentFavoriteService.query(id);
        DocumentFavoriteVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "文档收藏夹-复制新增")
    @PreAuthorize("hasPermission(null,'edoc:documentFavorite:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        documentFavoriteService.addByCopy(id);
        return ResultUtil.success();
    }



    //endregion

    //region 扩展操作
    /**
     * 获取收藏列表
     */
    @GetMapping("/getFavoriteList")
    @SystemLog(value = "文档收藏夹-列表")
    @AllowAll
    public ResponseEntity<Result> getFavoriteList(String name) {
        List<DocumentFavorite> list= documentFavoriteService.getFavoriteList(name);
        List<DocumentFavoriteVO> voList=mapperFacade.mapAsList(list,DocumentFavoriteVO.class);
        return ResultUtil.success(list);
    }
    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    protected DocumentFavoriteVO convert2VO(DocumentFavorite entity){
        DocumentFavoriteVO vo=mapperFacade.map(entity,DocumentFavoriteVO.class);
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    protected List<DocumentFavoriteVO> convert2VO(List<DocumentFavorite> entityList) {
        List<DocumentFavoriteVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            DocumentFavoriteVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private DocumentFavorite convert2Entity(DocumentFavoriteVO vo){
        DocumentFavorite entity=mapperFacade.map(vo,DocumentFavorite.class);
        return entity;
    }

    //endregion
 }