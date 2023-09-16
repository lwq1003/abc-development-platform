package tech.abc.platform.workflow.service;

import tech.abc.platform.workflow.entity.WorkflowListenerConfig;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.workflow.entity.WorkflowNodePermissionConfig;

import java.util.List;
import java.util.Map;

/**
 * 工作流程监听器配置 服务接口类
 *
 * @author wqliu
 * @date 2023-08-29
 */
public interface WorkflowListenerConfigService extends BaseService<WorkflowListenerConfig> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);

   /**
    * 更新配置
    *
    * @param processDefinitionId 流程定义id
    * @param nodeId              节点id
    * @param listenerList        侦听器列表
    */
   void updateConfig(String processDefinitionId, String nodeId, String listenerList);

   /**
    * 更新流程定义id
    *
    * @param processDefinitionId     流程定义id
    * @param tempProcessDefinitionId 临时流程定义id
    */
   void updateProcessDefinitionId(String processDefinitionId, String tempProcessDefinitionId);

   /**
    * 通过流程定义标识获取配置
    *
    * @param processDefinitionId 流程定义id
    * @return {@link List}<{@link WorkflowListenerConfig}>
    */
   List<WorkflowListenerConfig> getByProcessDefinitionId(String processDefinitionId);


   /**
    * 通过流程定义标识清除数据
    *
    * @param processDefinitionId 临时流程定义id
    */
   void removeByProcessDefinitionId(String processDefinitionId);

}

