package tech.abc.platform.support.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.support.entity.PortalTemplate;
import tech.abc.platform.support.service.PortalTemplateService;
import tech.abc.platform.support.vo.PortalTemplateVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 门户模板 前端控制器
 * @author wqliu
 * *
 */
@RestController
@RequestMapping("/support/portalTemplate")
@Slf4j
public class PortalTemplateController extends BaseController {
 @Autowired
 private PortalTemplateService portalTemplateService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        PortalTemplate entity=portalTemplateService.init();
        PortalTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "门户模板-新增")
    @PreAuthorize("hasPermission(null,'support:portalTemplate:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody PortalTemplateVO vo) {
        PortalTemplate entity=convert2Entity(vo);
        portalTemplateService.add(entity);
        PortalTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "门户模板-修改")
    @PreAuthorize("hasPermission(null,'support:portalTemplate:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody PortalTemplateVO vo)  {
        PortalTemplate entity=convert2Entity(vo);
        //此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        //个别需展示的该类信息的地方可以重新查询后返回
        portalTemplateService.modify(entity);
        PortalTemplateVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "门户模板-删除")
    @PreAuthorize("hasPermission(null,'support:portalTemplate:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        portalTemplateService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "门户模板-分页")
    @PreAuthorize("hasPermission(null,'support:portalTemplate:query')")
    public ResponseEntity<Result> page(PortalTemplateVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        //构造分页对象
        IPage<PortalTemplate> page = new Page<PortalTemplate>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<PortalTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(PortalTemplate.class,queryVO,sortInfo);

        //查询数据
        portalTemplateService.page(page, queryWrapper);
        //转换vo
        IPage<PortalTemplateVO> pageVO = mapperFacade.map(page, IPage.class);
        List<PortalTemplateVO>  portalTemplateVOList=new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            PortalTemplateVO vo = convert2VO(page.getRecords().get(i));
            portalTemplateVOList.add(vo);
        }
        pageVO.setRecords(portalTemplateVOList);        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "门户模板-列表")
    @PreAuthorize("hasPermission(null,'support:portalTemplate:query')")
    public ResponseEntity<Result> list(PortalTemplateVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<PortalTemplate> queryWrapper = QueryGenerator.generateQueryWrapper(PortalTemplate.class, queryVO,sortInfo);
        List<PortalTemplate> list= portalTemplateService.list(queryWrapper);
        //转换vo
        List<PortalTemplateVO> portalTemplateVOList = mapperFacade.mapAsList(list, PortalTemplateVO.class);
        return ResultUtil.success(portalTemplateVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @GetMapping("/{id}")
    @SystemLog(value = "门户模板-详情")
    @PreAuthorize("hasPermission(null,'support:portalTemplate:query')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        PortalTemplate entity = portalTemplateService.query(id);
        PortalTemplateVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }


    /**
     * 启用
     */
    @ApiOperation(value = "启用")
    @PutMapping("/{id}/enable")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @SystemLog(value = "门户模板-启用")
    @PreAuthorize("hasPermission(null,'support:portalTemplate:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        portalTemplateService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @ApiOperation(value = "停用")
    @PutMapping("/{id}/disable")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @SystemLog(value = "门户模板-停用")
    @PreAuthorize("hasPermission(null,'support:portalTemplate:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        portalTemplateService.disable(id);
        return ResultUtil.success();
    }
    
    
    

    private PortalTemplateVO convert2VO(PortalTemplate entity){
        PortalTemplateVO vo=mapperFacade.map(entity,PortalTemplateVO.class);
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    private PortalTemplate convert2Entity(PortalTemplateVO vo){

        PortalTemplate entity=mapperFacade.map(vo,PortalTemplate.class);
        return entity;
    }

}
