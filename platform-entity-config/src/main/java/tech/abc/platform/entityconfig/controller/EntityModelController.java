package tech.abc.platform.entityconfig.controller;


import org.springframework.web.bind.annotation.RestController;
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
import tech.abc.platform.entityconfig.entity.EntityModel;
import tech.abc.platform.entityconfig.service.EntityModelService;
import tech.abc.platform.entityconfig.vo.EntityModelVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
* 实体模型 前端控制器类
*
* @author wqliu
* @date 2023-04-19
*/
@RestController
@RequestMapping("/entityconfig/entityModel")
@Slf4j
public class EntityModelController extends BaseController {
    @Autowired
    private EntityModelService entityModelService;

    //region 基本操作
    /**
    * 初始化
    */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        EntityModel entity=entityModelService.init();
        EntityModelVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 新增
    */
    @PostMapping("/")
    @SystemLog(value = "实体模型-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModel:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody EntityModelVO vo) {
        EntityModel entity=convert2Entity(vo);
        entityModelService.add(entity);
        EntityModelVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 修改
    */
    @PutMapping("/")
    @SystemLog(value = "实体模型-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModel:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody EntityModelVO vo) {
        EntityModel entity=convert2Entity(vo);
        entityModelService.modify(entity);
        EntityModelVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
    * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @DeleteMapping("/{id}")
    @SystemLog(value = "实体模型-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModel:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        entityModelService.remove(id);
        return ResultUtil.success();
    }

    /**
    * 分页
    */
    @GetMapping("/page")
    @SystemLog(value = "实体模型-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModel:query')")
    public ResponseEntity<Result> page(EntityModelVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        //构造分页对象
        IPage<EntityModel> page = new Page<EntityModel>(pageInfo.getPageNum(), pageInfo.getPageSize());
        //构造查询条件
        QueryWrapper<EntityModel> queryWrapper = QueryGenerator.generateQueryWrapper(EntityModel.class,queryVO,sortInfo);

        //查询数据
        entityModelService.page(page, queryWrapper);
        //转换vo
        IPage<EntityModelVO> pageVO = mapperFacade.map(page, IPage.class);
        List<EntityModelVO>  entityModelVOList=convert2VO(page.getRecords());
        pageVO.setRecords(entityModelVOList);
        return ResultUtil.success(pageVO);
    }


    /**
    * 列表
    */
    @GetMapping("/list")
    @SystemLog(value = "实体模型-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModel:query')")
    public ResponseEntity<Result> list(EntityModelVO queryVO, SortInfo sortInfo) {
        //构造查询条件
        QueryWrapper<EntityModel> queryWrapper = QueryGenerator.generateQueryWrapper(EntityModel.class, queryVO,sortInfo);
        List<EntityModel> list= entityModelService.list(queryWrapper);
        //转换vo
        List<EntityModelVO>  entityModelVOList=convert2VO(list);
        return ResultUtil.success(entityModelVOList);
    }

    /**
    * 获取单条数据
    */
    @GetMapping("/{id}")
    @SystemLog(value = "实体模型-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModel:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        EntityModel entity = entityModelService.query(id);
        EntityModelVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
    * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
    */
    @PostMapping("/{id}")
    @SystemLog(value = "实体模型-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModel:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        entityModelService.addByCopy(id);
        return ResultUtil.success();
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
    private EntityModelVO convert2VO(EntityModel entity){
        EntityModelVO vo=mapperFacade.map(entity,EntityModelVO.class);
        vo.setParentModelName(dictionaryUtil.getNameByCode("BaseModel", entity.getParentModel()));
        vo.setMainModelFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getMainModelFlag()));
        vo.setSelfReferenceFlagName(dictionaryUtil.getNameByCode("YesOrNo", entity.getSelfReferenceFlag()));
        return vo;
    }

    /**
    * 将实体列表转换为视图对象列表
    *
    * @param entityList 实体列表
    * @return {@link List}<{@link EntityVO}> 视图对象列表
    */
    private List<EntityModelVO> convert2VO(List<EntityModel> entityList) {
        List<EntityModelVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            EntityModelVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private EntityModel convert2Entity(EntityModelVO vo){
        EntityModel entity=mapperFacade.map(vo,EntityModel.class);
        return entity;
    }

    //endregion
 }