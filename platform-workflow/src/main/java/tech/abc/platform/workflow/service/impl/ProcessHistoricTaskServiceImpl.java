package tech.abc.platform.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.system.service.UserService;
import tech.abc.platform.workflow.constant.WorkFlowConstant;
import tech.abc.platform.workflow.entity.ProcessHistoricTask;
import tech.abc.platform.workflow.mapper.ProcessHistoricTaskMapper;

import tech.abc.platform.workflow.service.ProcessHistoricTaskService;
import tech.abc.platform.workflow.service.WorkflowCommentService;
import tech.abc.platform.common.utils.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流程历史任务 服务实现类
 * @author wqliu
 * @date 2020-09-20
 *
 */
@Service
public class ProcessHistoricTaskServiceImpl extends BaseServiceImpl<ProcessHistoricTaskMapper, ProcessHistoricTask>
        implements ProcessHistoricTaskService {

    @Autowired
    private ProcessHistoricTaskMapper processHistoricTaskMapper;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private org.camunda.bpm.engine.TaskService taskService;


    @Autowired
    private IdentityService identityService;
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;


    @Autowired
    private UserService userService;
    @Autowired
    private WorkflowCommentService workflowCommentService;




    /**
     * 获取已办任务
     * @param queryWrapper
     * @return
     */
    @Override
    public IPage<ProcessHistoricTask> getDone(IPage<ProcessHistoricTask> page, QueryWrapper<ProcessHistoricTask> queryWrapper){
        //填充当前用户标识
        queryWrapper.lambda().eq(ProcessHistoricTask::getAssignee, UserUtil.getId())
        //状态为已完成
        .eq(ProcessHistoricTask::getDeleteReason,WorkFlowConstant.TASK_DELETE_REASON_COMPLETE);
        return processHistoricTaskMapper.customPage(page,queryWrapper);
    }

    /**
     * 获取任务信息
     * @param id 任务标识
     * @return 任务信息
     */
    @Override
    public ProcessHistoricTask get(String id) {
        // 标识非空判断
        if (StringUtils.isBlank(id) ) {
            throw new CustomException(CommonException.ID_EMPTY);
        }
        QueryWrapper<ProcessHistoricTask> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(ProcessHistoricTask::getId,id);
        ProcessHistoricTask entity= processHistoricTaskMapper.get(queryWrapper);
        if (entity == null) {
            throw new CustomException(CommonException.NOT_EXIST);
        }

        return entity;
    }
}
