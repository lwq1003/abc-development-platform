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
import tech.abc.platform.support.entity.PortletParam;
import tech.abc.platform.support.service.PortletParamService;
import tech.abc.platform.support.vo.PortletParamVO;
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
 * 组件参数 前端控制器
 * @author wqliu
 * *
 */
@RestController
@RequestMapping("/support/portletParam")
@Slf4j
public class PortletParamController extends BaseController {
 @Autowired
 private PortletParamService portletParamService;

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        PortletParam entity=portletParamService.init();
        PortletParamVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "组件参数-新增")
    @PreAuthorize("hasPermission(null,'support:portletParam:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody PortletParamVO vo) {
        PortletParam entity=convert2Entity(vo);
        portletParamService.add(entity);
        PortletParamVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "组件参数-修改")
    @PreAuthorize("hasPermission(null,'support:portletParam:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody PortletParamVO vo)  {
        PortletParam entity=convert2Entity(vo);
        //此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        //个别需展示的该类信息的地方可以重新查询后返回
        portletParamService.modify(entity);
        PortletParamVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "组件参数-删除")
    @PreAuthorize("hasPermission(null,'support:portletParam:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        portletParamService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "组件参数-分页")
    @PreAuthorize("hasPermission(null,'support:portletParam:query')")
    public ResponseEntity<Result> page(PortletParamVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        //构造分页对象
        IPage<PortletParam> page = new Page<PortletParam>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<PortletParam> queryWrapper = QueryGenerator.generateQueryWrapper(PortletParam.class,queryVO,sortInfo);

        //查询数据
        portletParamService.page(page, queryWrapper);
        //转换vo
        IPage<PortletParamVO> pageVO = mapperFacade.map(page, IPage.class);
        List<PortletParamVO>  portletParamVOList=new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            PortletParamVO vo = convert2VO(page.getRecords().get(i));
            portletParamVOList.add(vo);
        }
        pageVO.setRecords(portletParamVOList);        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "组件参数-列表")
    @PreAuthorize("hasPermission(null,'support:portletParam:query')")
    public ResponseEntity<Result> list(PortletParamVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<PortletParam> queryWrapper = QueryGenerator.generateQueryWrapper(PortletParam.class, queryVO,sortInfo);
        List<PortletParam> list= portletParamService.list(queryWrapper);
        //转换vo
        List<PortletParamVO> portletParamVOList = mapperFacade.mapAsList(list, PortletParamVO.class);
        return ResultUtil.success(portletParamVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String",paramType ="path")
    @GetMapping("/{id}")
    @SystemLog(value = "组件参数-详情")
    @PreAuthorize("hasPermission(null,'support:portletParam:query')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        PortletParam entity = portletParamService.query(id);
        PortletParamVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }


    private PortletParamVO convert2VO(PortletParam entity){
        PortletParamVO vo=mapperFacade.map(entity,PortletParamVO.class);
        return vo;
    }

    private PortletParam convert2Entity(PortletParamVO vo){

        PortletParam entity=mapperFacade.map(vo,PortletParam.class);
        return entity;
    }

}
