package tech.abc.platform.support.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import tech.abc.platform.common.annotation.AllowAuthenticated;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.support.entity.PortletCategory;
import tech.abc.platform.support.service.PortletCategoryService;
import tech.abc.platform.support.vo.PortletCategoryVO;
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
 * 组件类别 前端控制器
 * @author wqliu
 * *
 */
@RestController
@RequestMapping("/support/portletCategory")
@Slf4j
public class PortletCategoryController extends BaseController {
 @Autowired
 private PortletCategoryService portletCategoryService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        PortletCategory entity=portletCategoryService.init();
        PortletCategoryVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "组件类别-新增")
    @PreAuthorize("hasPermission(null,'support:portletCategory:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody PortletCategoryVO vo) {
        PortletCategory entity=convert2Entity(vo);
        portletCategoryService.add(entity);
        PortletCategoryVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "组件类别-修改")
    @PreAuthorize("hasPermission(null,'support:portletCategory:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody PortletCategoryVO vo)  {
        PortletCategory entity=convert2Entity(vo);
        //此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        //个别需展示的该类信息的地方可以重新查询后返回
        portletCategoryService.modify(entity);
        PortletCategoryVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "组件类别-删除")
    @PreAuthorize("hasPermission(null,'support:portletCategory:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        portletCategoryService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "组件类别-分页")
    @PreAuthorize("hasPermission(null,'support:portletCategory:query')")
    public ResponseEntity<Result> page(PortletCategoryVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        //构造分页对象
        IPage<PortletCategory> page = new Page<PortletCategory>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<PortletCategory> queryWrapper = QueryGenerator.generateQueryWrapper(PortletCategory.class,queryVO,sortInfo);

        //查询数据
        portletCategoryService.page(page, queryWrapper);
        //转换vo
        IPage<PortletCategoryVO> pageVO = mapperFacade.map(page, IPage.class);
        List<PortletCategoryVO> portletCategoryVOList=new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            PortletCategoryVO vo = convert2VO(page.getRecords().get(i));
            portletCategoryVOList.add(vo);
        }
        pageVO.setRecords(portletCategoryVOList);        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "组件类别-列表")
    @PreAuthorize("hasPermission(null,'support:portletCategory:query')")
    public ResponseEntity<Result> list(PortletCategoryVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<PortletCategory> queryWrapper = QueryGenerator.generateQueryWrapper(PortletCategory.class, queryVO,sortInfo);
        List<PortletCategory> list= portletCategoryService.list(queryWrapper);
        //转换vo
        List<PortletCategoryVO> portletCategoryVOList = mapperFacade.mapAsList(list, PortletCategoryVO.class);
        return ResultUtil.success(portletCategoryVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @GetMapping("/{id}")
    @SystemLog(value = "组件类别-详情")
    @PreAuthorize("hasPermission(null,'support:portletCategory:query')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        PortletCategory entity = portletCategoryService.query(id);
        PortletCategoryVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 启用
     */
    @ApiOperation(value = "启用")
    @PutMapping("/{id}/enable")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @SystemLog(value = "组件类别-启用")
    @PreAuthorize("hasPermission(null,'support:portletCategory:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        portletCategoryService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @ApiOperation(value = "停用")
    @PutMapping("/{id}/disable")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @SystemLog(value = "组件类别-停用")
    @PreAuthorize("hasPermission(null,'support:portletCategory:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        portletCategoryService.disable(id);
        return ResultUtil.success();
    }


    /**
     *  获取组件类别及组件
     *  用于自定义桌面设置
     */
    @ApiOperation(value = "获取组件类别及组件" )
    @GetMapping("/portal/list")
    @AllowAuthenticated
    public ResponseEntity<Result> getPortletList(PortletCategoryVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<PortletCategory> queryWrapper = QueryGenerator.generateQueryWrapper(PortletCategory.class, queryVO,sortInfo);
        List<PortletCategory> list= portletCategoryService.getPortletList(queryWrapper);
        //转换vo
        List<PortletCategoryVO> portletCategoryVOList = mapperFacade.mapAsList(list, PortletCategoryVO.class);
        return ResultUtil.success(portletCategoryVOList);
    }


    private PortletCategoryVO convert2VO(PortletCategory entity){
        PortletCategoryVO vo=mapperFacade.map(entity,PortletCategoryVO.class);
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    private PortletCategory convert2Entity(PortletCategoryVO vo){

        PortletCategory entity=mapperFacade.map(vo,PortletCategory.class);
        return entity;
    }

}
