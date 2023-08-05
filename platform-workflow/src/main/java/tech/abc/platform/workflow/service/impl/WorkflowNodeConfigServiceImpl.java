package tech.abc.platform.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import tech.abc.platform.workflow.entity.WorkflowNodeConfig;
import tech.abc.platform.workflow.mapper.WorkflowNodeConfigMapper;
import tech.abc.platform.workflow.service.WorkflowNodeConfigService;
import tech.abc.platform.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import tech.abc.platform.workflow.service.WorkflowTemplateService;

/**
* 流程环节配置 服务实现类
*
* @author wqliu
* @date 2023-07-16
*/
@Service
@Slf4j
public class WorkflowNodeConfigServiceImpl extends BaseServiceImpl<WorkflowNodeConfigMapper, WorkflowNodeConfig> implements WorkflowNodeConfigService {

    @Autowired
    private WorkflowTemplateService flowTemplateService;

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public WorkflowNodeConfig init() {
        WorkflowNodeConfig entity=new WorkflowNodeConfig();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        entity.setMode("NORMAL");
        entity.setSetAssigneeFlag("");
        return entity;
    }

    @Override
    public void beforeAdd(WorkflowNodeConfig entity) {
        //唯一性验证

    }

    @Override
    public void beforeModify(WorkflowNodeConfig entity) {
        //唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<WorkflowNodeConfig> list = this.lambdaQuery().in(WorkflowNodeConfig::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    public WorkflowNodeConfig getNodeConfig(String processDefinitionId, String definitionKey) {

        WorkflowNodeConfig entity = this.lambdaQuery().eq(WorkflowNodeConfig::getProcessDefinitionId, processDefinitionId)
                .eq(WorkflowNodeConfig::getNodeId, definitionKey).one();
        return entity;
    }

    @Override
    public void updateProcessDefinitionId(String processDefinitionKey,String tempProcessDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey).latestVersion().singleResult();

        UpdateWrapper<WorkflowNodeConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(WorkflowNodeConfig::getProcessDefinitionId, processDefinition.getId())
                .eq(WorkflowNodeConfig::getProcessDefinitionId, tempProcessDefinitionId);
        update(updateWrapper);

    }

    @Override
    protected void copyPropertyHandle(WorkflowNodeConfig entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName (entity.getName() + " 副本");
    }

}

