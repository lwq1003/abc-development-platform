package tech.abc.platform.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.workflow.entity.WorkflowListenerConfig;
import tech.abc.platform.workflow.mapper.WorkflowListenerConfigMapper;
import tech.abc.platform.workflow.service.WorkflowListenerConfigService;
import tech.abc.platform.workflow.utils.WorkflowUtil;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaTaskListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程监听器配置 服务实现类
 * @author wqliu
 * @date 2020-10-08
 *
 */
@Service
public class WorkflowListenerConfigServiceImpl extends BaseServiceImpl<WorkflowListenerConfigMapper, WorkflowListenerConfig> implements WorkflowListenerConfigService {

    @Autowired
    private RepositoryService repositoryService;
   
    @Override
    public WorkflowListenerConfig init() {
        WorkflowListenerConfig entity=new WorkflowListenerConfig();

        return entity;
    }



    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void generateTempListenerConfig(String tempProcessDefinitionId,String processDefinitionId) {
        //查询是否已存在
        long count=this.lambdaQuery().eq(WorkflowListenerConfig::getProcessDefinitionId,tempProcessDefinitionId).count();
        //已存在则不处理，不存在则生成
        if (count==0) {
            // 查询当前版本环节设置
            LambdaQueryWrapper<WorkflowListenerConfig> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(WorkflowListenerConfig::getProcessDefinitionId,processDefinitionId);
            List<WorkflowListenerConfig> configList=list(queryWrapper);
            for(WorkflowListenerConfig config:configList){
                //插入数据
                WorkflowListenerConfig tempListenerConfig=new WorkflowListenerConfig();
                //拷贝属性
                BeanUtils.copyProperties(config,tempListenerConfig);
                tempListenerConfig.setId(null);
                tempListenerConfig.setProcessDefinitionId(tempProcessDefinitionId);
                add(tempListenerConfig);
            }

        }
    }

    @Override
    public void updateProcessDefinitionId(String key) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(key).latestVersion().singleResult();
        Integer version=processDefinition.getVersion();
        UpdateWrapper<WorkflowListenerConfig> updateWrapper=new UpdateWrapper<>();
        updateWrapper.lambda().set(WorkflowListenerConfig::getProcessDefinitionId,processDefinition.getId())
                .eq(WorkflowListenerConfig::getProcessDefinitionId,key+":"+version);
        update(updateWrapper);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateModel(BpmnModelInstance modelInstance) {
        // 读取模型中流程定义信息
        List<Process> process = (ArrayList) modelInstance.getModelElementsByType(Process.class);
        String processDefinitionKey=process.get(0).getId();


        // 获取最新发布的版本
        // 默认版本为0，首次创建模板
        int latestVersion = 0;
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey).latestVersion().singleResult();
        if (processDefinition != null) {
            latestVersion = processDefinition.getVersion();
        }
        // 获取临时版本标识
        String tempProcessDefinitionId=WorkflowUtil.generateTempVersionId(processDefinitionKey,latestVersion);

        // 获取库表中保存的监听器设置
        LambdaQueryWrapper<WorkflowListenerConfig> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(WorkflowListenerConfig::getProcessDefinitionId,tempProcessDefinitionId);
        List<WorkflowListenerConfig> configList=list(queryWrapper);

        // 获取模型中所有用户任务节点
        Collection<UserTask> userTaskList = modelInstance.getModelElementsByType(UserTask.class);
        for(UserTask userTask:userTaskList){
            ExtensionElements extensionElements=modelInstance.newInstance(ExtensionElements.class);
            //获取库表中该节点监听器设置
            List<WorkflowListenerConfig> nodeConfigList =
                    configList.stream().filter(x -> x.getDefinitionKey().equals(userTask.getId()))
                            .collect(Collectors.toList());
            for(WorkflowListenerConfig listenerConfig:nodeConfigList){
                //TODO:只实现了类这一种方式，待日后根据需要扩展表达式和委托类
                CamundaTaskListener listener=modelInstance.newInstance(CamundaTaskListener.class);
                listener.setCamundaEvent(listenerConfig.getEvent().toLowerCase());
                listener.setCamundaClass(listenerConfig.getListenerCode());
                extensionElements.addChildElement(listener);
            }
            userTask.setExtensionElements(extensionElements);
        }

    }
}
