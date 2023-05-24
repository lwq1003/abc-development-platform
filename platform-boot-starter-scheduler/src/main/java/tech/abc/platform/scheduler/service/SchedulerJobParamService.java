package tech.abc.platform.scheduler.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.scheduler.entity.SchedulerJobParam;

import java.util.List;
import java.util.Map;

/**
 * 调度任务参数 服务接口类
 *
 * @author wqliu
 * @date 2023-05-24
 */
public interface SchedulerJobParamService extends BaseService<SchedulerJobParam> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 获取参数列表
     *
     * @param schedulerJobId 调度任务标识
     * @return {@link List}<{@link SchedulerJobParam}>
     */
    List<SchedulerJobParam> getBySchedulerJobId(String schedulerJobId);
}

