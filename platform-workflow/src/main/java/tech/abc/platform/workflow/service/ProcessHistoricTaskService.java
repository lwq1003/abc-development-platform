package tech.abc.platform.workflow.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.workflow.entity.ProcessHistoricTask;


/**
 * 流程历史任务 服务类
 * @author wqliu
 * @date 2020-09-20
 *
 */
public interface ProcessHistoricTaskService extends BaseService<ProcessHistoricTask> {

    /**
     * 查询我的待办
     * @param page 分页信息
     * @param queryWrapper 查询对象
     * @return 分页数据
     */
    IPage<ProcessHistoricTask> getDone(IPage<ProcessHistoricTask> page, QueryWrapper<ProcessHistoricTask> queryWrapper);


    /**
     * 获取历史任务信息
     * @param id 任务标识
     * @return 任务信息
     */
    ProcessHistoricTask get(String id);
}
