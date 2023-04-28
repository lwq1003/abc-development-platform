package tech.abc.platform.entityconfig.controller;

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
import tech.abc.platform.common.annotation.AllowAll;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.entityconfig.entity.EntityView;
import tech.abc.platform.entityconfig.service.EntityViewService;
import tech.abc.platform.entityconfig.vo.EntityViewVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 实体视图 前端控制器
 *
 * @author wqliu
 * @date 2022-10-31
 */
@RestController
@RequestMapping("/entityconfig/entityViewConfig")
@Slf4j
public class EntityViewConfigController extends BaseController {
    @Autowired
    private EntityViewService entityViewService;

    //region 基本操作

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        EntityView entity = entityViewService.init();
        EntityViewVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增")
    @PostMapping("/")
    @SystemLog(value = "实体视图-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:entityView:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody EntityViewVO vo) {
        EntityView entity = convert2Entity(vo);
        entityViewService.add(entity);
        EntityViewVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "实体视图-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:entityView:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody EntityViewVO vo) {
        EntityView entity = convert2Entity(vo);
        //此处数据库会更新 更新人和更新时间，但数据模型不会刷新
        //个别需展示的该类信息的地方可以重新查询后返回
        entityViewService.modify(entity);
        EntityViewVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @DeleteMapping("/{id}")
    @SystemLog(value = "实体视图-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:entityView:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        entityViewService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    @SystemLog(value = "实体视图-分页")
    //@PreAuthorize("hasPermission(null,'entityconfig:entityViewConfig:query')")
    public ResponseEntity<Result> page(EntityViewVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {

        //构造分页对象
        IPage<EntityView> page = new Page<EntityView>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<EntityView> queryWrapper = QueryGenerator.generateQueryWrapper(EntityView.class, queryVO, sortInfo);

        //查询数据
        entityViewService.page(page, queryWrapper);
        //转换vo
        IPage<EntityViewVO> pageVO = mapperFacade.map(page, IPage.class);
        List<EntityViewVO> entityViewVOList = new ArrayList<>();
        for (int i = 0; i < page.getRecords().size(); i++) {
            EntityViewVO vo = convert2VO(page.getRecords().get(i));
            entityViewVOList.add(vo);
        }
        pageVO.setRecords(entityViewVOList);
        ;
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @ApiOperation(value = "获取列表")
    @GetMapping("/list")
    @SystemLog(value = "实体视图-列表")
    @AllowAll
    //@PreAuthorize("hasPermission(null,'entityconfig:entityView:query')")
    public ResponseEntity<Result> list(EntityViewVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<EntityView> queryWrapper = QueryGenerator.generateQueryWrapper(EntityView.class, queryVO, sortInfo);
        List<EntityView> list = entityViewService.list(queryWrapper);
        //转换vo
        List<EntityViewVO> entityViewVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            EntityViewVO vo = convert2VO(list.get(i));
            entityViewVOList.add(vo);
        }
        return ResultUtil.success(entityViewVOList);
    }

    /**
     * 获取单条数据
     */
    @ApiOperation(value = "获取单条数据")
    @ApiImplicitParam(name = "id", value = "标识", required = true, dataType = "String", paramType = "path")
    @GetMapping("/{id}")
    @SystemLog(value = "实体视图-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:entityView:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        EntityView entity = entityViewService.query(id);
        EntityViewVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    //endregion

    //region 扩展操作

    //endregion

    //region 辅助操作

    private EntityViewVO convert2VO(EntityView entity) {
        EntityViewVO vo = mapperFacade.map(entity, EntityViewVO.class);
        if (entity.getEntityViewType() != null) {
            String entityViewTypeName = dictionaryUtil.getNameByCode("EntityViewType", entity.getEntityViewType());
            vo.setEntityViewTypeName(entityViewTypeName);
        }

        return vo;
    }

    private EntityView convert2Entity(EntityViewVO vo) {

        EntityView entity = mapperFacade.map(vo, EntityView.class);
        return entity;
    }

    //endregion
}
