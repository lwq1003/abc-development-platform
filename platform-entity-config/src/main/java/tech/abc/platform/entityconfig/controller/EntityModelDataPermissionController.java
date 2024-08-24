package tech.abc.platform.entityconfig.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.abc.platform.common.annotation.AllowAuthenticated;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.base.BaseController;
import tech.abc.platform.common.query.QueryGenerator;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.entityconfig.entity.EntityModelDataPermission;
import tech.abc.platform.entityconfig.service.EntityModelDataPermissionService;
import tech.abc.platform.entityconfig.vo.EntityModelDataPermissionVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 实体模型数据权限 前端控制器类
 *
 * @author wqliu
 * @date 2024-08-03
 */
@RestController
@RequestMapping("/entityconfig/entityModelDataPermission")
@Slf4j
public class EntityModelDataPermissionController extends BaseController {
    @Autowired
    private EntityModelDataPermissionService entityModelDataPermissionService;


    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        EntityModelDataPermission entity = entityModelDataPermissionService.init();
        EntityModelDataPermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "实体模型数据权限-新增")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelDataPermission:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody EntityModelDataPermissionVO vo) {
        EntityModelDataPermission entity = convert2Entity(vo);
        entityModelDataPermissionService.add(entity);
        EntityModelDataPermissionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "实体模型数据权限-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelDataPermission:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody EntityModelDataPermissionVO vo) {
        EntityModelDataPermission entity = convert2Entity(vo);
        entityModelDataPermissionService.modify(entity);
        EntityModelDataPermissionVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "实体模型数据权限-删除")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelDataPermission:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        entityModelDataPermissionService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "实体模型数据权限-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelDataPermission:query')")
    public ResponseEntity<Result> page(EntityModelDataPermissionVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<EntityModelDataPermission> page = new Page<EntityModelDataPermission>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<EntityModelDataPermission> queryWrapper = QueryGenerator.generateQueryWrapper(EntityModelDataPermission.class, queryVO, sortInfo);

        // 查询数据
        entityModelDataPermissionService.page(page, queryWrapper);
        // 转换vo
        IPage<EntityModelDataPermissionVO> pageVO = mapperFacade.map(page, IPage.class);
        List<EntityModelDataPermissionVO> entityModelDataPermissionVOList = convert2VO(page.getRecords());
        pageVO.setRecords(entityModelDataPermissionVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "实体模型数据权限-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelDataPermission:query')")
    public ResponseEntity<Result> list(EntityModelDataPermissionVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<EntityModelDataPermission> queryWrapper = QueryGenerator.generateQueryWrapper(EntityModelDataPermission.class, queryVO, sortInfo);
        List<EntityModelDataPermission> list = entityModelDataPermissionService.list(queryWrapper);
        // 转换vo
        List<EntityModelDataPermissionVO> entityModelDataPermissionVOList = convert2VO(list);
        return ResultUtil.success(entityModelDataPermissionVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "实体模型数据权限-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelDataPermission:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        EntityModelDataPermission entity = entityModelDataPermissionService.query(id);
        EntityModelDataPermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "实体模型数据权限-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelDataPermission:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        entityModelDataPermissionService.addByCopy(id);
        return ResultUtil.success();
    }


    /**
     * 复制新增单条数据，返回复制后的对象
     */
    @PostMapping("/{id}/addSingleByCopy")
    @SystemLog(value = "实体模型数据权限-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelDataPermission:addByCopy')")
    public ResponseEntity<Result> addSingleByCopy(@PathVariable("id") String id) {
        EntityModelDataPermission entity = entityModelDataPermissionService.addSingleByCopy(id);
        EntityModelDataPermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }
    // endregion

    // region 扩展操作


    /**
     * 根据模型标识获取数据权限配置
     * 若不存在则自动创建
     *
     * @param modelId 模型标识
     * @return 响应实体<result>
     */
    @GetMapping("/getOrInit")
    @SystemLog(value = "实体模型数据权限-获取")
    @AllowAuthenticated
    public ResponseEntity<Result> getOrInit(String modelId) {
        EntityModelDataPermission entity = entityModelDataPermissionService.getOrInit(modelId);
        EntityModelDataPermissionVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }


    /**
     * 生成SQL片段
     */
    @PostMapping("/{id}/generateSqlPart")
    @SystemLog(value = "实体模型数据权限-生成SQL片段")
    @PreAuthorize("hasPermission(null,'entityconfig:entityModelDataPermission:generateSqlPart')")
    public ResponseEntity<Result> generateSqlPart(@PathVariable("id") String id, @RequestBody String rule) {
        String sqlPart = entityModelDataPermissionService.generateSqlPart(id, rule);
        return ResultUtil.success(sqlPart);
    }


    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    protected EntityModelDataPermissionVO convert2VO(EntityModelDataPermission entity) {
        EntityModelDataPermissionVO vo = mapperFacade.map(entity, EntityModelDataPermissionVO.class);
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    protected List<EntityModelDataPermissionVO> convert2VO(List<EntityModelDataPermission> entityList) {
        List<EntityModelDataPermissionVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            EntityModelDataPermissionVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private EntityModelDataPermission convert2Entity(EntityModelDataPermissionVO vo) {
        EntityModelDataPermission entity = mapperFacade.map(vo, EntityModelDataPermission.class);
        return entity;
    }

    // endregion
}