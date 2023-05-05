package tech.abc.platform.entityconfig.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import tech.abc.platform.entityconfig.entity.Entity;
import tech.abc.platform.entityconfig.service.EntityService;
import tech.abc.platform.entityconfig.service.ModuleService;
import tech.abc.platform.entityconfig.vo.EntityVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 实体 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-11
 */
@RestController
@RequestMapping("/entityconfig/entity")
@Slf4j
public class EntityController extends BaseController {
    @Autowired
    private EntityService entityService;
    @Autowired
    private ModuleService moduleService;

    // region 基本操作

    /**
     * 初始化
     */
    @ApiOperation(value = "初始化")
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Entity entity = entityService.init();
        EntityVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "实体-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:entity:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody EntityVO vo) {
        Entity entity = convert2Entity(vo);
        entityService.add(entity);
        EntityVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改")
    @PutMapping("/")
    @SystemLog(value = "实体-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:entity:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody EntityVO vo) {
        Entity entity = convert2Entity(vo);
        entityService.modify(entity);
        EntityVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    @SystemLog(value = "实体-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:entity:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        entityService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "实体-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:entity:query')")
    public ResponseEntity<Result> page(EntityVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<Entity> page = new Page<Entity>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 构造查询条件
        QueryWrapper<Entity> queryWrapper = QueryGenerator.generateQueryWrapper(Entity.class, queryVO, sortInfo);

        // 查询数据
        entityService.page(page, queryWrapper);
        // 转换vo
        IPage<EntityVO> pageVO = mapperFacade.map(page, IPage.class);
        List<EntityVO> entityVOList = convert2VO(page.getRecords());
        pageVO.setRecords(entityVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "实体-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:entity:query')")
    public ResponseEntity<Result> list(EntityVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<Entity> queryWrapper = QueryGenerator.generateQueryWrapper(Entity.class, queryVO, sortInfo);
        List<Entity> list = entityService.list(queryWrapper);
        // 转换vo
        List<EntityVO> entityVOList = convert2VO(list);
        return ResultUtil.success(entityVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "实体-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:entity:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Entity entity = entityService.query(id);
        EntityVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "实体-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:entity:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        entityService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 生成库表，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/generateTable/{id}")
    @SystemLog(value = "实体-生成库表")
    @PreAuthorize("hasPermission(null,'entityconfig:entity:generateTable')")
    public ResponseEntity<Result> generateTable(@PathVariable("id") String id) {
        entityService.generateTable(id);
        return ResultUtil.success();
    }

    /**
     * 生成代码，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/generateCode/{id}")
    @SystemLog(value = "实体-生成代码")
    @PreAuthorize("hasPermission(null,'entityconfig:entity:generateCode')")
    public ResponseEntity<Result> generateCode(@PathVariable("id") String id) {
        entityService.generateCode(id);
        return ResultUtil.success();
    }


    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private EntityVO convert2VO(Entity entity) {
        EntityVO vo = mapperFacade.map(entity, EntityVO.class);
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<EntityVO> convert2VO(List<Entity> entityList) {
        List<EntityVO> voList = new ArrayList<>(entityList.size());

        // 获取 模块 集合
        List<String> moduleList = entityList.stream().map(x -> x.getModule()).collect(Collectors.toList());
        Map<String, String> moduleNameMap = moduleService.getNameMap(moduleList);
        entityList.stream().forEach(x -> {
            EntityVO vo = convert2VO(x);
            // 设置 模块
            vo.setModuleName(moduleNameMap.get(x.getModule()));
            voList.add(vo);
        });
        return voList;
    }


    private Entity convert2Entity(EntityVO vo) {
        Entity entity = mapperFacade.map(vo, Entity.class);
        return entity;
    }

    // endregion
}