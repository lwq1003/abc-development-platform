package tech.abc.platform.entityconfig.controller;


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
import tech.abc.platform.entityconfig.entity.Module;
import tech.abc.platform.entityconfig.service.ModuleService;
import tech.abc.platform.entityconfig.vo.ModuleVO;
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
* 模块 前端控制器类
*
* @author wqliu
* @date 2023-04-09
*/
@RestController
@RequestMapping("/entityconfig/module")
@Slf4j
public class ModuleController extends BaseController {
    @Autowired
    private ModuleService moduleService;

    //region 基本操作
    /**
    * 初始化
    */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Module entity=moduleService.init();
        ModuleVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "模块-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:module:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody ModuleVO vo) {
        Module entity=convert2Entity(vo);
        moduleService.add(entity);
        ModuleVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "模块-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:module:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody ModuleVO vo) {
        Module entity=convert2Entity(vo);
        moduleService.modify(entity);
        ModuleVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    @SystemLog(value = "模块-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:module:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        moduleService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "模块-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:module:query')")
    public ResponseEntity<Result> page(ModuleVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<Module> page = new Page<Module>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<Module> queryWrapper = QueryGenerator.generateQueryWrapper(Module.class,queryVO,sortInfo);

        //查询数据
        moduleService.page(page, queryWrapper);
        //转换vo
        IPage<ModuleVO> pageVO = mapperFacade.map(page, IPage.class);
        List<ModuleVO>  moduleVOList=convert2VO(page.getRecords());
        pageVO.setRecords(moduleVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "模块-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:module:query')")
    public ResponseEntity<Result> list(ModuleVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<Module> queryWrapper = QueryGenerator.generateQueryWrapper(Module.class, queryVO,sortInfo);
        List<Module> list= moduleService.list(queryWrapper);
        //转换vo
        List<ModuleVO>  moduleVOList=convert2VO(list);
        return ResultUtil.success(moduleVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "模块-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:module:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Module entity = moduleService.query(id);
        ModuleVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    //endregion

    //region 扩展操作

    //endregion

    //region 辅助操作

    /**
    * 将单条实体转换为视图对象
    *
    * @param entity 实体
    * @return {@link EntityVO} 视图对象
    */
    private ModuleVO convert2VO(Module entity){
        ModuleVO vo=mapperFacade.map(entity,ModuleVO.class);
        vo.setAppName(dictionaryUtil.getNameByCode("AppCode", entity.getApp()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<ModuleVO> convert2VO(List<Module> entityList) {
        List<ModuleVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            ModuleVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private Module convert2Entity(ModuleVO vo){
        Module entity=mapperFacade.map(vo,Module.class);
        return entity;
    }

    //endregion
 }