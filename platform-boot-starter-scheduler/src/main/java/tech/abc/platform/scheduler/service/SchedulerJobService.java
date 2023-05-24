package tech.abc.platform.scheduler.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.scheduler.entity.SchedulerJob;

import java.util.List;
import java.util.Map;

/**
 * 调度任务 服务接口类
 *
 * @author wqliu
 * @date 2023-05-24
 */
public interface SchedulerJobService extends BaseService<SchedulerJob> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 暂停任务
     *
     * @param id 任务标识
     */
    void pause(String id);

    /**
     * 恢复任务
     *
     * @param id 任务标识
     */
    void resume(String id);

    /**
     * 暂停所有任务
     */
    void pauseAll();

    /**
     * 恢复所有任务
     */
    void resumeAll();

    /**
     * 执行任务
     *
     * @param id 任务标识
     */
    void execute(String id);
}

