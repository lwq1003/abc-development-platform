package tech.abc.platform.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaTaskListener;
import org.camunda.bpm.model.xml.ModelInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.system.entity.PermissionItem;
import tech.abc.platform.system.enums.PermissionTypeEnum;
import tech.abc.platform.system.service.UserService;
import tech.abc.platform.workflow.entity.*;
import tech.abc.platform.workflow.enums.FlowCodeTypeEnum;
import tech.abc.platform.workflow.enums.NodeModeEnum;
import tech.abc.platform.workflow.enums.WorkflowTemplateStatusEnum;
import tech.abc.platform.workflow.exception.WorkflowException;
import tech.abc.platform.workflow.mapper.WorkflowTemplateMapper;
import tech.abc.platform.workflow.service.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程模板 服务实现类
 *
 * @author wqliu
 * @date 2023-07-06
 */
@Service
@Slf4j
public class WorkflowTemplateServiceImpl extends BaseServiceImpl<WorkflowTemplateMapper, WorkflowTemplate> implements WorkflowTemplateService {

    /**
     * 默认模板版本
     */
    public static final String DEFAULT_TEMPLATE_VERSION = "1.0";
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkflowNodeConfigService workflowNodeConfigService;

    @Autowired
    private WorkflowNodePermissionConfigService workflowNodePermissionConfigService;

    @Autowired
    private WorkflowJumpNodeConfigService workflowJumpNodeConfigService;

    @Autowired
    private WorkflowBackNodeConfigService workflowBackNodeConfigService;

    @Autowired
    private WorkflowListenerConfigService workflowListenerConfigService;

    @Override
    public WorkflowTemplate init() {
        WorkflowTemplate entity = new WorkflowTemplate();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        // 默认值处理
        entity.setCategory("");
        entity.setStatus("NORMAL");
        entity.setTemplateStatus("UNPUBLISHED");
        entity.setTemplateVersion(DEFAULT_TEMPLATE_VERSION);
        return entity;
    }

