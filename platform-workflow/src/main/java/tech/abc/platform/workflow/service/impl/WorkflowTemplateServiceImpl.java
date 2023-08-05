package tech.abc.platform.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.camunda.bpm.model.bpmn.impl.instance.ConditionExpressionImpl;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaTaskListener;
import org.camunda.bpm.model.xml.ModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.system.entity.PermissionItem;
import tech.abc.platform.system.enums.PermissionTypeEnum;
import tech.abc.platform.system.service.UserService;
import tech.abc.platform.workflow.entity.MyConditionNode;
import tech.abc.platform.workflow.entity.MyFlowNode;
import tech.abc.platform.workflow.entity.WorkflowNodeConfig;
import tech.abc.platform.workflow.entity.WorkflowTemplate;
import tech.abc.platform.workflow.enums.FlowCodeTypeEnum;
import tech.abc.platform.workflow.enums.NodeModeEnum;
import tech.abc.platform.workflow.mapper.WorkflowTemplateMapper;
import tech.abc.platform.workflow.service.WorkflowNodeConfigService;
import tech.abc.platform.workflow.service.WorkflowTemplateService;

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

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkflowNodeConfigService flowNodeConfigService;


    @Override
    public WorkflowTemplate init() {
        WorkflowTemplate entity=new WorkflowTemplate();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        entity.setCategory("");
        entity.setStatus("NORMAL");
        entity.setTemplateVersion("1.0.0");
        return entity;
    }

    @Override
    public void beforeAdd(WorkflowTemplate entity) {
        //唯一性验证
        //验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(WorkflowTemplate::getName, entity.getName()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST,"【名称】");
            }
        }
        //验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(WorkflowTemplate::getCode, entity.getCode()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST,"【编码】");
            }
        }

    }

    @Override
    public void beforeModify(WorkflowTemplate entity) {
        //唯一性验证
        //验证 名称 全局唯一
        if (StringUtils.isNotBlank(entity.getName())) {
            long countName = this.lambdaQuery().eq(WorkflowTemplate::getName, entity.getName())
                .ne(WorkflowTemplate::getId, entity.getId()).count();
            if (countName > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST,"【名称】");
            }
        }
        //验证 编码 全局唯一
        if (StringUtils.isNotBlank(entity.getCode())) {
            long countCode = this.lambdaQuery().eq(WorkflowTemplate::getCode, entity.getCode())
                .ne(WorkflowTemplate::getId, entity.getId()).count();
            if (countCode > 0) {
                throw new CustomException(CommonException.PROPERTY_EXIST,"【编码】");
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
        // 主属性后附加“副本”用于区分
        entity.setName (entity.getName() + " 副本");
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
    protected void afterModify(WorkflowTemplate entity, WorkflowTemplate orginEntity) {

        convertJsonToModel(entity);
    }


    // region  模型转换
    public void convertJsonToModel(WorkflowTemplate entity) {
        // 流程定义编码
        String processDefinitionKey=entity.getCode();
        // 临时流程定义标识
        String tempProcessDefinitionId=this.generateTempVersion(processDefinitionKey);


        // String json="{\n" +
        //         "\t\"name\": \"填报\",\n" +
        //         "\t\"id\": \"1\",\n" +
        //         "\t\"type\": \"ROOT\",\n" +
        //         "\t\"config\": {},\n" +
        //         "\t\"branchList\": [],\n" +
        //         "\t\"child\": {\n" +
        //         "\t\t\"name\": \"处理人\",\n" +
        //         "\t\t\"id\": \"2\",\n" +
        //         "\t\t\"type\": \"HANDLE\",\n" +
        //         "\t\t\"config\": {\n" +
        //         "\t\t\t\"mode\": \"NORMAL\",\n" +
        //         "\t\t\t\"setAssigneeFlag\": \"YES\",\n" +
        //         "\t\t\t\"userGroup\": \"99\"\n" +
        //         "\t\t},\n" +
        //         "\t\t\"child\": {},\n" +
        //         "\t\t\"error\": true\n" +
        //         "\t}\n" +
        //         "}";

        // String json="{\n" +
        //         "\t\"name\": \"填报\",\n" +
        //         "\t\"id\": \"1\",\n" +
        //         "\t\"type\": \"ROOT\",\n" +
        //         "\t\"config\": {},\n" +
        //         "\t\"branchList\": [],\n" +
        //         "\t\"child\": {\n" +
        //         "\t\t\"name\": \"处理人\",\n" +
        //         "\t\t\"id\": \"2\",\n" +
        //         "\t\t\"type\": \"HANDLE\",\n" +
        //         "\t\t\"config\": {\n" +
        //         "\t\t\t\"mode\": \"NORMAL\",\n" +
        //         "\t\t\t\"setAssigneeFlag\": \"YES\",\n" +
        //         "\t\t\t\"userGroup\": \"99\"\n" +
        //         "\t\t},\n" +
        //         "\t\t\"child\": {\n" +
        //         "\t\t\t\"name\": \"条件路由\",\n" +
        //         "\t\t\t\"id\": \"node3278_00b0_e238_a105\",\n" +
        //         "\t\t\t\"type\": \"INCLUSIVE_GATEWAY\",\n" +
        //         "\t\t\t\"config\": {},\n" +
        //         "\t\t\t\"child\": null,\n" +
        //         "\t\t\t\"branchList\": [{\n" +
        //         "\t\t\t\t\"name\": \"3天以内\",\n" +
        //         "\t\t\t\t\"id\": \"condition5914_12fb_e783_f171\",\n" +
        //         "\t\t\t\t\"type\": \"CONDITION\",\n" +
        //         "\t\t\t\t\"config\": {\n" +
        //         "\t\t\t\t\t\"expression\": \"${total<=3}\"\n" +
        //         "\t\t\t\t},\n" +
        //         "\t\t\t\t\"branchList\": [],\n" +
        //         "\t\t\t\t\"child\": {}\n" +
        //         "\t\t\t}, {\n" +
        //         "\t\t\t\t\"name\": \"超过3天\",\n" +
        //         "\t\t\t\t\"id\": \"condition10081_fd56_1fb6_f8ed\",\n" +
        //         "\t\t\t\t\"type\": \"CONDITION\",\n" +
        //         "\t\t\t\t\"config\": {\n" +
        //         "\t\t\t\t\t\"expression\": \"${total>3}\"\n" +
        //         "\t\t\t\t},\n" +
        //         "\t\t\t\t\"branchList\": []\n" +
        //         "\t\t\t}]\n" +
        //         "\t\t},\n" +
        //         "\t\t\"error\": true\n" +
        //         "\t}\n" +
        //         "}";

        String json=entity.getModel();


        //  解析json，反序列化为节点对象
        MyFlowNode flowNode = JSON.parseObject(json, MyFlowNode.class);
        log.info("json数据：{}",JSON.toJSONString(flowNode));

        // 新建流程
        ProcessBuilder processBuilder = Bpmn.createProcess()
                .name(entity.getName())
                .id(processDefinitionKey)
                .executable();
        Process process = processBuilder.getElement();

        // 开始环节
        StartEventBuilder startEventBuilder = processBuilder.startEvent().name("流程开始");

        // 流程节点转换
        FlowNode lastNode = convertJsonToModel(process, startEventBuilder.getElement(), flowNode, tempProcessDefinitionId,null);

        // 附加结束环节
        appendEndNode(process, lastNode);

        // 构建模型
        BpmnModelInstance modelInstance =startEventBuilder.done();

        // 验证模型
        Bpmn.validateModel(modelInstance);

        // 转换为xml
        String xmlString = Bpmn.convertToString(modelInstance);
        log.info(xmlString);

        // 发布
        String name=entity.getName();
        Deployment deployment = repositoryService.createDeployment().name(name)
                .addModelInstance(name + ".bpmn", modelInstance).deploy();
        String deploymentId = deployment.getId();


        // 获取流程定义标识
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId).singleResult();
        String processDefinitionId = processDefinition.getId();
        log.info(processDefinitionId);


        // 更新环节设置
        flowNodeConfigService.updateProcessDefinitionId(processDefinitionKey,tempProcessDefinitionId);

    }


    /**
     * 将json转换为模型
     * 流程节点转换
     *
     * @param process       流程
     * @param parentElement 父元素
     * @param flowNode      流程节点
     * @param tempVersion   临时版本
     * @param expression    表达式
     * @return {@link FlowNode}
     */
    private  FlowNode convertJsonToModel(Process process, FlowNode parentElement,
                                     MyFlowNode flowNode,String tempVersion,String expression) {
        // 获取模型实例
        ModelInstance modelInstance = process.getModelInstance();
        // 构建节点
        FlowNode element=null;
        FlowCodeTypeEnum type = EnumUtils.getEnum(FlowCodeTypeEnum.class, flowNode.getType());
        switch (type){
            case ROOT:
                UserTask firstNode = modelInstance.newInstance(UserTask.class);
                firstNode.setName(flowNode.getName());
                firstNode.setCamundaAssignee("${firstNodeAssignee}");
                firstNode.setId("node"+ UUID.randomUUID().toString());
                process.addChildElement(firstNode);
                element=firstNode;
                // 构建边
                createSequenceFlow(process, parentElement, element);
                break;
            case HANDLE:
                UserTask userTask = modelInstance.newInstance(UserTask.class);
                // 基本属性设置
                userTask.setName(flowNode.getName());
                userTask.setId("node"+UUID.randomUUID().toString());
                // 环节配置
                String config=flowNode.getConfig();
                WorkflowNodeConfig userTaskNodeConfig =JSON.parseObject(config, WorkflowNodeConfig.class) ;
                userTask.setCamundaCandidateGroups(userTaskNodeConfig.getUserGroup());
                if (userTaskNodeConfig.getMode().equals(NodeModeEnum.COUNTERSIGN.name())) {
                    //会签模式
                    //设置处理人为变量
                    userTask.setCamundaAssignee("${assignee}");
                    //设置多实例
                    MultiInstanceLoopCharacteristics loopCharacteristics =
                            modelInstance.newInstance(MultiInstanceLoopCharacteristics.class);

                    loopCharacteristics.setSequential(false);
                    loopCharacteristics.setCamundaCollection("${assigneeList}");
                    loopCharacteristics.setCamundaElementVariable("assignee");
                    userTask.addChildElement(loopCharacteristics);
                } else {
                    //普通模式
                    //设置处理人为变量
                    userTask.setCamundaAssignee("${singleHandler}");
                }

                // 附加固化的人员指派监听器
                ExtensionElements extensionElements=modelInstance.newInstance(ExtensionElements.class);

                CamundaTaskListener listener=modelInstance.newInstance(CamundaTaskListener.class);
                listener.setCamundaEvent("create");
                listener.setCamundaClass("tech.abc.platform.workflow.listener.ApproverTaskListener");
                extensionElements.addChildElement(listener);
                userTask.setExtensionElements(extensionElements);

                process.addChildElement(userTask);
                element=userTask;
                // 构建边
                SequenceFlow sequenceFlow = createSequenceFlow(process, parentElement, element);
                // 如表达式不为空，则意味着需要设置条件边
                if(StringUtils.isNotBlank(expression)){
                    ConditionExpression conditionExpression= modelInstance.newInstance(ConditionExpression.class);
                    conditionExpression.setTextContent(expression);
                    sequenceFlow.setConditionExpression(conditionExpression);
                    // 使用一次后置空
                    expression=null;
                }

                // 生成环节配置
                userTaskNodeConfig.setProcessDefinitionId(tempVersion);
                userTaskNodeConfig.setName(userTask.getName());
                userTaskNodeConfig.setNodeId(userTask.getId());
                flowNodeConfigService.add(userTaskNodeConfig);

                break;
            case INCLUSIVE_GATEWAY:
                InclusiveGateway node = modelInstance.newInstance(InclusiveGateway.class);
                process.addChildElement(node);
                // 基本属性设置
                node.setName(flowNode.getName());
                node.setId(flowNode.getId());
                // 构建入边
                SequenceFlow inflow = createSequenceFlow(process, parentElement, node);
                // 如表达式不为空，则意味着需要设置条件边
                if(StringUtils.isNotBlank(expression)){
                    ConditionExpression conditionExpression= modelInstance.newInstance(ConditionExpression.class);
                    conditionExpression.setTextContent(expression);
                    inflow.setConditionExpression(conditionExpression);
                    // 使用一次后置空
                    expression=null;
                }
                // 生成虚拟的汇聚节点
                InclusiveGateway convergeNode = modelInstance.newInstance(InclusiveGateway.class);
                process.addChildElement(convergeNode);
                convergeNode.setName("汇聚节点");
                convergeNode.setId("convergeNode"+UUID.randomUUID().toString());
                element=convergeNode;

                // 分支处理
                List<MyFlowNode> branchList = flowNode.getBranchList();
                // 转换分支
                branchList.stream().forEach(item->{
                    // 分支首节点涉及到在边上设置条件表达式
                    MyConditionNode myConditionNode = JSON.parseObject(item.getConfig(), MyConditionNode.class);
                    String branchExpression=myConditionNode.getExpression();
                    log.info("expression:{}",branchExpression);

                    if(item.getChild()!=null && StringUtils.isNotBlank(item.getChild().getName())) {


                        FlowNode brachEndNode = convertJsonToModel(process, node,
                                item.getChild(), tempVersion,branchExpression);
                        // 附加汇聚节点
                        createSequenceFlow(process, brachEndNode, convergeNode);
                    }else{
                        // 附加汇聚节点
                        SequenceFlow endFlow = createSequenceFlow(process, node, convergeNode);
                        ConditionExpression conditionExpression= modelInstance.newInstance(ConditionExpression.class);
                        conditionExpression.setTextContent(branchExpression);
                        inflow.setConditionExpression(conditionExpression);

                    }

                });

                break;
            case SERVICE_TASK:
                // TODO
                // element = modelInstance.newInstance(ServiceTask.class);
                break;
            default:
                log.error("未找到合适的类型");

        }

        //递归处理子节点
        if(flowNode.getChild()!=null && StringUtils.isNotBlank(flowNode.getChild().getName())){
            return convertJsonToModel(process,element,flowNode.getChild(),tempVersion,expression);
        }else{
            return element;
        }

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
        if(CollectionUtils.isNotEmpty(flowTemplateCodeList)){
            List<WorkflowTemplate> list = this.lambdaQuery().in(WorkflowTemplate::getCode, flowTemplateCodeList)
                    .eq(WorkflowTemplate::getStatus, StatusEnum.NORMAL.toString()).orderByAsc(WorkflowTemplate::getOrderNo).list();
            return list;
        }

        return null;
    }

    @Override
    public void checkProcessStartPermission(String code) {
        boolean hasPermission=false;
        List<String> flowTemplateCodeList = getFlowTemplateCodeListByPermission();
        if (CollectionUtils.isEmpty(flowTemplateCodeList) || !flowTemplateCodeList.contains(code)) {
            // 不包含时，抛出异常
            throw new CustomException(CommonException.NONE_START_PROCESS_PERMISSION);
        }

    }

    @Override
    public WorkflowTemplate getByCode(String code) {
        Optional<WorkflowTemplate> workflowTemplate = this.lambdaQuery().eq(WorkflowTemplate::getCode, code).oneOpt();
        if(workflowTemplate.isPresent()){
            return workflowTemplate.get();
        }else{
            throw new CustomException(CommonException.OBJECT_NOT_EXIST,"流程模板");
        }

    }

    @Override
    public String generateTempVersion(String processDefinitionKey){
        // 获取最新发布的版本
        // 默认版本为0，首次创建模板
        int latestVersion = 0;
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
        if (processDefinition != null) {
            latestVersion = processDefinition.getVersion();
        }

        // 获取临时版本标识
        String tempProcessDefinitionId = processDefinitionKey+":"+latestVersion;
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
        String identifier = "SequenceFlow"+UUID.randomUUID().toString();
        SequenceFlow sequenceFlow = createElement(process,identifier, SequenceFlow.class);
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
        // endEvent.setAttributeValue("name", "流程结束");
        process.addChildElement(endEvent);
        createSequenceFlow(process,flowNode, endEvent);
    }
    //endregion
}


