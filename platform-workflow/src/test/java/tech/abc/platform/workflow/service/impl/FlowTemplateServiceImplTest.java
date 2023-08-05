package tech.abc.platform.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.junit.jupiter.api.Test;
import tech.abc.platform.workflow.entity.MyFlowNode;
import tech.abc.platform.workflow.enums.FlowCodeTypeEnum;

import java.util.UUID;

/**
 * @author wqliu
 * @date 2023-07-12
 */
@Slf4j
class FlowTemplateServiceImplTest {

    @Test
    void convertJsonToModel() {


        String json="{\n" +
                "\t\"nodeName\": \"填报\",\n" +
                "\t\"nodeId\": \"1\",\n" +
                "\t\"type\": \"FIRST_NODE\",\n" +
                "\t\"userTaskNodeConfig\": {},\n" +
                "\t\"conditionList\": [],\n" +
                "\t\"childNode\": {\n" +
                "\t\t\"nodeName\": \"处理人\",\n" +
                "\t\t\"nodeId\": \"2\",\n" +
                "\t\t\"type\": \"USER_TASK\",\n" +
                "\t\t\"userTaskNodeConfig\": {\n" +
                "\t\t\t\"mode\": \"NORMAL\",\n" +
                "\t\t\t\"setAssigneeFlag\": \"YES\",\n" +
                "\t\t\t\"userGroup\": \"1\"\n" +
                "\t\t},\n" +
                "\t\t\"childNode\": {}\n" +
                "\t}\n" +
                "}";



        //  解析json，反序列化为节点对象
        MyFlowNode flowNode = JSON.parseObject(json, MyFlowNode.class);
        log.info("json数据：{}",JSON.toJSONString(flowNode));

        // 新建流程
        ProcessBuilder processBuilder = Bpmn.createProcess()
                .name("请假流程")
                // TODO 静态固化调整为动态流程编码
                .id("Leave")
                .executable();
        Process process = processBuilder.getElement();
        // 开始环节
        StartEventBuilder startEventBuilder = processBuilder.startEvent().name("流程开始");

        // 流程节点转换
        convertJsonToModel(process,startEventBuilder.getElement(),flowNode);

        // 构建模型
        BpmnModelInstance modelInstance =startEventBuilder.done();

        // 验证模型
        Bpmn.validateModel(modelInstance);

        // 转换为xml
        String xmlString = Bpmn.convertToString(modelInstance);
        log.info(xmlString);
    }


    /**
     * 流程节点转换
     *
     * @param process       流程
     * @param parentElement 父元素
     * @param flowNode      流程节点
     */
    private  void convertJsonToModel(Process process, FlowNode parentElement,
                                     MyFlowNode flowNode) {
        // 构建节点
        FlowNode element=null;
        FlowCodeTypeEnum type = EnumUtils.getEnum(FlowCodeTypeEnum.class, flowNode.getType());
        switch (type){
            case FIRST_NODE:
                UserTask firstNode = process.getModelInstance().newInstance(UserTask.class);
                firstNode.setName(flowNode.getNodeName());
                firstNode.setCamundaAssignee("${firstNodeAssignee}");
                firstNode.setId("node"+UUID.randomUUID().toString());
                process.addChildElement(firstNode);
                element=firstNode;
                break;
            case USER_TASK:

                UserTask userTask = process.getModelInstance().newInstance(UserTask.class);
                userTask.setName(flowNode.getNodeName());
                userTask.setId("node"+UUID.randomUUID().toString());
                process.addChildElement(userTask);
                element=userTask;
                // 生成环节配置
                generateUserTaskNodeConfig();
                break;
            case SERVICE_TASK:
                // TODO
                // element = process.getModelInstance().newInstance(ServiceTask.class);
                break;
            default:
                log.error("未找到合适的类型");

        }
        // element.setAttributeValue("id", "node"+UUID.randomUUID().toString(), true);
        // process.addChildElement(element);



        // 构建边
        createSequenceFlow(process, parentElement, element);

        //递归处理子节点
        if(flowNode.getChildNode()!=null && StringUtils.isNotBlank(flowNode.getChildNode().getNodeName())){
            convertJsonToModel(process,element,flowNode.getChildNode());
        }else{
            //附加结束节点
            appendEndNode(process, element);
        }

    }

    private void generateUserTaskNodeConfig() {
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
        String identifier = from.getId() + "-" + to.getId();
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
}