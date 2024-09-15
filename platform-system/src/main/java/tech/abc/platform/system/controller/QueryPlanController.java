package tech.abc.platform.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.system.entity.QueryPlan;
import tech.abc.platform.system.service.QueryPlanService;
import tech.abc.platform.system.vo.QueryPlanVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询方案 前端控制器类
 *
 * @author wqliu
 * @date 2024-09-04
 */
@RestController
@RequestMapping("/system/queryPlan")
@Slf4j
public class QueryPlanController extends BaseController {
    @Autowired
    private QueryPlanService queryPlanService;


    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        QueryPlan entity = queryPlanService.init();
        QueryPlanVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "查询方案-新增")
    @PreAuthorize("hasPermission(null,'system:queryPlan:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody QueryPlanVO vo) {
        QueryPlan entity = convert2Entity(vo);
        queryPlanService.add(entity);
        QueryPlanVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "查询方案-修改")
    @PreAuthorize("hasPermission(null,'system:queryPlan:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody QueryPlanVO vo) {
        QueryPlan entity = convert2Entity(vo);
        queryPlanService.modify(entity);
        QueryPlanVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "查询方案-删除")
    @PreAuthorize("hasPermission(null,'system:queryPlan:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        queryPlanService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "查询方案-分页")
    @PreAuthorize("hasPermission(null,'system:queryPlan:query')")
    public ResponseEntity<Result> page(QueryPlanVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<QueryPlan> page = new Page<QueryPlan>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<QueryPlan> queryWrapper = QueryGenerator.generateQueryWrapper(QueryPlan.class, queryVO, sortInfo);

        // 查询数据
        queryPlanService.page(page, queryWrapper);
        // 转换vo
        IPage<QueryPlanVO> pageVO = mapperFacade.map(page, IPage.class);
        List<QueryPlanVO> queryPlanVOList = convert2VO(page.getRecords());
        pageVO.setRecords(queryPlanVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "查询方案-列表")
    @PreAuthorize("hasPermission(null,'system:queryPlan:query')")
    public ResponseEntity<Result> list(QueryPlanVO queryVO, SortInfo sortInfo) {
        // 限制只能查询本人的查询计划
        queryVO.setUserId(UserUtil.getId());

        // 构造查询条件
        QueryWrapper<QueryPlan> queryWrapper = QueryGenerator.generateQueryWrapper(QueryPlan.class, queryVO, sortInfo);
        List<QueryPlan> list = queryPlanService.list(queryWrapper);
        // 转换vo
        List<QueryPlanVO> queryPlanVOList = convert2VO(list);
        return ResultUtil.success(queryPlanVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "查询方案-详情")
    @PreAuthorize("hasPermission(null,'system:queryPlan:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        QueryPlan entity = queryPlanService.query(id);
        QueryPlanVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "查询方案-复制新增")
    @PreAuthorize("hasPermission(null,'system:queryPlan:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        queryPlanService.addByCopy(id);
        return ResultUtil.success();
    }


    /**
     * 复制新增单条数据，返回复制后的对象
     */
    @PostMapping("/{id}/addSingleByCopy")
    @SystemLog(value = "查询方案-复制新增")
    @PreAuthorize("hasPermission(null,'system:queryPlan:addByCopy')")
    public ResponseEntity<Result> addSingleByCopy(@PathVariable("id") String id) {
        QueryPlan entity = queryPlanService.addSingleByCopy(id);
        QueryPlanVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
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
    protected QueryPlanVO convert2VO(QueryPlan entity) {
        QueryPlanVO vo = mapperFacade.map(entity, QueryPlanVO.class);
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    protected List<QueryPlanVO> convert2VO(List<QueryPlan> entityList) {
        List<QueryPlanVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            QueryPlanVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private QueryPlan convert2Entity(QueryPlanVO vo) {
        QueryPlan entity = mapperFacade.map(vo, QueryPlan.class);
        return entity;
    }

    // endregion
}