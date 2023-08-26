package tech.abc.platform.workflow.service;

import tech.abc.platform.workflow.entity.WorkflowNodeConfig;
import tech.abc.platform.common.base.BaseService;
import java.util.List;
import java.util.Map;

/**
 * 流程环节配置 服务接口类
 *
 * @author wqliu
 * @date 2023-07-16
 */
public interface WorkflowNodeConfigService extends BaseService<WorkflowNodeConfig> {

   /**
   * 获取标识与名称的Map集合
   *
   * @param idList 标识列表
   * @return 集合
   */
   Map<String,String> getNameMap(List<String> idList);

    /**
     * 获取节点配置
     *
     * @param processDefinitionId 流程定义标识
     * @param definitionKey       环节标识
     * @return {@link WorkflowNodeConfig}
     */
    WorkflowNodeConfig getNodeConfig(String processDefinitionId, String definitionKey);

    /**
     * 更新流程定义标识
     *
     * @param processDefinitionId    流程定义标识
     * @param tempProcessDefinitionId 临时流程定义标识
     */
    void updateProcessDefinitionId(String processDefinitionId,String tempProcessDefinitionId);

    /**
     * 通过流程定义标识获取节点配置
     *
     * @param processDefinitionId 流程定义id
     * @return {@link List}<{@link WorkflowNodeConfig}>
     */
    List<WorkflowNodeConfig> getByProcessDefinitionId(String processDefinitionId);

    /**
     * 通过流程定义标识清除数据
     *
     * @param processDefinitionId 流程定义标识
     */
    void removeByProcessDefinitionId(String processDefinitionId);

 /**

  * 通过id列表获取
  *
  * @param processDefinitionId 流程定义标识
  * @param idList id列表
  * @return {@link List}<{@link WorkflowNodeConfig}>
  */
   List<WorkflowNodeConfig> getByIdList(String processDefinitionId,List<String> idList);
}

