package tech.abc.platform.workflow.service.impl;

import tech.abc.platform.workflow.entity.WorkflowNodePermissionConfig;
import tech.abc.platform.workflow.mapper.WorkflowNodePermissionConfigMapper;
import tech.abc.platform.workflow.service.WorkflowNodePermissionConfigService;
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
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
/**
* 工作流程环节权限配置 服务实现类
*
* @author wqliu
* @date 2023-07-26
*/
@Service
@Slf4j
public class WorkflowNodePermissionConfigServiceImpl extends BaseServiceImpl<WorkflowNodePermissionConfigMapper, WorkflowNodePermissionConfig> implements WorkflowNodePermissionConfigService {

    @Override
    public WorkflowNodePermissionConfig init() {
        WorkflowNodePermissionConfig entity=new WorkflowNodePermissionConfig();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        entity.setVisibleFlag("YES");
        entity.setReadonlyFlag("YES");
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
                    result.put(x.getId(), x.getNodeName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(WorkflowNodePermissionConfig entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setNodeName (entity.getNodeName() + " 副本");
    }

}

