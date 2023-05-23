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
import tech.abc.platform.support.entity.Portlet;
import tech.abc.platform.support.service.PortletService;
import tech.abc.platform.support.vo.PortletVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 组件 前端控制器
 *
 * @author wqliu
 */
@RestController
@RequestMapping("/support/portlet")
@Slf4j
public class PortletController extends BaseController {
    @Autowired
    private PortletService portletService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Portlet entity = portletService.init();
        PortletVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "组件-新增")
    @PreAuthorize("hasPermission(null,'support:portlet:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody PortletVO vo) {
        Portlet entity = convert2Entity(vo);
        portletService.add(entity);
        PortletVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "组件-修改")
    @PreAuthorize("hasPermission(null,'support:portlet:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody PortletVO vo) {
        Portlet entity = convert2Entity(vo);
        // 此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        // 个别需展示的该类信息的地方可以重新查询后返回
        portletService.modify(entity);
        PortletVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "组件-删除")
    @PreAuthorize("hasPermission(null,'support:portlet:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        portletService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "组件-分页")
    @PreAuthorize("hasPermission(null,'support:portlet:query')")
    public ResponseEntity<Result> page(PortletVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        // 构造分页对象
        IPage<Portlet> page = new Page<Portlet>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 构造查询条件
        QueryWrapper<Portlet> queryWrapper = QueryGenerator.generateQueryWrapper(Portlet.class, queryVO, sortInfo);

        // 查询数据
        portletService.page(page, queryWrapper);
        // 转换vo
        IPage<PortletVO> pageVO = mapperFacade.map(page, IPage.class);
        List<PortletVO> portletVOList = new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            PortletVO vo = convert2VO(page.getRecords().get(i));
            portletVOList.add(vo);
        }
        pageVO.setRecords(portletVOList);
        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "组件-列表")
    @PreAuthorize("hasPermission(null,'support:portlet:query')")
    public ResponseEntity<Result> list(PortletVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<Portlet> queryWrapper = QueryGenerator.generateQueryWrapper(Portlet.class, queryVO, sortInfo);
        List<Portlet> list = portletService.list(queryWrapper);
        // 转换vo
        List<PortletVO> portletVOList = mapperFacade.mapAsList(list, PortletVO.class);
        return ResultUtil.success(portletVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    @SystemLog(value = "组件-详情")
    @PreAuthorize("hasPermission(null,'support:portlet:query')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Portlet entity = portletService.query(id);
        PortletVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }


    /**
     * 启用
     */
    @ApiOperation(value = "启用")
    @PutMapping("/{id}/enable")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @SystemLog(value = "组件-启用")
    @PreAuthorize("hasPermission(null,'support:portlet:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        portletService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */
    @ApiOperation(value = "停用")
    @PutMapping("/{id}/disable")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @SystemLog(value = "组件-停用")
    @PreAuthorize("hasPermission(null,'support:portlet:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        portletService.disable(id);
        return ResultUtil.success();
    }


    private PortletVO convert2VO(Portlet entity) {
        PortletVO vo = mapperFacade.map(entity, PortletVO.class);
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    private Portlet convert2Entity(PortletVO vo) {

        Portlet entity = mapperFacade.map(vo, Portlet.class);
        return entity;
    }

}
