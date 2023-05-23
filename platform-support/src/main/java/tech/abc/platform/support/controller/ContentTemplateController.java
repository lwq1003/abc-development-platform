package tech.abc.platform.support.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.support.entity.ContentTemplate;
import tech.abc.platform.support.service.ContentTemplateService;
import tech.abc.platform.support.vo.ContentTemplateVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容模板 前端控制器
 *
 * @author wqliu
 */
@RestController
@RequestMapping("/support/contentTemplate")
@Slf4j
public class ContentTemplateController extends BaseController {
    @Autowired
    private ContentTemplateService contentTemplateService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        ContentTemplate entity = contentTemplateService.init();
        ContentTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "内容模板-新增")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ContentTemplateVO vo) {
        ContentTemplate entity = convert2Entity(vo);
        contentTemplateService.add(entity);
        ContentTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "内容模板-修改")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ContentTemplateVO vo) {
        ContentTemplate entity = convert2Entity(vo);
        // 此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        // 个别需展示的该类信息的地方可以重新查询后返回
        contentTemplateService.modify(entity);
        ContentTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "内容模板-删除")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        contentTemplateService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "内容模板-分页")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:query')")
    public ResponseEntity<Result> page(ContentTemplateVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        // 构造分页对象
        IPage<ContentTemplate> page = new Page<ContentTemplate>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setCategoryId(null);
        }

        // 构造查询条件
        QueryWrapper<ContentTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(ContentTemplate.class, queryVO, sortInfo);

        // 查询数据
        contentTemplateService.page(page, queryWrapper);
        // 转换vo
        IPage<ContentTemplateVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ContentTemplateVO> contentTemplateVOList = new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            ContentTemplateVO vo = convert2VO(page.getRecords().get(i));
            contentTemplateVOList.add(vo);
        }
        pageVO.setRecords(contentTemplateVOList);
        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "内容模板-列表")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:query')")
    public ResponseEntity<Result> list(ContentTemplateVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<ContentTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(ContentTemplate.class, queryVO, sortInfo);
        List<ContentTemplate> list = contentTemplateService.list(queryWrapper);
        // 转换vo
        List<ContentTemplateVO> contentTemplateVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ContentTemplateVO vo = convert2VO(list.get(i));
            contentTemplateVOList.add(vo);
        }
        return ResultUtil.success(contentTemplateVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    @SystemLog(value = "内容模板-详情")
    @PreAuthorize("hasPermission(null,'support:contentTemplate:query')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        ContentTemplate entity = contentTemplateService.query(id);
        ContentTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }


    private ContentTemplateVO convert2VO(ContentTemplate entity) {
        ContentTemplateVO vo = mapperFacade.map(entity, ContentTemplateVO.class);
        vo.setTypeName(dictionaryUtil.getNameByCode("ContentTemplateType", entity.getType()));


        return vo;
    }

    private ContentTemplate convert2Entity(ContentTemplateVO vo) {

        ContentTemplate entity = mapperFacade.map(vo, ContentTemplate.class);
        return entity;
    }

}
