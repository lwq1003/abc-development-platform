package tech.abc.platform.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.platform.workflow.entity.WorkflowBackNodeConfig;
import tech.abc.platform.workflow.entity.WorkflowListenerConfig;
import tech.abc.platform.workflow.entity.WorkflowNodePermissionConfig;
import tech.abc.platform.workflow.mapper.WorkflowListenerConfigMapper;
import tech.abc.platform.workflow.service.WorkflowListenerConfigService;
import tech.abc.platform.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
/**
* 工作流程监听器配置 服务实现类
*
* @author wqliu
* @date 2023-08-29
*/
@Service
@Slf4j
public class WorkflowListenerConfigServiceImpl extends BaseServiceImpl<WorkflowListenerConfigMapper, WorkflowListenerConfig> implements WorkflowListenerConfigService {

    @Override
    public WorkflowListenerConfig init() {
        WorkflowListenerConfig entity=new WorkflowListenerConfig();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        entity.setCategory("");
        entity.setType("CLASS");
        return entity;
    }

    @Override
    public void beforeAdd(WorkflowListenerConfig entity) {
        //唯一性验证

    }

    @Override
    public void beforeModify(WorkflowListenerConfig entity) {
        //唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<WorkflowListenerConfig> list = this.lambdaQuery().in(WorkflowListenerConfig::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getName());
                });
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConfig(String processDefinitionId, String nodeId, String configString) {
        // 先清空配置
        QueryWrapper<WorkflowListenerConfig> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(WorkflowListenerConfig::getProcessDefinitionId,processDefinitionId)
                .eq(WorkflowListenerConfig::getNodeId,nodeId);
        remove(queryWrapper);
        // 后生成配置
        if(StringUtils.isNotBlank(configString)) {
            List<WorkflowListenerConfig> listenerConfigList = JSON.parseArray(configString, WorkflowListenerConfig.class);
            for (WorkflowListenerConfig config:listenerConfigList) {
                config.setProcessDefinitionId(processDefinitionId);
                config.setNodeId(nodeId);
                add(config);
            }
        }

    }

    @Override
    public void updateProcessDefinitionId(String processDefinitionId, String tempProcessDefinitionId) {
        UpdateWrapper<WorkflowListenerConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(WorkflowListenerConfig::getProcessDefinitionId, processDefinitionId)
                .eq(WorkflowListenerConfig::getProcessDefinitionId, tempProcessDefinitionId);
        update(updateWrapper);
    }

    @Override
    public List<WorkflowListenerConfig> getByProcessDefinitionId(String processDefinitionId) {
        return this.lambdaQuery().eq(WorkflowListenerConfig::getProcessDefinitionId, processDefinitionId).list();
    }


    @Override
    public void removeByProcessDefinitionId(String processDefinitionId) {
        List<WorkflowListenerConfig> list = getByProcessDefinitionId(processDefinitionId);
        for(WorkflowListenerConfig item:list ){
            removeById(item.getId());
        }

    }

    @Override
    protected void copyPropertyHandle(WorkflowListenerConfig entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setName (entity.getName() + " 副本");
    }

}

