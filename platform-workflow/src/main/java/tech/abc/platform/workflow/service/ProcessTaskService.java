package tech.abc.platform.workflow.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.workflow.entity.ProcessTask;
import tech.abc.platform.workflow.entity.WorkflowNodeConfig;

import java.util.List;


/**
 * 流程任务 服务类
 * @author wqliu
 * @date 2020-09-14
 *
 */
public interface ProcessTaskService extends BaseService<ProcessTask> {

    /**
     * 提交
     * @param taskId 任务标识
     * @param comment 处理意见
     * @param nextStepId 环节标识
     * @param assigneeList 处理人列表
     */
    void commit(String taskId, String comment,String nextStepId,
                List<String> assigneeList);

    /**
     * 驳回
     * @param taskId 任务标识
     * @param comment 处理意见
     * @param nextStepId 环节标识
     * @param assigneeList 处理人列表
     */
    void reject(String taskId, String comment,String nextStepId,
                List<String> assigneeList);


    /**
     * 转办
     * @param taskId 任务标识
     * @param assignee 被转办人
     * @param comment 处理意见
     */
    void transfer(String taskId,String assignee,String comment);

    /**
     * 委派
     * @param taskId 任务标识
     * @param assignee 被委派人
     * @param comment 处理意见
     */
    void delegate(String taskId,String assignee,String comment);

    /**
     * 签收
     * @param taskId 任务标识
     */
    void signIn(String taskId);

    /**
     * 撤销签收
     * @param taskId 任务标识
     */
    void cancelSignIn(String taskId);

    /**
     * 获取正常提交环节列表（业务意义上的后续环节）
     * 排除标记为回退的条件边指向的环节
     * @param taskId 任务标识
     * @return 可提交到的环节列表
     */
    List<WorkflowNodeConfig> getForwardNodeList(String taskId);

    /**
     * 获取回退环节
     * @param taskId 任务标识
     * @return 可回退的环节列表
     */
    List<WorkflowNodeConfig> getBackNodeList(String taskId);

    /**
     * 查询我的待办
     * @param page 分页信息
     * @param queryWrapper 查询对象
     * @return 分页数据
     */
    IPage<ProcessTask> getTodo(IPage<ProcessTask> page, QueryWrapper<ProcessTask> queryWrapper);


    /**
     * 获取任务信息
     * @param id 任务标识
     * @return 任务信息
     */
    ProcessTask get(String id);
}
