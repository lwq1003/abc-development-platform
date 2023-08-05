package tech.abc.platform.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.system.entity.PermissionItem;
import tech.abc.platform.system.enums.PermissionTypeEnum;
import tech.abc.platform.system.service.PermissionItemService;
import tech.abc.platform.workflow.entity.WorkflowRightConfig;
import tech.abc.platform.workflow.mapper.WorkflowRightConfigMapper;
import tech.abc.platform.workflow.service.WorkflowRightConfigService;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流程权限配置 服务实现类
 * @author wqliu
 * @date 2020-11-08
 *
 */
@Service
public class WorkflowRightConfigServiceImpl extends BaseServiceImpl<WorkflowRightConfigMapper, WorkflowRightConfig> implements WorkflowRightConfigService {

    @Autowired
    private RepositoryService repositoryService;


    @Autowired
    private PermissionItemService permissionItemService;

    @Override
    public WorkflowRightConfig init() {
        WorkflowRightConfig entity=new WorkflowRightConfig();

        return entity;
    }



    @Override
    public List<WorkflowRightConfig> getConfig(String processDefinitionId,String processDefinitionKey,
                                               String definitionKey) {


        // 优先查询已存在的配置信息
        List<WorkflowRightConfig> list = this.lambdaQuery()
                .eq(WorkflowRightConfig::getProcessDefinitionId,processDefinitionId)
                .eq(WorkflowRightConfig::getDefinitionKey, definitionKey).list();
        if(CollectionUtils.isEmpty(list)){
            // 不存在配置信息，构建默认返回
            PermissionItem flowPermission = permissionItemService.lambdaQuery().eq(PermissionItem::getCode, processDefinitionKey)
                    .eq(PermissionItem::getType, PermissionTypeEnum.PROCESS.name()).one();

            List<PermissionItem> areaList = permissionItemService.lambdaQuery().eq(PermissionItem::getPermissionItem, flowPermission.getId())
                    .eq(PermissionItem::getType, PermissionTypeEnum.AREA.name()).orderByAsc(PermissionItem::getOrderNo)
                    .list();

            for(int i=0;i<areaList.size();i++){
                PermissionItem areaPermission=areaList.get(i);
                WorkflowRightConfig config=new WorkflowRightConfig();
                config.setProcessDefinitionId(processDefinitionId);
                config.setDefinitionKey(definitionKey);
                config.setName(areaPermission.getName());
                config.setResourceId(areaPermission.getId());
                config.setResourceCode(areaPermission.getCode());
                //默认设置为可见，适用于大多数情况
                config.setVisibleFlag(YesOrNoEnum.YES.name());
                //默认设置为只读，适用于大多数情况
                config.setReadonlyFlag(YesOrNoEnum.YES.name());
                //保存数据
                add(config);
                //追加到结果中
                list.add(config);
            }
        }

        return list;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void generateTempNodeConfig(String tempProcessDefinitionId, String processDefinitionId) {
        //查询是否已存在
        long count = this.lambdaQuery().eq(WorkflowRightConfig::getProcessDefinitionId, tempProcessDefinitionId).count();
        //已存在则不处理，不存在则生成
        if (count == 0) {
            // 查询当前版本环节设置
            LambdaQueryWrapper<WorkflowRightConfig> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WorkflowRightConfig::getProcessDefinitionId, processDefinitionId);
            List<WorkflowRightConfig> configList = list(queryWrapper);
            for (WorkflowRightConfig config : configList) {
                //插入数据
                WorkflowRightConfig tempNodeConfig = new WorkflowRightConfig();
                tempNodeConfig.setProcessDefinitionId(tempProcessDefinitionId);
                tempNodeConfig.setDefinitionKey(config.getDefinitionKey());
                tempNodeConfig.setVisibleFlag(config.getVisibleFlag());
                tempNodeConfig.setReadonlyFlag(config.getReadonlyFlag());
                tempNodeConfig.setName(config.getName());
                tempNodeConfig.setResourceCode(config.getResourceCode());
                tempNodeConfig.setResourceId(config.getResourceId());
                add(tempNodeConfig);
            }

        }
    }


    @Override
    public void updateProcessDefinitionId(String key) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(key).latestVersion().singleResult();
        Integer version = processDefinition.getVersion();
        UpdateWrapper<WorkflowRightConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(WorkflowRightConfig::getProcessDefinitionId, processDefinition.getId())
                .eq(WorkflowRightConfig::getProcessDefinitionId, key + ":" + version);
        update(updateWrapper);

    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveConfig(List<WorkflowRightConfig> list) {
        for(WorkflowRightConfig config:list){
            modify(config);
        }
    }

    @Override
    public Map<String, Object> getFirstNodeConfig(String processDefinitionId) {
        // 获取首环节
        Map<String,Object> result=new HashMap<>(5);
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);
        Collection<StartEvent> startEventList = bpmnModelInstance.getModelElementsByType(StartEvent.class);
        StartEvent startEvent = startEventList.iterator().next();
        List<FlowNode> nodeList = startEvent.getOutgoing().stream().map(x -> x.getTarget())
                .collect(Collectors.toList());
        FlowNode firstNode = nodeList.iterator().next();
        if (firstNode instanceof UserTask) {
            String nodeKey=firstNode.getId();
            result=getNodeRightConfig(processDefinitionId, nodeKey);
        }
        return result;
    }

    private Map<String, Object>  getNodeRightConfig(String processDefinitionId, String nodeKey) {
        Map<String, Object> result = new HashMap<>();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        List<WorkflowRightConfig> configList = getConfig(processDefinitionId, processDefinition.getKey(), nodeKey);

        for(WorkflowRightConfig config:configList){
            Map<String,Boolean> param=new HashMap<>(2);
            //可见性
            Boolean visible=false;
            if(config.getVisibleFlag().equals(YesOrNoEnum.YES.name())){
                visible=true;
            }
            param.put("visible",visible);
            //只读性
            Boolean readonly=false;
            if(config.getReadonlyFlag().equals(YesOrNoEnum.YES.name())){
                readonly=true;
            }
            param.put("readonly",readonly);
            result.put(config.getResourceCode(),param);
        }
        return result;
    }

    @Override
    public Map<String, Object> getNodeConfig(String processDefinitionId, String taskDefinitionKey) {
        return getNodeRightConfig(processDefinitionId,taskDefinitionKey);
    }

    @Override
    public Map<String, Object> getNodeConfigForView(String processDefinitionId) {
        Map<String, Object> result = new HashMap<>();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();

        // 查询配置信息
        List<WorkflowRightConfig> configList = this.lambdaQuery()
                .eq(WorkflowRightConfig::getProcessDefinitionId,processDefinitionId)
                .groupBy(WorkflowRightConfig::getResourceCode).list();

        for(WorkflowRightConfig config:configList){
            Map<String,Boolean> param=new HashMap<>(2);
            //可见性
            param.put("visible",true);
            //只读性
            param.put("readonly",true);
            result.put(config.getResourceCode(),param);
        }
        return result;
    }
}
