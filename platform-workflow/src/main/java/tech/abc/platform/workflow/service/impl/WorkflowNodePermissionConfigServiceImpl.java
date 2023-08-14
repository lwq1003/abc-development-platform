package tech.abc.platform.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.YesOrNoEnum;
import tech.abc.platform.system.entity.PermissionItem;
import tech.abc.platform.system.enums.PermissionTypeEnum;
import tech.abc.platform.system.service.PermissionItemService;
import tech.abc.platform.workflow.entity.AreaPermission;
import tech.abc.platform.workflow.entity.WorkflowNodeConfig;
import tech.abc.platform.workflow.entity.WorkflowNodePermissionConfig;

import tech.abc.platform.workflow.enums.NodePermissionCodeEnum;
import tech.abc.platform.workflow.mapper.WorkflowNodePermissionConfigMapper;
import tech.abc.platform.workflow.service.WorkflowNodePermissionConfigService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* 工作流程环节权限配置 服务实现类
*
* @author wqliu
* @date 2023-08-07
*/
@Service
@Slf4j
public class WorkflowNodePermissionConfigServiceImpl extends BaseServiceImpl<WorkflowNodePermissionConfigMapper, WorkflowNodePermissionConfig> implements WorkflowNodePermissionConfigService {

    @Autowired
    private PermissionItemService permissionItemService;

    @Autowired
    private RepositoryService repositoryService;



    @Override
    public WorkflowNodePermissionConfig init() {
        WorkflowNodePermissionConfig entity=new WorkflowNodePermissionConfig();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        entity.setPermission("READONLY");
        return entity;
    }

    @Override
    public void beforeAdd(WorkflowNodePermissionConfig entity) {
        //唯一性验证

    }

    @Override
    public void beforeModify(WorkflowNodePermissionConfig entity) {
        //唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<WorkflowNodePermissionConfig> list = this.lambdaQuery().in(WorkflowNodePermissionConfig::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getNodeId());
                });
            }
        }
        return result;
    }

    @Override
    public List<WorkflowNodePermissionConfig> getNodePermissionConfig(String processDefinitionId, String definitionKey) {
        // 优先查询已存在的配置信息
        List<WorkflowNodePermissionConfig> list = this.lambdaQuery()
                .eq(WorkflowNodePermissionConfig::getProcessDefinitionId,processDefinitionId)
                .eq(WorkflowNodePermissionConfig::getNodeId, definitionKey).list();
        if(CollectionUtils.isEmpty(list)){
            String processDefinitionKey = processDefinitionId.split(":")[0];
            // 不存在配置信息，构建默认返回
            PermissionItem flowPermission = permissionItemService.lambdaQuery().eq(PermissionItem::getCode, processDefinitionKey.toLowerCase())
                    .eq(PermissionItem::getType, PermissionTypeEnum.PROCESS.name()).one();

            List<PermissionItem> areaList = permissionItemService.lambdaQuery().eq(PermissionItem::getPermissionItem, flowPermission.getId())
                    .eq(PermissionItem::getType, PermissionTypeEnum.AREA.name()).orderByAsc(PermissionItem::getOrderNo)
                    .list();

            for(int i=0;i<areaList.size();i++){
                PermissionItem areaPermission=areaList.get(i);
                WorkflowNodePermissionConfig config=new WorkflowNodePermissionConfig();
                config.setProcessDefinitionId(processDefinitionId);
                config.setNodeId(definitionKey);
                config.setAreaName(areaPermission.getName());
                config.setAreaCode(areaPermission.getCode());

                //默认设置为只读，适用于大多数情况
                config.setPermission(NodePermissionCodeEnum.READONLY.name());
                //保存数据
                add(config);
                //追加到结果中
                list.add(config);
            }
        }

        return list;
    }

    @Override
    protected void copyPropertyHandle(WorkflowNodePermissionConfig entity, String... value) {
        entity.setProcessDefinitionId(value[0]);

    }



    // public List<AreaPermission>  getPermissionConfig(String processDefinitionId, String nodeId) {
    //     List<WorkflowNodePermissionConfig> list = this.lambdaQuery().eq(WorkflowNodePermissionConfig::getProcessDefinitionId, processDefinitionId)
    //             .eq(WorkflowNodePermissionConfig::getNodeId, nodeId).list();
    //
    //     return list.stream().map(x -> {
    //         AreaPermission areaPermission = new AreaPermission();
    //         areaPermission.setCode(x.getAreaCode());
    //         areaPermission.setPermission(x.getPermission());
    //         return areaPermission;
    //
    //     }).collect(Collectors.toList());
    //
    //
    // }

    @Override
    public void updateProcessDefinitionId(String processDefinitionId,String tempProcessDefinitionId) {

        UpdateWrapper<WorkflowNodePermissionConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(WorkflowNodePermissionConfig::getProcessDefinitionId, processDefinitionId)
                .eq(WorkflowNodePermissionConfig::getProcessDefinitionId, tempProcessDefinitionId);
        update(updateWrapper);

    }

    @Override
    public List<WorkflowNodePermissionConfig> getNodePermissionConfigForView(String processDefinitionId) {
        String processDefinitionKey = processDefinitionId.split(":")[0];
        // 查询所有区域
        PermissionItem flowPermission = permissionItemService.lambdaQuery().eq(PermissionItem::getCode, processDefinitionKey.toLowerCase())
                .eq(PermissionItem::getType, PermissionTypeEnum.PROCESS.name()).one();

        List<PermissionItem> areaList = permissionItemService.lambdaQuery().eq(PermissionItem::getPermissionItem, flowPermission.getId())
                .eq(PermissionItem::getType, PermissionTypeEnum.AREA.name()).orderByAsc(PermissionItem::getOrderNo)
                .list();

        List<WorkflowNodePermissionConfig> list=new ArrayList<>();

        for(int i=0;i<areaList.size();i++){
            PermissionItem areaPermission=areaList.get(i);
            WorkflowNodePermissionConfig config=new WorkflowNodePermissionConfig();

            config.setAreaCode(areaPermission.getCode());
            //设置为只读
            config.setPermission(NodePermissionCodeEnum.READONLY.name());

            //追加到结果中
            list.add(config);
        }
        return  list;
    }

    @Override
    public List<WorkflowNodePermissionConfig> getByProcessDefinitionId(String processDefinitionId) {
        return this.lambdaQuery().eq(WorkflowNodePermissionConfig::getProcessDefinitionId, processDefinitionId).list();
    }

    @Override
    public void removeByProcessDefinitionId(String processDefinitionId) {
        List<WorkflowNodePermissionConfig> workflowNodePermissionConfigList = getByProcessDefinitionId(processDefinitionId);
        for(WorkflowNodePermissionConfig item:workflowNodePermissionConfigList ){
            removeById(item.getId());
        }
    }
}

