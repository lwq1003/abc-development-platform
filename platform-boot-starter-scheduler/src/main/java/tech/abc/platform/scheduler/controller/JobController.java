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
import tech.abc.platform.scheduler.entity.Job;
import tech.abc.platform.scheduler.service.JobService;
import tech.abc.platform.scheduler.vo.JobVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/scheduler/job")
@Slf4j
public class JobController extends BaseController {
    @Autowired
    private JobService jobService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        Job entity = jobService.init();
        JobVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "任务-新增")
    @PreAuthorize("hasPermission(null,'scheduler:job:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody JobVO vo) {
        Job entity = convert2Entity(vo);
        jobService.add(entity);
        JobVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "任务-修改")
    @PreAuthorize("hasPermission(null,'scheduler:job:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody JobVO vo) {
        Job entity = convert2Entity(vo);
        jobService.modify(entity);
        JobVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "任务-删除")
    @PreAuthorize("hasPermission(null,'scheduler:job:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        jobService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "任务-分页")
    @PreAuthorize("hasPermission(null,'scheduler:job:query')")
    public ResponseEntity<Result> page(JobVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<Job> page = new Page<Job>(pageInfo.getPageNum(), pageInfo.getPageSize());


        // 构造查询条件
        QueryWrapper<Job> queryWrapper = QueryGenerator.generateQueryWrapper(Job.class, queryVO, sortInfo);

        // 查询数据
        jobService.page(page, queryWrapper);
        // 转换vo
        IPage<JobVO> pageVO = mapperFacade.map(page, IPage.class);
        List<JobVO> jobVOList = convert2VO(page.getRecords());
        pageVO.setRecords(jobVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "任务-列表")
    @PreAuthorize("hasPermission(null,'scheduler:job:query')")
    public ResponseEntity<Result> list(JobVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<Job> queryWrapper = QueryGenerator.generateQueryWrapper(Job.class, queryVO, sortInfo);
        List<Job> list = jobService.list(queryWrapper);
        // 转换vo
        List<JobVO> jobVOList = convert2VO(list);
        return ResultUtil.success(jobVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "任务-详情")
    @PreAuthorize("hasPermission(null,'scheduler:job:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        Job entity = jobService.query(id);
        JobVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "任务-复制新增")
    @PreAuthorize("hasPermission(null,'scheduler:job:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        jobService.addByCopy(id);
        return ResultUtil.success();
    }


    // endregion

    // region 扩展操作

    /**
     * 启用
     */

    @PutMapping("/{id}/enable")
    @SystemLog(value = "任务-启用")
    @PreAuthorize("hasPermission(null,'system:organization:enable')")
    public ResponseEntity<Result> enable(@PathVariable("id") String id) {

        jobService.enable(id);
        return ResultUtil.success();
    }

    /**
     * 停用
     */

    @PutMapping("/{id}/disable")
    @SystemLog(value = "任务-停用")
    @PreAuthorize("hasPermission(null,'system:organization:disable')")
    public ResponseEntity<Result> disable(@PathVariable("id") String id) {

        jobService.disable(id);
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
    private JobVO convert2VO(Job entity) {
        JobVO vo = mapperFacade.map(entity, JobVO.class);
        vo.setStatusName(dictionaryUtil.getNameByCode("Status", entity.getStatus()));
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<JobVO> convert2VO(List<Job> entityList) {
        List<JobVO> voList = new ArrayList<>(entityList.size());

        entityList.stream().forEach(x -> {
            JobVO vo = convert2VO(x);
            voList.add(vo);
        });
        return voList;
    }


    private Job convert2Entity(JobVO vo) {
        Job entity = mapperFacade.map(vo, Job.class);
        return entity;
    }

    // endregion
}