package tech.abc.platform.workflow.service;

import tech.abc.platform.workflow.entity.AreaPermission;
import tech.abc.platform.workflow.entity.WorkflowNodePermissionConfig;
import tech.abc.platform.common.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 工作流程环节权限配置 服务接口类
 *
 * @author wqliu
 * @date 2023-08-07
 */
public interface WorkflowNodePermissionConfigService extends BaseService<WorkflowNodePermissionConfig> {

    /**
     * 获取标识与名称的Map集合
     *
     * @param idList 标识列表
     * @return 集合
     */
    Map<String, String> getNameMap(List<String> idList);

    /**
     * 获取节点权限配置
     *
     * @param processDefinitionId 流程定义标识
     * @param definitionKey       环节标识
     * @return {@link List}<{@link WorkflowNodePermissionConfig}>
     */
    List<WorkflowNodePermissionConfig> getNodePermissionConfig(String processDefinitionId, String definitionKey);


    /**
     * 更新流程定义标识
     *
     * @param processDefinitionId     流程定义标识
     * @param tempProcessDefinitionId 临时流程定义标识
     */
    void updateProcessDefinitionId(String processDefinitionId, String tempProcessDefinitionId);

    /**
     * 得到浏览模式下权限配置
     *
     * @param processDefinitionId 流程定义标识
     * @return {@link List}<{@link WorkflowNodePermissionConfig}>
     */
    List<WorkflowNodePermissionConfig> getNodePermissionConfigForView(String processDefinitionId);

    /**
     * 通过流程定义标识获取权限配置
     *
     * @param processDefinitionId 流程定义id
     * @return {@link List}<{@link WorkflowNodePermissionConfig}>
     */
    List<WorkflowNodePermissionConfig> getByProcessDefinitionId(String processDefinitionId);


    /**
     * 通过流程定义标识清除数据
     *
     * @param processDefinitionId 流程定义标识
     */
    void removeByProcessDefinitionId(String processDefinitionId);
}


