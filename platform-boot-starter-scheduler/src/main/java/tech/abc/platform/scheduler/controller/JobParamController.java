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
import tech.abc.platform.scheduler.entity.JobParam;
import tech.abc.platform.scheduler.service.JobParamService;
import tech.abc.platform.scheduler.service.JobService;
import tech.abc.platform.scheduler.vo.JobParamVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 任务参数 前端控制器类
 *
 * @author wqliu
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/scheduler/jobParam")
@Slf4j
public class JobParamController extends BaseController {
    @Autowired
    private JobParamService jobParamService;
    @Autowired
    private JobService jobService;

    // region 基本操作

    /**
     * 初始化
     */
    @GetMapping("/init")
    public ResponseEntity<Result> init() {
        JobParam entity = jobParamService.init();
        JobParamVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 新增
     */
    @PostMapping("/")
    @SystemLog(value = "任务参数-新增")
    @PreAuthorize("hasPermission(null,'scheduler:jobParam:add')")
    public ResponseEntity<Result> add(@Validated @RequestBody JobParamVO vo) {
        JobParam entity = convert2Entity(vo);
        jobParamService.add(entity);
        JobParamVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 修改
     */
    @PutMapping("/")
    @SystemLog(value = "任务参数-修改")
    @PreAuthorize("hasPermission(null,'scheduler:jobParam:modify')")
    public ResponseEntity<Result> modify(@Validated @RequestBody JobParamVO vo) {
        JobParam entity = convert2Entity(vo);
        jobParamService.modify(entity);
        JobParamVO newVO = convert2VO(entity);
        return ResultUtil.success(newVO);
    }

    /**
     * 删除数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @DeleteMapping("/{id}")
    @SystemLog(value = "任务参数-删除")
    @PreAuthorize("hasPermission(null,'scheduler:jobParam:remove')")
    public ResponseEntity<Result> remove(@PathVariable("id") String id) {
        jobParamService.remove(id);
        return ResultUtil.success();
    }

    /**
     * 分页
     */
    @GetMapping("/page")
    @SystemLog(value = "任务参数-分页")
    @PreAuthorize("hasPermission(null,'scheduler:jobParam:query')")
    public ResponseEntity<Result> page(JobParamVO queryVO, PageInfo pageInfo, SortInfo sortInfo) {
        // 构造分页对象
        IPage<JobParam> page = new Page<JobParam>(pageInfo.getPageNum(), pageInfo.getPageSize());

        // 当勾选查询所有复选框时，查询所有数据
        if (queryVO.getIgnoreParent() != null && queryVO.getIgnoreParent()) {
            queryVO.setJob(null);
        }

        // 构造查询条件
        QueryWrapper<JobParam> queryWrapper = QueryGenerator.generateQueryWrapper(JobParam.class, queryVO, sortInfo);

        // 查询数据
        jobParamService.page(page, queryWrapper);
        // 转换vo
        IPage<JobParamVO> pageVO = mapperFacade.map(page, IPage.class);
        List<JobParamVO> jobParamVOList = convert2VO(page.getRecords());
        pageVO.setRecords(jobParamVOList);
        return ResultUtil.success(pageVO);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @SystemLog(value = "任务参数-列表")
    @PreAuthorize("hasPermission(null,'scheduler:jobParam:query')")
    public ResponseEntity<Result> list(JobParamVO queryVO, SortInfo sortInfo) {
        // 构造查询条件
        QueryWrapper<JobParam> queryWrapper = QueryGenerator.generateQueryWrapper(JobParam.class, queryVO, sortInfo);
        List<JobParam> list = jobParamService.list(queryWrapper);
        // 转换vo
        List<JobParamVO> jobParamVOList = convert2VO(list);
        return ResultUtil.success(jobParamVOList);
    }

    /**
     * 获取单条数据
     */
    @GetMapping("/{id}")
    @SystemLog(value = "任务参数-详情")
    @PreAuthorize("hasPermission(null,'scheduler:jobParam:view')")
    public ResponseEntity<Result> get(@PathVariable("id") String id) {
        JobParam entity = jobParamService.query(id);
        JobParamVO vo = convert2VO(entity);
        return ResultUtil.success(vo);
    }

    /**
     * 复制新增数据，单条数据标识，或多条数据标识用逗号间隔拼成的字符串
     */
    @PostMapping("/{id}")
    @SystemLog(value = "任务参数-复制新增")
    @PreAuthorize("hasPermission(null,'scheduler:jobParam:addByCopy')")
    public ResponseEntity<Result> addByCopy(@PathVariable("id") String id) {
        jobParamService.addByCopy(id);
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
    private JobParamVO convert2VO(JobParam entity) {
        JobParamVO vo = mapperFacade.map(entity, JobParamVO.class);
        return vo;
    }

    /**
     * 将实体列表转换为视图对象列表
     *
     * @param entityList 实体列表
     * @return {@link List}<{@link EntityVO}> 视图对象列表
     */
    private List<JobParamVO> convert2VO(List<JobParam> entityList) {
        List<JobParamVO> voList = new ArrayList<>(entityList.size());

        // 获取 任务 集合
        List<String> jobList = entityList.stream().map(x -> x.getJob()).collect(Collectors.toList());
        Map<String, String> jobNameMap = jobService.getNameMap(jobList);
        entityList.stream().forEach(x -> {
            JobParamVO vo = convert2VO(x);
            // 设置 任务
            vo.setJobName(jobNameMap.get(x.getJob()));
            voList.add(vo);
        });
        return voList;
    }


    private JobParam convert2Entity(JobParamVO vo) {
        JobParam entity = mapperFacade.map(vo, JobParam.class);
        return entity;
    }

    // endregion
}