package tech.abc.platform.workflow.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.workflow.entity.WorkflowListenerConfig;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;


/**
 * 流程监听器配置 服务类
 * @author wqliu
 * @date 2020-10-08
 *
 */
public interface WorkflowListenerConfigService extends BaseService<WorkflowListenerConfig> {

    /**
     * 生成临时版本监听器设置
     * 先查询是否已存在，不存在则生成
     * @param tempProcessDefinitionId 流程定义临时版本标识
     * @param processDefinitionId 流程定义最新版本标识
     */
    void generateTempListenerConfig(String tempProcessDefinitionId,String processDefinitionId);

    /**
     * 更新监听器设置中的临时流程定义标识
     * @param key
     */
    void updateProcessDefinitionId(String key);

    /**
     * 将临时版本的监听器信息更新到流程模型中
     * @param modelInstance 流程模型
     */
    void updateModel(BpmnModelInstance modelInstance);
}
