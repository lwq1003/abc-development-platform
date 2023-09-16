package tech.abc.platform.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import tech.abc.platform.workflow.entity.WorkflowBackNodeConfig;
import tech.abc.platform.workflow.entity.WorkflowBackNodeConfig;
import tech.abc.platform.workflow.entity.WorkflowJumpNodeConfig;
import tech.abc.platform.workflow.mapper.WorkflowBackNodeConfigMapper;
import tech.abc.platform.workflow.service.WorkflowBackNodeConfigService;
import tech.abc.platform.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
/**
* 工作流程回退环节配置 服务实现类
*
* @author wqliu
* @date 2023-08-23
*/
@Service
@Slf4j
public class WorkflowBackNodeConfigServiceImpl extends BaseServiceImpl<WorkflowBackNodeConfigMapper, WorkflowBackNodeConfig> implements WorkflowBackNodeConfigService {

    @Override
    public WorkflowBackNodeConfig init() {
        WorkflowBackNodeConfig entity=new WorkflowBackNodeConfig();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        entity.setOrderNo("");
        return entity;
    }

    @Override
    public void beforeAdd(WorkflowBackNodeConfig entity) {
        //唯一性验证

    }

    @Override
    public void beforeModify(WorkflowBackNodeConfig entity) {
        //唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<WorkflowBackNodeConfig> list = this.lambdaQuery().in(WorkflowBackNodeConfig::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getTargetNodeId());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(WorkflowBackNodeConfig entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setTargetNodeId (entity.getTargetNodeId() + " 副本");
    }


    @Override
    public void updateConfig(String processDefinitionId, String nodeId, String configString) {
        // 先清空配置
        QueryWrapper<WorkflowBackNodeConfig> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(WorkflowBackNodeConfig::getProcessDefinitionId,processDefinitionId)
                .eq(WorkflowBackNodeConfig::getNodeId,nodeId);
        remove(queryWrapper);
        // 后生成配置
        int orderNo=0;
        // 后生成配置
        if(StringUtils.isNotBlank(configString)) {
            JSONArray jsonArray = JSON.parseArray(configString);
            for (int i = 0; i < jsonArray.size(); i++) {
                orderNo++;
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                WorkflowBackNodeConfig config = new WorkflowBackNodeConfig();
                config.setTargetNodeId(jsonObject.getString("id"));
                config.setTargetNodeName(jsonObject.getString("name"));
                config.setProcessDefinitionId(processDefinitionId);
                config.setNodeId(nodeId);
                config.setOrderNo(StringUtils.leftPad(String.valueOf(orderNo),2,"0"));
                add(config);

            }
        }

    }

    @Override
    public void updateProcessDefinitionId(String processDefinitionId, String tempProcessDefinitionId) {
        UpdateWrapper<WorkflowBackNodeConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(WorkflowBackNodeConfig::getProcessDefinitionId, processDefinitionId)
                .eq(WorkflowBackNodeConfig::getProcessDefinitionId, tempProcessDefinitionId);
        update(updateWrapper);
    }

    @Override
    public List<WorkflowBackNodeConfig> getByProcessDefinitionId(String processDefinitionId) {
        return this.lambdaQuery().eq(WorkflowBackNodeConfig::getProcessDefinitionId, processDefinitionId).list();
    }

    @Override
    public void removeByProcessDefinitionId(String processDefinitionId) {
        List<WorkflowBackNodeConfig> workflowBackNodeConfigList = getByProcessDefinitionId(processDefinitionId);
        for(WorkflowBackNodeConfig item:workflowBackNodeConfigList ){
            removeById(item.getId());
        }
    }

    @Override
    public List<String> getTargetNodeIdList(String processDefinitionId, String nodeId) {
        List<WorkflowBackNodeConfig> list = this.lambdaQuery().eq(WorkflowBackNodeConfig::getProcessDefinitionId, processDefinitionId)
                .eq(WorkflowBackNodeConfig::getNodeId, nodeId)
                .list();
        return  list.stream().map(x->x.getTargetNodeId()).collect(Collectors.toList());
    }

}

