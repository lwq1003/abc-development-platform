package tech.abc.platform.workflow.service;

import tech.abc.platform.workflow.entity.WorkflowBackNodeConfig;
import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.workflow.entity.WorkflowBackNodeConfig;

import java.util.List;
import java.util.Map;

/**
 * 工作流程回退环节配置 服务接口类
 *
 * @author wqliu
 * @date 2023-08-23
 */
public interface WorkflowBackNodeConfigService extends BaseService<WorkflowBackNodeConfig> {

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
    * @param processDefinitionId 流程定义标识
    * @param nodeId              环节标识
    * @param jumpNodeList        回退节点列表
    */
   void updateConfig(String processDefinitionId, String nodeId, List<WorkflowBackNodeConfig> jumpNodeList);

   /**
    * 更新流程定义id
    *
    * @param processDefinitionId     流程定义id
    * @param tempProcessDefinitionId 临时流程定义id
    */
   void updateProcessDefinitionId(String processDefinitionId, String tempProcessDefinitionId);


   /**
    * 通过流程定义标识获取环节回退配置
    *
    * @param processDefinitionId 流程定义id
    * @return {@link List}<{@link WorkflowBackNodeConfig}>
    */
   List<WorkflowBackNodeConfig> getByProcessDefinitionId(String processDefinitionId);


   /**
    * 通过流程定义标识清除数据
    *
    * @param processDefinitionId 临时流程定义id
    */
   void removeByProcessDefinitionId(String processDefinitionId);

   /**
    * 获得目标节点id列表
    *
    * @param processDefinitionId 流程定义id
    * @param nodeId              节点id
    * @return {@link List}<{@link String}>
    */
   List<String> getTargetNodeIdList(String processDefinitionId, String nodeId);
}

