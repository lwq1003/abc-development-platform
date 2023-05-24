package tech.abc.platform.scheduler.controller;


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
import tech.abc.platform.common.vo.PageInfo;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.common.vo.SortInfo;
import tech.abc.platform.scheduler.entity.SchedulerJobParam;
import tech.abc.platform.scheduler.service.SchedulerJobParamService;
import tech.abc.platform.scheduler.service.SchedulerJobService;
import tech.abc.platform.scheduler.vo.SchedulerJobParamVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 调度任务参数 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/scheduler/schedulerJobParam")
@Slf4j
public class SchedulerJobParamController extends BaseController {
    @Autowired
    private SchedulerJobParamService schedulerJobParamService;
    @Autowired
    private SchedulerJobService schedulerJobService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        SchedulerJobParam entity = schedulerJobParamService.init();
        SchedulerJobParamVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "调度任务参数-新增")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJobParam:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody SchedulerJobParamVO vo) {
        SchedulerJobParam entity = convert2Entity(vo);
        schedulerJobParamService.add(entity);
        SchedulerJobParamVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "调度任务参数-修改")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJobParam:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody SchedulerJobParamVO vo) {
        SchedulerJobParam entity = convert2Entity(vo);
        schedulerJobParamService.modify(entity);
        SchedulerJobParamVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "调度任务参数-删除")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJobParam:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        schedulerJobParamService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "调度任务参数-分页")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJobParam:query')")
    public ResponseEntity<Result> page(SchedulerJobParamVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<SchedulerJobParam> page = new Page<SchedulerJobParam>(pageInfo.getPageNum(), pageInfo.getPageSize());

        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setSchedulerJob(null);
        }

        // 构造查询条件
        QueryWrapper<SchedulerJobParam> queryWrapper = QueryGenerator.generateQueryWrapper(SchedulerJobParam.class, queryVO, sortInfo);

        // 查询数据
        schedulerJobParamService.page(page, queryWrapper);
        // 转换vo
        IPage<SchedulerJobParamVO> pageVO = mapperFacade.map(page, IPage.class);
        List<SchedulerJobParamVO> schedulerJobParamVOList = convert2VO(page.getRecords());
        pageVO.setRecords(schedulerJobParamVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "调度任务参数-列表")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJobParam:query')")
    public ResponseEntity<Result> list(SchedulerJobParamVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<SchedulerJobParam> queryWrapper = QueryGenerator.generateQueryWrapper(SchedulerJobParam.class, queryVO, sortInfo);
        List<SchedulerJobParam> list = schedulerJobParamService.list(queryWrapper);
        // 转换vo
        List<SchedulerJobParamVO> schedulerJobParamVOList = convert2VO(list);
        return ResultUtil.success(schedulerJobParamVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "调度任务参数-详情")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJobParam:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        SchedulerJobParam entity = schedulerJobParamService.query(id);
        SchedulerJobParamVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "调度任务参数-复制新增")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJobParam:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        schedulerJobParamService.addByCopy(id);
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
    private SchedulerJobParamVO convert2VO(SchedulerJobParam entity) {
        SchedulerJobParamVO vo = mapperFacade.map(entity, SchedulerJobParamVO.class);
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<SchedulerJobParamVO> convert2VO(List<SchedulerJobParam> entityList) {
        List<SchedulerJobParamVO> voList = new ArrayList<>(entityList.size());

        // 获取 调度任务 集合
        List<String> schedulerJobList = entityList.stream().map(x -> x.getSchedulerJob()).collect(Collectors.toList());
        Map<String, String> schedulerJobNameMap = schedulerJobService.getNameMap(schedulerJobList);
        entityList.stream().forEach(x -> {
            SchedulerJobParamVO vo = convert2VO(x);
            // 设置 调度任务
            vo.setSchedulerJobName(schedulerJobNameMap.get(x.getSchedulerJob()));
            voList.add(vo);
        });
        return voList;
    }


    private SchedulerJobParam convert2Entity(SchedulerJobParamVO vo) {
        SchedulerJobParam entity = mapperFacade.map(vo, SchedulerJobParam.class);
        return entity;
    }

    // endregion
}