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
import tech.abc.platform.entityconfig.entity.EntityView;
import tech.abc.platform.entityconfig.service.EntityModelService;
import tech.abc.platform.entityconfig.service.EntityViewService;
import tech.abc.platform.entityconfig.vo.EntityViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 实体视图 前端控制器类
 *
 * @author wqliu
 * @date 2023-04-14
 */
@RestController
@RequestMapping("/entityconfig/entityView")
@Slf4j
public class EntityViewController extends BaseController {
    @Autowired
    private EntityViewService entityViewService;
    @Autowired
    private EntityModelService entityModelService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        EntityView entity = entityViewService.init();
        EntityViewVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
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
    @PutMapping("/")
    @SystemLog(value = "实体视图-修改")
    @PreAuthorize("hasPermission(null,'entityconfig:entityView:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody EntityViewVO vo) {
        EntityView entity = convert2Entity(vo);
        entityViewService.modify(entity);
        EntityViewVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
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
    @GetMapping("/page")
    @SystemLog(value = "实体视图-分页")
    @PreAuthorize("hasPermission(null,'entityconfig:entityView:query')")
    public ResponseEntity<Result> page(EntityViewVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<EntityView> page = new Page<EntityView>(pageInfo.getPageNum(), pageInfo.getPageSize());
        // 构造查询条件
        QueryWrapper<EntityView> queryWrapper = QueryGenerator.generateQueryWrapper(EntityView.class, queryVO, sortInfo);

        // 查询数据
        entityViewService.page(page, queryWrapper);
        // 转换vo
        IPage<EntityViewVO> pageVO = mapperFacade.map(page, IPage.class);
        List<EntityViewVO> entityViewVOList = convert2VO(page.getRecords());
        pageVO.setRecords(entityViewVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "实体视图-列表")
    @PreAuthorize("hasPermission(null,'entityconfig:entityView:query')")
    public ResponseEntity<Result> list(EntityViewVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<EntityView> queryWrapper = QueryGenerator.generateQueryWrapper(EntityView.class, queryVO, sortInfo);
        List<EntityView> list = entityViewService.list(queryWrapper);
        // 转换vo
        List<EntityViewVO> entityViewVOList = convert2VO(list);
        return ResultUtil.success(entityViewVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "实体视图-详情")
    @PreAuthorize("hasPermission(null,'entityconfig:entityView:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        EntityView entity = entityViewService.query(id);
        EntityViewVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "实体视图-复制新增")
    @PreAuthorize("hasPermission(null,'entityconfig:entityView:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        entityViewService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    // endregion

    // region 辅助操作

    /**
     * 将单条实体转换为视图对象
     *
     * @param entity 实体
     * @return {@link EntityVO} 视图对象
     */
    private EntityViewVO convert2VO(EntityView entity) {
        EntityViewVO vo = mapperFacade.map(entity, EntityViewVO.class);
        vo.setEntityViewTypeName(dictionaryUtil.getNameByCode("EntityViewType", entity.getEntityViewType()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<EntityViewVO> convert2VO(List<EntityView> entityList) {
        List<EntityViewVO> voList = new ArrayList<>(entityList.size());

        // 获取 实体模型 集合
        List<String> entityModelList = entityList.stream().map(x -> x.getEntityModel()).collect(Collectors.toList());
        Map<String, String> entityModelNameMap = entityModelService.getNameMap(entityModelList);
        entityList.stream().forEach(x -> {
            EntityViewVO vo = convert2VO(x);
            // 设置 实体模型
            vo.setEntityModelName(entityModelNameMap.get(x.getEntityModel()));
            voList.add(vo);
        });
        return voList;
    }


    private EntityView convert2Entity(EntityViewVO vo) {
        EntityView entity = mapperFacade.map(vo, EntityView.class);
        return entity;
    }

    // endregion
}