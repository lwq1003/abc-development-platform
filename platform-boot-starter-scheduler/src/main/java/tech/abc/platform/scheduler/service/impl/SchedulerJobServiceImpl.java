package tech.abc.platform.scheduler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.scheduler.entity.Job;
import tech.abc.platform.scheduler.entity.SchedulerJob;
import tech.abc.platform.scheduler.entity.SchedulerJobParam;
import tech.abc.platform.scheduler.enums.JobStatusEnum;
import tech.abc.platform.scheduler.exception.SchedulerExceptionEnum;
import tech.abc.platform.scheduler.mapper.SchedulerJobMapper;
import tech.abc.platform.scheduler.service.JobService;
import tech.abc.platform.scheduler.service.SchedulerJobParamService;
import tech.abc.platform.scheduler.service.SchedulerJobService;
import tech.abc.platform.scheduler.service.SchedulerService;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 调度任务 服务实现类
 *
 * @author wqliu
 * @date 2023-05-24
 */
@Service
@Slf4j
public class SchedulerJobServiceImpl extends BaseServiceImpl<SchedulerJobMapper, SchedulerJob> implements SchedulerJobService {

    @Autowired
    private SchedulerJobParamService schedulerJobParamService;
    @Autowired
    private JobService jobService;
    @Autowired
    private SchedulerService schedulerService;


    @Override
    public SchedulerJob init() {
        SchedulerJob entity = new SchedulerJob();
        // 默认值处理
        entity.setStatus("RUNNING");
        return entity;
    }

    @Override
    public void beforeAdd(SchedulerJob entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(SchedulerJob::getName, entity.getName()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
    }

    @Override
    public void beforeModify(SchedulerJob entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(SchedulerJob::getName, entity.getName())
                    .ne(SchedulerJob::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<SchedulerJob> list = this.lambdaQuery().in(SchedulerJob::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(SchedulerJob entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }

    @Override
    protected void afterAdd(SchedulerJob entity) {
        Job job = jobService.query(entity.getJob());
        String jobClass = job.getJobClass();
        List<SchedulerJobParam> params = schedulerJobParamService.getBySchedulerJobId(entity.getId());
        Map<String, String> paramMap = params.stream().collect(
                Collectors.toMap(SchedulerJobParam::getParamCode, SchedulerJobParam::getParamValue));
        schedulerService.add(jobClass, entity.getId(), entity.getCronExpression(), paramMap, entity.getName());
    }

    @Override
    protected void beforeAddOrModifyOp(SchedulerJob entity) {
        // 验证cron表达式是否正确
        try {
            CronExpression.validateExpression(entity.getCronExpression());
        } catch (ParseException e) {
            log.error("cron表达式验证失败", e);
            throw new CustomException(SchedulerExceptionEnum.CRON_EXPRESSION_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    protected void afterModify(SchedulerJob entity, SchedulerJob originEntity) {
        Job job = jobService.query(entity.getJob());
        String jobClass = job.getJobClass();
        List<SchedulerJobParam> params = schedulerJobParamService.getBySchedulerJobId(entity.getId());
        Map<String, String> paramMap = params.stream().collect(
                Collectors.toMap(SchedulerJobParam::getParamCode, SchedulerJobParam::getParamValue));
        schedulerService.modify(jobClass, entity.getId(), entity.getCronExpression(), paramMap, entity.getName());
    }


    @Override
    public void afterRemove(SchedulerJob entity) {
        //  清理子表
        removeParams(entity);
        // 移除任务
        schedulerService.remove(entity.getId());
    }

    private void removeParams(SchedulerJob entity) {
        //  清理子表
        QueryWrapper<SchedulerJobParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SchedulerJobParam::getSchedulerJob, entity.getId());
        schedulerJobParamService.remove(queryWrapper);
    }

    @Override
    public void pause(String id) {
        // 如已经是暂停状态
        SchedulerJob entity = query(id);
        if (JobStatusEnum.PAUSE.name().equals(entity.getStatus())) {
            throw new CustomException(SchedulerExceptionEnum.SCHEDULER_JOB_IS_PAUSED);
        }
        // 暂停单条任务
        pauseSingleJob(entity);
    }


    @Override
    public void resume(String id) {
        // 如已经是运行状态
        SchedulerJob entity = query(id);
        if (JobStatusEnum.RUNNING.name().equals(entity.getStatus())) {
            throw new CustomException(SchedulerExceptionEnum.SCHEDULER_JOB_IS_RUNNING);
        }
        resumeSingleJob(entity);
    }

    @Override
    public void pauseAll() {
        List<SchedulerJob> schedulerJobList = list();
        for (SchedulerJob entity : schedulerJobList) {
            if (JobStatusEnum.RUNNING.name().equals(entity.getStatus())) {
                // 暂停单条任务
                pauseSingleJob(entity);
            }
        }

    }

    @Override
    public void resumeAll() {
        List<SchedulerJob> schedulerJobList = list();
        for (SchedulerJob entity : schedulerJobList) {
            if (JobStatusEnum.PAUSE.name().equals(entity.getStatus())) {
                // 恢复单条任务
                resumeSingleJob(entity);
            }
        }
    }


    @Override
    public void execute(String id) {
        schedulerService.execute(id);
    }

    private void pauseSingleJob(SchedulerJob entity) {
        // 更新自身状态
        entity.setStatus(JobStatusEnum.PAUSE.name());
        // 直接调用父类update而不是modify操作，避免触发修改前和修改后处理
        updateById(entity);
        // 设置调度引擎
        schedulerService.pause(entity.getId());
    }

    private void resumeSingleJob(SchedulerJob entity) {
        // 更新自身状态
        entity.setStatus(JobStatusEnum.RUNNING.name());
        // 直接调用父类update而不是modify操作，避免触发修改前和修改后处理
        updateById(entity);
        // 设置调度引擎
        schedulerService.resume(entity.getId());
    }
}

