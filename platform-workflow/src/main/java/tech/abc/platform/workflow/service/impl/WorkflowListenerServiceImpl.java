package tech.abc.platform.workflow.service.impl;

import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.workflow.entity.WorkflowListener;
import tech.abc.platform.workflow.enums.FlowListenerTypeEnum;
import tech.abc.platform.workflow.enums.WorkflowListenerCategoryEnum;
import tech.abc.platform.workflow.mapper.WorkflowListenerMapper;
import tech.abc.platform.workflow.service.WorkflowListenerService;
import org.springframework.stereotype.Service;
/**
 * 流程监听器 服务实现类
 * @author wqliu
 * @date 2020-07-13
 *
 */
@Service
public class WorkflowListenerServiceImpl extends BaseServiceImpl<WorkflowListenerMapper, WorkflowListener> implements WorkflowListenerService {
    @Override
    public WorkflowListener init() {
        WorkflowListener entity=new WorkflowListener();
        entity.setStatus(StatusEnum.NORMAL.name());
        entity.setCategory(WorkflowListenerCategoryEnum.TASK.name());
        entity.setType(FlowListenerTypeEnum.CLASS.name());
        return entity;
    }

    @Override
    public void enable(String id) {
        WorkflowListener entity=getEntity(id);
        entity.setStatus(StatusEnum.NORMAL.name());
        modify(entity);
    }

    @Override
    public void disable(String id) {
        WorkflowListener entity=getEntity(id);
        entity.setStatus(StatusEnum.DEAD.name());
        modify(entity);
    }


}
