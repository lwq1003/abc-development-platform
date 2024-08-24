package tech.abc.platform.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.javax.el.ExpressionFactory;
import org.camunda.bpm.engine.impl.javax.el.ValueExpression;
import org.camunda.bpm.engine.impl.juel.ExpressionFactoryImpl;
import org.camunda.bpm.engine.impl.juel.SimpleContext;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.system.service.UserService;
import tech.abc.platform.workflow.constant.WorkFlowConstant;
import tech.abc.platform.workflow.entity.ProcessTask;
import tech.abc.platform.workflow.entity.WorkflowNodeConfig;
import tech.abc.platform.workflow.enums.CommitTypeEnum;
import tech.abc.platform.workflow.enums.NodeModeEnum;
import tech.abc.platform.workflow.mapper.ProcessTaskMapper;
import tech.abc.platform.workflow.service.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程任务 服务实现类
 *
 * @author wqliu
 * @date 2020-09-14
 */
@Service
@Slf4j
public class ProcessTaskServiceImpl extends BaseServiceImpl<ProcessTaskMapper, ProcessTask>
        implements ProcessTaskService {


    /**
     * 发起环节编码
     */
    public static final String ROOT_CODE = "root";
    @Autowired
    private ProcessTaskMapper processTaskMapper;

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

    @Autowired
    private WorkflowNodeConfigService workflowNodeConfigService;

    @Autowired
    private WorkflowJumpNodeConfigService workflowJumpNodeConfigService;

    @Autowired
    private WorkflowBackNodeConfigService workflowBackNodeConfigService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void commit(String taskId, String comment, String nextStepId,
                       List<String> assigneeList) {
        // 设置当前处理人
        identityService.setAuthenticatedUserId(UserUtil.getId());
        // 获取任务
        ProcessTask task = get(taskId);
        // 记录处理意见
        workflowCommentService.addComment(task.getProcessInstanceId(), task.getTaskDefinitionKey(), task.getNodeName(), comment,
                CommitTypeEnum.COMMIT);
        // 判断当前是否被委派任务
        String delegation = task.getDelegation();
        if (StringUtils.isNotBlank(delegation) && delegation.equals(WorkFlowConstant.DELEGATION_PENDING)) {
            // 委派状态为等待时，调用resolveTask方法提交
            taskService.resolveTask(taskId);
            return;
        }
        // 处理任务
        handleTask(task.getProcessInstanceId(), taskId, nextStepId, assigneeList);
    }

    private void handleTask(String processInstanceId, String taskId, String nextStepId, List<String> assigneeList) {

        Map<String, Object> instanceVariable = generateInstanceVariable(taskId, nextStepId, assigneeList);
        taskService.setVariables(taskId, instanceVariable);
        taskService.complete(taskId);
    }

    /**
     * 生成实例变量
     *
     * @param taskId       任务标识
     * @param nextStepId   下一环节标识
     * @param assigneeList 处理人列表
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    private Map<String, Object> generateInstanceVariable(String taskId, String nextStepId, List<String> assigneeList) {
        // 设置下一环节及处理人实例变量
        Map<String, Object> instanceVariable = new HashMap<String, Object>(2);
        instanceVariable.put(WorkFlowConstant.INSTANCE_NEXT_STEP, nextStepId);
        // 获取环节设置
        ProcessTask processTask = get(taskId);
        WorkflowNodeConfig nodeConfig = workflowNodeConfigService
                .getNodeConfig(processTask.getProcessDefinitionId(), nextStepId);
        if (nodeConfig != null) {
            if (nodeConfig.getMode().equals(NodeModeEnum.COUNTERSIGN.name())) {
                // 会签环节
                instanceVariable.put(WorkFlowConstant.INSTANCE_APPROVER_LIST, assigneeList);
            } else {
                // 普通环节
                if (nodeConfig.getSetAssigneeFlag().equals(YesOrNoEnum.YES.name())) {
                    // 指定处理人
                    instanceVariable.put("singleHandler", assigneeList.get(0));
                } else {
                    // 不指定处理人
                    instanceVariable.put("singleHandler", null);
                }

            }
        } else {
            // 用于并行网关后续环节处理，不设置为null流程会将任务派发到空处理人
            instanceVariable.put("singleHandler", null);
        }
        return instanceVariable;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reject(String taskId, String comment, String nextStepId, List<String> assigneeList) {

        jumpTargetStep(taskId, comment, nextStepId, assigneeList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void jump(String taskId, String comment, String nextStepId, List<String> assigneeList) {
        jumpTargetStep(taskId, comment, nextStepId, assigneeList);
    }

    private void jumpTargetStep(String taskId, String comment, String nextStepId, List<String> assigneeList) {

        // 设置当前处理人
        identityService.setAuthenticatedUserId(UserUtil.getId());

        // 获取任务
        ProcessTask processTask = get(taskId);

        // 记录处理意见
        workflowCommentService.addComment(processTask.getProcessInstanceId(), processTask.getProcessDefinitionKey(), processTask.getNodeName(),
                comment,
                CommitTypeEnum.REJECT);

        // 生成实例变量
        Map<String, Object> instanceVariable = generateInstanceVariable(taskId, nextStepId, assigneeList);


        // 跳转到指定环节
        ActivityInstance activityInstance = runtimeService.getActivityInstance(processTask.getProcessInstanceId());
        runtimeService.createProcessInstanceModification(processTask.getProcessInstanceId())
                // 作废当前任务
                .cancelActivityInstance(activityInstance.getId())
                // 启动目标活动节点
                .startBeforeActivity(nextStepId)
                // 流程的可变参数赋值
                .setVariables(instanceVariable).execute();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transfer(String taskId, String assignee, String comment) {

        // 获取任务
        ProcessTask task = get(taskId);
        // 任务转办
        taskService.setAssignee(taskId, assignee);
        // 记录处理意见
        workflowCommentService.addComment(task.getProcessInstanceId(), task.getProcessDefinitionKey(), task.getNodeName(), comment,
                CommitTypeEnum.TRANSFER);

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delegate(String taskId, String assignee, String comment) {

        // 获取任务
        ProcessTask task = get(taskId);
        // 任务委派
        taskService.delegateTask(taskId, assignee);
        // 记录审批日志
        workflowCommentService.addComment(task.getProcessInstanceId(), task.getProcessDefinitionKey(), task.getNodeName(), comment,
                CommitTypeEnum.DELEGATE);

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signIn(String taskId) {

        // 获取任务
        ProcessTask task = get(taskId);
        // 设置处理人为当前用户
        taskService.setAssignee(taskId, UserUtil.getId());
        // 记录处理意见
        workflowCommentService.addComment(task.getProcessInstanceId(), task.getProcessDefinitionKey(), task.getNodeName(), "签收任务",
                CommitTypeEnum.SIGN_IN);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelSignIn(String taskId) {

        // 获取任务
        ProcessTask task = get(taskId);
        // 取消签收
        taskService.setAssignee(taskId, null);
        // 记录处理意见
        workflowCommentService.addComment(task.getProcessInstanceId(), task.getProcessDefinitionKey(), task.getNodeName(), "撤销签收",
                CommitTypeEnum.CANCEL_SIGN_IN);

    }


    @Override
    public List<WorkflowNodeConfig> getForwardNodeList(String taskId) {

        // 结束环节标识
        String endNodeId = "";

        // 后续环节列表
        List<WorkflowNodeConfig> list = new ArrayList<>();
        // 是否包含结束环节
        boolean containEndEvent = false;
        // 获取任务
        ProcessTask task = get(taskId);
        // 获取流程模型
        BpmnModelInstance modelInstance = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId());

        // 获取当前任务在模型中对应节点定义
        UserTask userTask = (UserTask) modelInstance.getModelElementById(task.getTaskDefinitionKey());
        // 获取所有流出边
        Collection<SequenceFlow> outgoing = userTask.getOutgoing();
        // 获取顺序边，或者非回退标记的条件边指向的用户任务类型节点
        List<String> nodeIdList = outgoing.stream()
                .filter(x -> x.getTarget() instanceof UserTask &&
                        (x.getConditionExpression() == null) || (
                        x.getConditionExpression() != null &&
                                !x.getConditionExpression().getTextContent().equals(WorkFlowConstant.REJECT_FLAG_VALUE)))
                .map(x -> x.getTarget().getId())
                .collect(Collectors.toList());

        // 判断是否包括网关环节
        // TODO：此处未考虑连续使用网关的复杂场景
        List<FlowNode> gatewayList = outgoing.stream().filter(x -> x.getTarget() instanceof Gateway)
                .map(x -> x.getTarget())
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(gatewayList)) {
            FlowNode gatewayNode = gatewayList.get(0);
            if (gatewayNode instanceof ExclusiveGateway) {
                // 排他网关
                // 获取输出流条件边
                Collection<SequenceFlow> sequenceFlowList = gatewayNode.getOutgoing();
                for (SequenceFlow item : sequenceFlowList) {
                    // 计算el表达式，如符合，则获取后续环节
                    boolean conditionResult = checkCondition(item.getConditionExpression().getTextContent(),
                            runtimeService.getVariables(task.getExecutionId()));
                    if (conditionResult) {
                        nodeIdList.add(item.getTarget().getId());
                        break;
                    }
                }
            } else {
                // 并行网关或包容网关
                Collection<SequenceFlow> outSequenceFlowList = gatewayNode.getOutgoing();
                if (outSequenceFlowList.size() == 1) {
                    // 流出边只有1条，认为是汇聚节点，继续往下找
                    FlowNode nextNode = outSequenceFlowList.stream().iterator().next().getTarget();
                    if (nextNode instanceof UserTask) {
                        // 用户任务加入后续环节列表中
                        nodeIdList.add(nextNode.getId());
                    } else if (nextNode instanceof EndEvent) {
                        // 结束环节做标记
                        endNodeId = nextNode.getId();
                        containEndEvent = true;
                    }
                } else if (outSequenceFlowList.size() > 1) {
                    // 流出边多条，认为是分支节点
                    WorkflowNodeConfig gatewayConfig = new WorkflowNodeConfig();
                    gatewayConfig.setSetAssigneeFlag(YesOrNoEnum.NO.name());
                    gatewayConfig.setNodeId(gatewayNode.getId());
                    gatewayConfig.setName(gatewayNode.getName());
                    list.add(gatewayConfig);

                }
            }
        }

        // 查询环节配置信息
        if (CollectionUtils.isNotEmpty(nodeIdList)) {
            QueryWrapper<WorkflowNodeConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(WorkflowNodeConfig::getProcessDefinitionId, task.getProcessDefinitionId())
                    .in(WorkflowNodeConfig::getNodeId, nodeIdList);
            list.addAll(workflowNodeConfigService.list(queryWrapper));
        }

        handleEndEvent(outgoing, endNodeId, containEndEvent, list);
        return list;
    }

    private void handleEndEvent(Collection<SequenceFlow> outgoing, String endNodeId, boolean containEndEvent, List<WorkflowNodeConfig> list) {
        // 判断是否包括结束环节
        List<String> endIdList = outgoing.stream().filter(x -> x.getTarget().getElementType().getTypeName()
                        .equals(WorkFlowConstant.END_EVENT_TYPE_NAME))
                .map(x -> x.getTarget().getId())
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(endIdList)) {
            endNodeId = endIdList.get(0);
            containEndEvent = true;
        }
        // 如包含，追加结束环节
        if (containEndEvent) {
            WorkflowNodeConfig endConfig = new WorkflowNodeConfig();
            endConfig.setSetAssigneeFlag(YesOrNoEnum.NO.name());
            endConfig.setNodeId(endNodeId);
            endConfig.setName("流程结束");
            list.add(endConfig);
        }
    }

    /**
     * 根据key和value判断el表达式是否通过信息
     *
     * @param el           el表达式信息
     * @param conditionMap el表达式传入值信息
     * @return
     */
    private boolean checkCondition(String el, Map<String, Object> conditionMap) {
        ExpressionFactory factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        for (String key : conditionMap.keySet()) {
            context.setVariable(key, factory.createValueExpression(conditionMap.get(key), Object.class));
        }
        ValueExpression e = factory.createValueExpression(context, el, boolean.class);
        return (Boolean) e.getValue(context);
    }


    @Override
    public List<WorkflowNodeConfig> getBackNodeList(String taskId) {
        // 获取任务
        ProcessTask task = get(taskId);

        // 从任务信息中获取对应的流程定义标识和环节标识
        String processDefinitionId = task.getProcessDefinitionId();
        String nodeId = task.getTaskDefinitionKey();

        // 从环节跳转配置中获取可跳转的目标节点列表
        List<String> targetNodeIdList = workflowBackNodeConfigService.getTargetNodeIdList(processDefinitionId, nodeId);

        // 根据目标节点列表获取环节配置信息
        List<WorkflowNodeConfig> entityList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(targetNodeIdList)) {
            entityList = workflowNodeConfigService.getByIdList(processDefinitionId, targetNodeIdList);
        }

        // 发起环节处理
        if (targetNodeIdList.contains(ROOT_CODE)) {
            WorkflowNodeConfig root = new WorkflowNodeConfig();
            root.setSetAssigneeFlag(YesOrNoEnum.YES.name());
            root.setNodeId(ROOT_CODE);
            root.setMode(NodeModeEnum.NORMAL.name());
            root.setName("填报");
            entityList.add(root);
        }
        return entityList;
    }


    /**
     * 获取待办任务
     *
     * @param queryWrapper
     * @return
     */
    @Override
    public IPage<ProcessTask> getTodo(IPage<ProcessTask> page, QueryWrapper<ProcessTask> queryWrapper) {
        // 获取当前用户标识
        String userId = UserUtil.getId();
        // 三种情况，一是直接设置处理人；二是未设置处理人，但设置了角色，当前拥有该角色，签收和未签收两种情况下会引发assignee是否有值
        // 第三种是首环节，只设置了处理人，未设置处理角色
        queryWrapper.lambda()
                .or(x -> x.eq(ProcessTask::getAssignee, userId)
                        .and(y -> y.eq(ProcessTask::getCandidateUser, userId)
                                .or(z -> z.isNull(ProcessTask::getAssignee))
                                .or(a -> a.isNull(ProcessTask::getCandidateUser))))

                .or(x -> x.isNull(ProcessTask::getAssignee).eq(ProcessTask::getCandidateUser, userId));

        return processTaskMapper.todoTask(page, queryWrapper);
    }


    /**
     * 获取任务信息
     *
     * @param id 任务标识
     * @return 任务信息
     */
    @Override
    public ProcessTask get(String id) {
        // 标识非空判断
        if (StringUtils.isBlank(id)) {
            throw new CustomException(CommonException.ID_EMPTY);
        }
        QueryWrapper<ProcessTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ProcessTask::getId, id);

        ProcessTask entity = processTaskMapper.get(queryWrapper);
        if (entity == null) {
            throw new CustomException(CommonException.NOT_EXIST);
        }
        return entity;
    }

    @Override
    public List<WorkflowNodeConfig> getJumpNodeList(String taskId) {
        // 获取任务
        ProcessTask task = get(taskId);

        // 从任务信息中获取对应的流程定义标识和环节标识
        String processDefinitionId = task.getProcessDefinitionId();
        String nodeId = task.getTaskDefinitionKey();

        // 从环节跳转配置中获取可跳转的目标节点列表
        List<String> targetNodeIdList = workflowJumpNodeConfigService.getTargetNodeIdList(processDefinitionId, nodeId);

        // 根据目标节点列表获取环节配置信息
        List<WorkflowNodeConfig> entityList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(targetNodeIdList)) {
            entityList = workflowNodeConfigService.getByIdList(processDefinitionId, targetNodeIdList);
        }

        // 发起环节处理
        if (targetNodeIdList.contains(ROOT_CODE)) {
            WorkflowNodeConfig root = new WorkflowNodeConfig();
            root.setSetAssigneeFlag(YesOrNoEnum.YES.name());
            root.setNodeId(ROOT_CODE);
            root.setMode(NodeModeEnum.NORMAL.name());
            root.setName("填报");
            entityList.add(root);
        }
        return entityList;
    }

    @Override
    public List<ProcessTask> getTodoPortletData(Integer count) {
        // 复用分页查询逻辑，构造参数

        // 构造分页对象
        IPage<ProcessTask> page = new Page<ProcessTask>(1, count);
        // 构造查询条件
        QueryWrapper<ProcessTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(ProcessTask::getCreateTime);

        IPage<ProcessTask> todo = this.getTodo(page, queryWrapper);

        return todo.getRecords();
    }
}
