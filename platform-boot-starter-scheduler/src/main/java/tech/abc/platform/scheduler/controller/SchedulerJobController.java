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
import tech.abc.platform.scheduler.entity.SchedulerJob;
import tech.abc.platform.scheduler.service.JobService;
import tech.abc.platform.scheduler.service.SchedulerJobService;
import tech.abc.platform.scheduler.vo.SchedulerJobVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 调度任务 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/scheduler/schedulerJob")
@Slf4j
public class SchedulerJobController extends BaseController {
    @Autowired
    private SchedulerJobService schedulerJobService;
    @Autowired
    private JobService jobService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        SchedulerJob entity = schedulerJobService.init();
        SchedulerJobVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "调度任务-新增")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody SchedulerJobVO vo) {
        SchedulerJob entity = convert2Entity(vo);
        schedulerJobService.add(entity);
        SchedulerJobVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "调度任务-修改")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody SchedulerJobVO vo) {
        SchedulerJob entity = convert2Entity(vo);
        schedulerJobService.modify(entity);
        SchedulerJobVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "调度任务-删除")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        schedulerJobService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "调度任务-分页")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:query')")
    public ResponseEntity<Result> page(SchedulerJobVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<SchedulerJob> page = new Page<SchedulerJob>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<SchedulerJob> queryWrapper = QueryGenerator.generateQueryWrapper(SchedulerJob.class, queryVO, sortInfo);

        // 查询数据
        schedulerJobService.page(page, queryWrapper);
        // 转换vo
        IPage<SchedulerJobVO> pageVO = mapperFacade.map(page, IPage.class);
        List<SchedulerJobVO> schedulerJobVOList = convert2VO(page.getRecords());
        pageVO.setRecords(schedulerJobVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "调度任务-列表")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:query')")
    public ResponseEntity<Result> list(SchedulerJobVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<SchedulerJob> queryWrapper = QueryGenerator.generateQueryWrapper(SchedulerJob.class, queryVO, sortInfo);
        List<SchedulerJob> list = schedulerJobService.list(queryWrapper);
        // 转换vo
        List<SchedulerJobVO> schedulerJobVOList = convert2VO(list);
        return ResultUtil.success(schedulerJobVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "调度任务-详情")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        SchedulerJob entity = schedulerJobService.query(id);
        SchedulerJobVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "调度任务-复制新增")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        schedulerJobService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 暂停
     */

    @PutMapping("/{id}/pause")
    @SystemLog(value = "调度任务-暂停")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:pause')")
    public ResponseEntity<Result> pause(@PathVariable("id") String id) {
        schedulerJobService.pause(id);
        return ResultUtil.success();
    }

    /**
     * 恢复
     */

    @PutMapping("/{id}/resume")
    @SystemLog(value = "调度任务-恢复")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:resume')")
    public ResponseEntity<Result> resume(@PathVariable("id") String id) {

        schedulerJobService.resume(id);
        return ResultUtil.success();
    }

    /**
     * 暂停全部
     */

    @PutMapping("/pauseAll")
    @SystemLog(value = "调度任务-暂停全部")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:pauseAll')")
    public ResponseEntity<Result> pauseAll() {

        schedulerJobService.pauseAll();
        return ResultUtil.success();
    }

    /**
     * 恢复全部
     */
    @PutMapping("/resumeAll")
    @SystemLog(value = "调度任务-恢复全部")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:resumeAll')")
    public ResponseEntity<Result> resumeAll() {

        schedulerJobService.resumeAll();
        return ResultUtil.success();
    }

    /**
     * 执行任务
     */

    @PutMapping("/{id}/execute")
    @SystemLog(value = "调度任务-执行")
    @PreAuthorize("hasPermission(null,'scheduler:schedulerJob:execute')")
    public ResponseEntity<Result> execute(@PathVariable("id") String id) {
        schedulerJobService.execute(id);
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
    private SchedulerJobVO convert2VO(SchedulerJob entity) {
        SchedulerJobVO vo = mapperFacade.map(entity, SchedulerJobVO.class);
        vo.setStatusName(dictionaryUtil.getNameByCode("JobStatus", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<SchedulerJobVO> convert2VO(List<SchedulerJob> entityList) {
        List<SchedulerJobVO> voList = new ArrayList<>(entityList.size());

        // 获取 任务 集合
        List<String> jobList = entityList.stream().map(x -> x.getJob()).collect(Collectors.toList());
        Map<String, String> jobNameMap = jobService.getNameMap(jobList);
        entityList.stream().forEach(x -> {
            SchedulerJobVO vo = convert2VO(x);
            // 设置 任务
            vo.setJobName(jobNameMap.get(x.getJob()));
            voList.add(vo);
        });
        return voList;
    }


    private SchedulerJob convert2Entity(SchedulerJobVO vo) {
        SchedulerJob entity = mapperFacade.map(vo, SchedulerJob.class);
        return entity;
    }

    // endregion
}