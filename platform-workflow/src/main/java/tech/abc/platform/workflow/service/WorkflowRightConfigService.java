package tech.abc.platform.workflow.service;

import tech.abc.platform.common.base.BaseService;
import tech.abc.platform.workflow.entity.WorkflowRightConfig;

import java.util.List;
import java.util.Map;


/**
 * 流程权限配置 服务类
 * @author wqliu
 * @date 2020-11-08
 *
 */
public interface WorkflowRightConfigService extends BaseService<WorkflowRightConfig> {

    /**
     *  获取权限配置
     * @param processDefinitionId 流程定义标识
     *  @param processDefinitionKey 流程定义Key
     * @param definitionKey 环节标识
     * @return 权限配置
     */
    List<WorkflowRightConfig> getConfig(String processDefinitionId,String processDefinitionKey, String definitionKey);



    /**
     * 生成临时版本权限设置
     * 先查询是否已存在，不存在则生成
     * @param tempProcessDefinitionId 流程定义临时版本标识
     * @param processDefinitionId 流程定义最新版本标识
     */
    void generateTempNodeConfig(String tempProcessDefinitionId, String processDefinitionId);


    /**
     * 更新权限设置中的临时流程定义标识
     * @param key
     */
    void updateProcessDefinitionId(String key);

    /**
     * 更新权限配置
     * @param list 权限配置列表
     */
    void saveConfig(List<WorkflowRightConfig> list);

    /**
     * 获取流程首环节表单权限配置
     * @param processDefinitionId
     * @return
     */
    Map<String, Object> getFirstNodeConfig(String processDefinitionId);

    /**
     * 获取流程环节权限配置
     * @param processDefinitionId 流程定义标识
     * @param taskDefinitionKey  环节定义标识
     * @return
     */
    Map<String, Object> getNodeConfig(String processDefinitionId, String taskDefinitionKey);

    /**
     * 获取浏览模式下流程环节权限配置
     * @param processDefinitionId 流程定义标识
     * @return
     */
    Map<String, Object> getNodeConfigForView(String processDefinitionId);
}
