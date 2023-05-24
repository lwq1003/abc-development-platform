package tech.abc.platform.scheduler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.scheduler.entity.Job;
import tech.abc.platform.scheduler.entity.JobParam;
import tech.abc.platform.scheduler.entity.SchedulerJob;
import tech.abc.platform.scheduler.exception.SchedulerExceptionEnum;
import tech.abc.platform.scheduler.mapper.JobMapper;
import tech.abc.platform.scheduler.service.JobParamService;
import tech.abc.platform.scheduler.service.JobService;
import tech.abc.platform.scheduler.service.SchedulerJobService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务 服务实现类
 *
 * @author wqliu
 * @date 2023-05-24
 */
@Service
@Slf4j
public class JobServiceImpl extends BaseServiceImpl<JobMapper, Job> implements JobService {

    @Autowired
    private JobParamService jobParamService;

    @Autowired
    private SchedulerJobService schedulerJobService;

    @Override
    public Job init() {
        Job entity = new Job();
        // 默认值处理
        entity.setStatus("NORMAL");
        return entity;
    }

    @Override
    public void beforeAdd(Job entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(Job::getName, entity.getName()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
    }

    @Override
    public void beforeModify(Job entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(Job::getName, entity.getName())
                    .ne(Job::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Job> list = this.lambdaQuery().in(Job::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(Job entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName(entity.getName() + " 副本");
    }


    @Override
    public void beforeRemove(Job entity) {

        // 验证是否已被调度任务使用
        QueryWrapper<SchedulerJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SchedulerJob::getJob, entity.getId());
        long count = schedulerJobService.count(queryWrapper);
        if (count > 0) {
            throw new CustomException(SchedulerExceptionEnum.JOB_HAS_SCHEDULER);
        }

    }

    @Override
    public void afterRemove(Job entity) {

        //  清理子表
        QueryWrapper<JobParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(JobParam::getJob, entity.getId());
        jobParamService.remove(queryWrapper);
    }


    @Override
    public void enable(String id) {
        Job entity = query(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        Job entity = query(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }


}