    @Override
    public void beforeAdd(WorkflowTemplate entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(WorkflowTemplate::getName, entity.getName()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(WorkflowTemplate::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }

    }

    @Override
    public void beforeModify(WorkflowTemplate entity) {
        // 唯一性验证
        // 验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(WorkflowTemplate::getName, entity.getName())
                    .eq(WorkflowTemplate::getTemplateStatus, entity.getTemplateStatus())
                    .ne(WorkflowTemplate::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【名称】");
            }
        }
        // 验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(WorkflowTemplate::getCode, entity.getCode())
                    .eq(WorkflowTemplate::getTemplateStatus, entity.getTemplateStatus())
                    .ne(WorkflowTemplate::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST, "【编码】");
            }
        }
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<WorkflowTemplate> list = this.lambdaQuery().in(WorkflowTemplate::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(WorkflowTemplate entity, String... value) {
        // 重置属性
        entity.setTemplateStatus(WorkflowTemplateStatusEnum.UNPUBLISHED.name());
        entity.setProcessDefinitionId(this.generateTemporaryVersion(entity.getCode()));
        // 版本升级时，模板版本号小段自动加1
        if (StringUtils.isNotBlank(value[0]) && value[0].equals(YesOrNoEnum.YES.name())) {
            String templateVersion = entity.getTemplateVersion();
            String[] versionArray = templateVersion.split("\\.");
            int minVersion = Integer.parseInt(versionArray[1]);
            String newVersion = versionArray[0] + "." + (minVersion + 1);
            entity.setTemplateVersion(newVersion);
        } else {
            // 复制新增时，模板版本号默认
            entity.setTemplateVersion(DEFAULT_TEMPLATE_VERSION);
        }
    }


    @Override
    public void enable(String id) {
        WorkflowTemplate entity = query(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        WorkflowTemplate entity = query(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }

    @Override
    protected void afterAdd(WorkflowTemplate entity) {

        convertJsonToModel(entity, false);
    }

    @Override
    protected void afterModify(WorkflowTemplate entity, WorkflowTemplate orginEntity) {

        convertJsonToModel(entity, false);
    }

    @Override
    protected void beforeRemove(WorkflowTemplate entity) {
        // 获取临时流程定义标识
        String tempProcessDefinitionId = this.generateTemporaryVersion(entity.getCode());

        // 删除节点配置
        workflowNodeConfigService.removeByProcessDefinitionId(tempProcessDefinitionId);

        // 删除流程权限
        workflowNodePermissionConfigService.removeByProcessDefinitionId(tempProcessDefinitionId);

        // 删除环节回退配置
        workflowBackNodeConfigService.removeByProcessDefinitionId(tempProcessDefinitionId);

        // 删除环节跳转配置
        workflowJumpNodeConfigService.removeByProcessDefinitionId(tempProcessDefinitionId);

        // 删除监听器配置
        workflowListenerConfigService.removeByProcessDefinitionId(tempProcessDefinitionId);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(String id) {
        WorkflowTemplate entity = query(id);
        // 状态检查，仅未发布的模板允许执行发布操作
        if (entity.getTemplateStatus().equals(WorkflowTemplateStatusEnum.UNPUBLISHED.name()) == false) {
            throw new CustomException(WorkflowException.HAVE_PUBLISHED);
        }

        // 获取临时流程定义标识
        String tempProcessDefinitionId = this.generateTemporaryVersion(entity.getCode());

        // 生成模型及配置信息
        BpmnModelInstance modelInstance = convertJsonToModel(entity, true);
        // 发布
        String name = entity.getName();
        Deployment deployment = repositoryService.createDeployment().name(name)
                .addModelInstance(name + ".bpmn", modelInstance).deploy();
        String deploymentId = deployment.getId();


        // 获取流程定义标识
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId).singleResult();
        String processDefinitionId = processDefinition.getId();


        // 更新环节设置，使用正式流程定义标识替换临时流程定义标识
        workflowNodeConfigService.updateProcessDefinitionId(processDefinitionId, tempProcessDefinitionId);
        // 更新环节权限设置，使用正式流程定义标识替换临时流程定义标识
        workflowNodePermissionConfigService.updateProcessDefinitionId(processDefinitionId, tempProcessDefinitionId);
        // 更新环节回退设置，使用正式流程定义标识替换临时流程定义标识
        workflowBackNodeConfigService.updateProcessDefinitionId(processDefinitionId, tempProcessDefinitionId);
        // 更新环节跳转设置，使用正式流程定义标识替换临时流程定义标识
        workflowJumpNodeConfigService.updateProcessDefinitionId(processDefinitionId, tempProcessDefinitionId);
        // 更新监听器设置，使用正式流程定义标识替换临时流程定义标识
        workflowListenerConfigService.updateProcessDefinitionId(processDefinitionId, tempProcessDefinitionId);


        // 将现有运行中的模板归档
        setArchived(entity.getCode());

        // 补填流程模板中的流程定义标识
        entity.setProcessDefinitionId(processDefinitionId);
        // 更新状态
        entity.setTemplateStatus(WorkflowTemplateStatusEnum.RUNNING.name());
        super.updateById(entity);
    }

    /**
     * 将运行状态的流程模板归档
     *
     * @param templateCode 模板代码
     */
    private void setArchived(String templateCode) {
        Optional<WorkflowTemplate> workflowTemplate = this.lambdaQuery().eq(WorkflowTemplate::getTemplateStatus, WorkflowTemplateStatusEnum.RUNNING.name())
                .eq(WorkflowTemplate::getCode, templateCode).oneOpt();
        if (workflowTemplate.isPresent()) {
            WorkflowTemplate entity = workflowTemplate.get();
            entity.setTemplateStatus(WorkflowTemplateStatusEnum.ARCHIVED.name());
            // 此处直接调用底层更新，避免修改前检查名称是否相同（同一流程多版本，名称通常是一样的）
            super.updateById(entity);
        }

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upgrade(String id) {

        // 获取模板
        WorkflowTemplate originEntity = query(id);
        // 状态检查，仅运行中的模板允许执行升级操作
        if (originEntity.getTemplateStatus().equals(WorkflowTemplateStatusEnum.RUNNING.name()) == false) {
            throw new CustomException(WorkflowException.IS_NOT_RUNNING);
        }
        // 拷贝记录
        addByCopy(id, YesOrNoEnum.YES.name());

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void valid(String id) {

        // 获取模板
        WorkflowTemplate entity = query(id);
        // 状态检查，仅已归档的模板允许执行操作
        if (entity.getTemplateStatus().equals(WorkflowTemplateStatusEnum.ARCHIVED.name()) == false) {
            throw new CustomException(WorkflowException.IS_NOT_ARCHIVED);
        }

        // 将现有运行中的模板归档
        setArchived(entity.getCode());

        // 将当前选中置为运行状态
        entity.setTemplateStatus(WorkflowTemplateStatusEnum.RUNNING.name());
        // 此处直接调用底层更新，避免修改前检查名称是否相同（同一流程多版本，名称通常是一样的）
        super.updateById(entity);

    }

    @Override
    public String getModelByProcessDefinitionId(String processDefinitionId) {
        WorkflowTemplate entity = this.lambdaQuery().eq(WorkflowTemplate::getProcessDefinitionId, processDefinitionId).one();
        return entity.getModel();
    }

    @Override
    public List<FlowStep> getUserTaskNodeByProcessDefinitionId(String processDefinitionId) {

        String model = getModelByProcessDefinitionId(processDefinitionId);
        MyFlowNode flowNode = JSON.parseObject(model, MyFlowNode.class);
        List<FlowStep> result = new ArrayList<>();
        // 添加发起环节
        FlowStep rootStep = new FlowStep();
        BeanUtils.copyProperties(flowNode, rootStep);
        result.add(rootStep);
        // 遍历后续环节，跳过路由分支
        MyFlowNode child = flowNode.getChild();
        while (true) {
            if (child == null || StringUtils.isBlank(child.getName())) {
                break;
            }
            if (child.getType().equals(FlowCodeTypeEnum.HANDLE.name())) {
                FlowStep handleStep = new FlowStep();
                BeanUtils.copyProperties(child, handleStep);
                result.add(handleStep);
            }
            child = child.getChild();
        }

        return result;
    }


    // region  模型转换

    /**
     * 将json数据局转换为模型
     *
     * @param entity             流程模板实体
     * @param generateConfigFlag 是否生成配置
     * @return {@link BpmnModelInstance}
     */
    public BpmnModelInstance convertJsonToModel(WorkflowTemplate entity, boolean generateConfigFlag) {
        // 流程模型未配置，直接返回null对象
        if (StringUtils.isBlank(entity.getModel())) {
            return null;
        }


        // 流程定义编码
        String processDefinitionKey = entity.getCode();
        // 临时流程定义标识
        String tempProcessDefinitionId = this.generateTemporaryVersion(processDefinitionKey);


        //  解析json，反序列化为节点对象
        MyFlowNode flowNode = JSON.parseObject(entity.getModel(), MyFlowNode.class);
        log.info("json数据：{}", JSON.toJSONString(flowNode));

        // 新建流程
        ProcessBuilder processBuilder = Bpmn.createProcess()
                .name(entity.getName())
                .id(processDefinitionKey)
                .executable();
        Process process = processBuilder.getElement();

        // 开始环节
        StartEventBuilder startEventBuilder = processBuilder.startEvent().name("流程开始");

        // 流程节点转换
        FlowNode lastNode = convertJsonToModel(process, startEventBuilder.getElement(), flowNode, tempProcessDefinitionId, null, generateConfigFlag);

        // 附加结束环节
        appendEndNode(process, lastNode);

        // 构建模型
        BpmnModelInstance modelInstance = startEventBuilder.done();

        // 验证模型
        Bpmn.validateModel(modelInstance);

        return modelInstance;

    }


    /**
     * 将json转换为模型
     * 流程节点转换
     *
     * @param process            流程
     * @param parentElement      父元素
     * @param flowNode           流程节点
     * @param tempVersion        临时版本
     * @param expression         表达式
     * @param generateConfigFlag 是否生成配置信息
     * @return {@link FlowNode}
     */
    private FlowNode convertJsonToModel(Process process, FlowNode parentElement,
                                        MyFlowNode flowNode, String tempVersion, String expression, boolean generateConfigFlag) {
        // 获取模型实例
        ModelInstance modelInstance = process.getModelInstance();
        // 构建节点
        FlowNode element = null;
        FlowCodeTypeEnum type = EnumUtils.getEnum(FlowCodeTypeEnum.class, flowNode.getType());
        switch (type) {
            case ROOT:
                element = rootNodeConvert(process, parentElement, flowNode, tempVersion, generateConfigFlag);

                break;
            case HANDLE:
                element = handleNodeConvert(process, parentElement, flowNode, tempVersion, expression, generateConfigFlag);

                break;
            case INCLUSIVE_GATEWAY:

                element = gatewayNodeConvert(process, flowNode, parentElement, tempVersion, expression, generateConfigFlag);
                break;
            case SERVICE_TASK:
                // TODO

                break;
            default:
                log.error("未找到合适的类型");

        }

        // 递归处理子节点
        if (flowNode.getChild() != null && StringUtils.isNotBlank(flowNode.getChild().getName())) {
            return convertJsonToModel(process, element, flowNode.getChild(), tempVersion, expression, generateConfigFlag);
        } else {
            return element;
        }

    }

    /**
     * 发起节点转换
     *
     * @param process            流程
     * @param parentElement      父元素
     * @param flowNode           流程节点
     * @param tempVersion        临时版本
     * @param generateConfigFlag 是否生成配置
     * @return {@link FlowNode}
     */
    private FlowNode rootNodeConvert(Process process, FlowNode parentElement, MyFlowNode flowNode, String tempVersion, boolean generateConfigFlag) {
        // 创建节点
        ModelInstance modelInstance = process.getModelInstance();
        UserTask firstNode = modelInstance.newInstance(UserTask.class);
        // 设置基本属性
        firstNode.setName(flowNode.getName());
        firstNode.setCamundaAssignee("${firstNodeAssignee}");
        firstNode.setId(flowNode.getId());
        process.addChildElement(firstNode);

        // 构建边
        createSequenceFlow(process, parentElement, firstNode);
        if (generateConfigFlag) {
            // 环节配置
            String rootConfig = flowNode.getConfig();
            JSONObject nodeConfig = JSON.parseObject(rootConfig);

            // 权限配置
            List<WorkflowNodePermissionConfig> rootPermissionList = JSON.parseArray(nodeConfig.getString("permissionConfig"),
                    WorkflowNodePermissionConfig.class);
            configPermission(tempVersion, firstNode.getId(), rootPermissionList);

            // 跳转环节配置
            workflowJumpNodeConfigService.updateConfig(tempVersion, firstNode.getId(), nodeConfig.getString("jumpNodeList"));

        }
        return firstNode;
    }

    /**
     * 办理节点转换
     *
     * @param process            流程
     * @param parentElement      父元素
     * @param flowNode           流程节点
     * @param tempVersion        临时版本
     * @param expression         表达式
     * @param generateConfigFlag 是否生成配置
     * @return {@link UserTask}
     */
    private UserTask handleNodeConvert(Process process, FlowNode parentElement, MyFlowNode flowNode, String tempVersion, String expression,
                                       boolean generateConfigFlag) {
        // 生成节点
        ModelInstance modelInstance = process.getModelInstance();
        UserTask userTask = modelInstance.newInstance(UserTask.class);
        // 设置基本属性
        userTask.setName(flowNode.getName());
        userTask.setId(flowNode.getId());
        // 环节配置
        String config = flowNode.getConfig();
        JSONObject handleNodeConfig = JSON.parseObject(config);
        // 人员配置
        WorkflowNodeConfig personConfig = JSON.parseObject(handleNodeConfig.getString("personConfig"), WorkflowNodeConfig.class);
        userTask.setCamundaCandidateGroups(personConfig.getUserGroup());
        if (personConfig.getMode().equals(NodeModeEnum.COUNTERSIGN.name())) {
            // 会签模式，设置处理人为变量
            userTask.setCamundaAssignee("${assignee}");
            // 设置多实例
            MultiInstanceLoopCharacteristics loopCharacteristics =
                    modelInstance.newInstance(MultiInstanceLoopCharacteristics.class);
            loopCharacteristics.setSequential(false);
            loopCharacteristics.setCamundaCollection("${assigneeList}");
            loopCharacteristics.setCamundaElementVariable("assignee");
            userTask.addChildElement(loopCharacteristics);
        } else {
            // 普通模式，设置处理人为变量
            userTask.setCamundaAssignee("${singleHandler}");
        }
        // 监听器配置
        List<WorkflowListenerConfig> listenerConfigList = JSON.parseArray(handleNodeConfig.getString("listenerList")
                , WorkflowListenerConfig.class);
        if (CollectionUtils.isNotEmpty(listenerConfigList)) {
            ExtensionElements extensionElements = modelInstance.newInstance(ExtensionElements.class);

            for (WorkflowListenerConfig listenerConfig : listenerConfigList) {
                CamundaTaskListener listener = modelInstance.newInstance(CamundaTaskListener.class);
                listener.setCamundaEvent(listenerConfig.getEvent().toLowerCase());
                listener.setCamundaClass(listenerConfig.getCode());
                extensionElements.addChildElement(listener);
            }
            userTask.setExtensionElements(extensionElements);
        }

        // 配置后端处理
        if (generateConfigFlag) {
            // 环节配置
            personConfig.setProcessDefinitionId(tempVersion);
            personConfig.setName(userTask.getName());
            personConfig.setNodeId(userTask.getId());
            workflowNodeConfigService.add(personConfig);

            // 权限配置
            List<WorkflowNodePermissionConfig> permissionList = JSON.parseArray(handleNodeConfig.getString("permissionConfig"),
                    WorkflowNodePermissionConfig.class);
            configPermission(tempVersion, userTask.getId(), permissionList);

            // 回退环节配置
            workflowBackNodeConfigService.updateConfig(tempVersion, userTask.getId(), handleNodeConfig.getString("backNodeList"));

            // 跳转环节配置
            workflowJumpNodeConfigService.updateConfig(tempVersion, userTask.getId(), handleNodeConfig.getString("jumpNodeList"));

            // 监听器配置
            workflowListenerConfigService.updateConfig(tempVersion, userTask.getId(), handleNodeConfig.getString("listenerList"));

        }
        process.addChildElement(userTask);
        // 构建边
        SequenceFlow sequenceFlow = createSequenceFlow(process, parentElement, userTask);
        // 设置条件边
        setConditionForSequenceFlow(expression, modelInstance, sequenceFlow);

        return userTask;
    }

    /**
     * 设置条件
     *
     * @param expression    表达式
     * @param modelInstance 模型实例
     * @param sequenceFlow  边
     */
    private void setConditionForSequenceFlow(String expression, ModelInstance modelInstance, SequenceFlow sequenceFlow) {
        if (StringUtils.isNotBlank(expression)) {
            ConditionExpression conditionExpression = modelInstance.newInstance(ConditionExpression.class);
            conditionExpression.setTextContent(expression);
            sequenceFlow.setConditionExpression(conditionExpression);
            // 使用一次后置空
            expression = null;
        }
    }


    /**
     * 网关节点转换
     *
     * @param process            过程
     * @param flowNode           流程节点
     * @param parentElement      父元素
     * @param tempVersion        临时版本
     * @param expression         表达式
     * @param generateConfigFlag 是否生成配置
     * @return {@link InclusiveGateway}
     */
    private InclusiveGateway gatewayNodeConvert(Process process, MyFlowNode flowNode, FlowNode parentElement, String tempVersion, String expression, boolean generateConfigFlag) {
        // 创建节点
        ModelInstance modelInstance = process.getModelInstance();
        InclusiveGateway node = modelInstance.newInstance(InclusiveGateway.class);
        process.addChildElement(node);
        // 设置基本属性
        node.setName(flowNode.getName());
        node.setId(flowNode.getId());
        // 构建入边
        SequenceFlow inflow = createSequenceFlow(process, parentElement, node);
        // 设置条件边
        setConditionForSequenceFlow(expression, modelInstance, inflow);
        // 生成虚拟的汇聚节点
        InclusiveGateway convergeNode = modelInstance.newInstance(InclusiveGateway.class);
        process.addChildElement(convergeNode);
        convergeNode.setName("汇聚节点");
        convergeNode.setId("convergeNode" + UUID.randomUUID().toString());


        // 分支处理
        List<MyFlowNode> branchList = flowNode.getBranchList();
        // 转换分支
        branchList.stream().forEach(item -> {
            // 分支首节点涉及到在边上设置条件表达式
            MyConditionNode myConditionNode = JSON.parseObject(item.getConfig(), MyConditionNode.class);
            String branchExpression = myConditionNode.getExpression();

            if (item.getChild() != null && StringUtils.isNotBlank(item.getChild().getName())) {
                // 存在子节点，转换
                FlowNode brachEndNode = convertJsonToModel(process, node,
                        item.getChild(), tempVersion, branchExpression, generateConfigFlag);
                // 分支最后一个节点连接汇聚节点
                createSequenceFlow(process, brachEndNode, convergeNode);
            } else {
                // 不存在子节点，路由直接连接汇聚，并设置条件边
                SequenceFlow endFlow = createSequenceFlow(process, node, convergeNode);
                // 设置条件边
                setConditionForSequenceFlow(expression, modelInstance, endFlow);

            }

        });
        return convergeNode;
    }


    private void configPermission(String tempVersion, String nodeId, List<WorkflowNodePermissionConfig> permissionList) {
        // 基础权限
        List<WorkflowNodePermissionConfig> nodePermissionConfigList = workflowNodePermissionConfigService.getNodePermissionConfig(tempVersion,
                nodeId);

        // 使用前端传入权限设置更新基础权限数据
        if (CollectionUtils.isNotEmpty(permissionList)) {
            nodePermissionConfigList.stream().forEach(item -> {
                permissionList.stream().forEach(permission -> {
                    if (permission.getAreaCode().equals(item.getAreaCode())) {
                        item.setPermission(permission.getPermission());
                        return;
                    }
                });
            });
        }

        // 存库
        nodePermissionConfigList.stream().forEach(item -> {
            workflowNodePermissionConfigService.modify(item);
        });
    }


    private List<String> getFlowTemplateCodeListByPermission() {
        List<String> flowTemplateCodeList = new ArrayList<>();

        List<PermissionItem> permissionItemList = userService.getPermission(UserUtil.getId());


        if (CollectionUtils.isNotEmpty(permissionItemList)) {
            flowTemplateCodeList = permissionItemList.stream()
                    .filter(x -> x.getType().equals(PermissionTypeEnum.PROCESS.name()))
                    .map(x -> StringUtils.capitalize(x.getCode())).collect(Collectors.toList());
        }
        return flowTemplateCodeList;
    }

    @Override
    public List<WorkflowTemplate> listByPermission() {
        List<String> flowTemplateCodeList = getFlowTemplateCodeListByPermission();
        if (CollectionUtils.isNotEmpty(flowTemplateCodeList)) {
            List<WorkflowTemplate> list = this.lambdaQuery().in(WorkflowTemplate::getCode, flowTemplateCodeList)
                    .eq(WorkflowTemplate::getStatus, StatusEnum.NORMAL.toString())
                    .eq(WorkflowTemplate::getTemplateStatus, WorkflowTemplateStatusEnum.RUNNING.toString())
                    .orderByAsc(WorkflowTemplate::getOrderNo).list();
            return list;
        }

        return null;
    }

    @Override
    public void checkProcessStartPermission(String code) {
        boolean hasPermission = false;
        List<String> flowTemplateCodeList = getFlowTemplateCodeListByPermission();
        if (CollectionUtils.isEmpty(flowTemplateCodeList) || !flowTemplateCodeList.contains(code)) {
            // 不包含时，抛出异常
            throw new CustomException(CommonException.NONE_START_PROCESS_PERMISSION);
        }

    }

    @Override
    public WorkflowTemplate getByCode(String code) {
        Optional<WorkflowTemplate> workflowTemplate = this.lambdaQuery().eq(WorkflowTemplate::getCode, code)
                .eq(WorkflowTemplate::getTemplateStatus, WorkflowTemplateStatusEnum.RUNNING.name())
                .oneOpt();
        if (workflowTemplate.isPresent()) {
            return workflowTemplate.get();
        } else {
            throw new CustomException(CommonException.OBJECT_NOT_EXIST, "流程模板");
        }

    }


    @Override
    public String generateTemporaryVersion(String processDefinitionKey) {
        // 获取最新发布的版本
        // 默认版本为0，首次创建模板
        int latestVersion = 0;
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
        if (processDefinition != null) {
            latestVersion = processDefinition.getVersion() + 1;
        }

        // 获取临时版本标识
        String tempProcessDefinitionId = processDefinitionKey + ":" + latestVersion;
        return tempProcessDefinitionId;


    }


    /**
     * 创建元素
     *
     * @param parentElement 父元素
     * @param id            标识
     * @param elementClass  元素类别
     * @return {@link T}
     */
    private <T extends BpmnModelElementInstance> T createElement(BpmnModelElementInstance parentElement,
                                                                 String id, Class<T> elementClass) {
        T element = parentElement.getModelInstance().newInstance(elementClass);
        element.setAttributeValue("id", id, true);
        parentElement.addChildElement(element);
        return element;
    }

    /**
     * 创建序列流
     *
     * @param process 流程
     * @param from    起始节点
     * @param to      终止节点
     * @return {@link SequenceFlow}
     */
    private SequenceFlow createSequenceFlow(Process process, FlowNode from, FlowNode to) {
        String identifier = "SequenceFlow" + UUID.randomUUID().toString();
        SequenceFlow sequenceFlow = createElement(process, identifier, SequenceFlow.class);
        process.addChildElement(sequenceFlow);
        sequenceFlow.setSource(from);
        from.getOutgoing().add(sequenceFlow);
        sequenceFlow.setTarget(to);
        to.getIncoming().add(sequenceFlow);
        return sequenceFlow;
    }

    /**
     * 附加结束节点
     *
     * @param process  流程
     * @param flowNode 流程节点
     */
    private void appendEndNode(Process process, FlowNode flowNode) {
        EndEvent endEvent = process.getModelInstance().newInstance(EndEvent.class);
        endEvent.setAttributeValue("name", "流程结束");
        process.addChildElement(endEvent);
        createSequenceFlow(process, flowNode, endEvent);
    }
    // endregion
}


