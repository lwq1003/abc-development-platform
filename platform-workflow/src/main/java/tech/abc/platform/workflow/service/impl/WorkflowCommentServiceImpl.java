package tech.abc.platform.workflow.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseFlowBill;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.workflow.entity.WorkflowComment;
import tech.abc.platform.workflow.enums.CommitTypeEnum;
import tech.abc.platform.workflow.mapper.WorkflowCommentMapper;
import tech.abc.platform.workflow.service.WorkflowCommentService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* 处理意见 服务实现类
*
* @author wqliu
* @date 2023-07-25
*/
@Service
@Slf4j
public class WorkflowCommentServiceImpl extends BaseServiceImpl<WorkflowCommentMapper, WorkflowComment> implements WorkflowCommentService {

    @Override
    public WorkflowComment init() {
        WorkflowComment entity=new WorkflowComment();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        entity.setCommitType("");
        return entity;
    }

    @Override
    protected void beforeAdd(WorkflowComment entity) {
        entity.setAssignee(UserUtil.getId());
        entity.setAssigneeName(UserUtil.getName());
        entity.setCommitTime(LocalDateTime.now());
        super.beforeAdd(entity);
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<WorkflowComment> list = this.lambdaQuery().in(WorkflowComment::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getNodeName());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(WorkflowComment entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setNodeName (entity.getNodeName() + " 副本");
    }

    @Override
    public void addComment(String processInstanceId,String nodeId,String nodeName, String comment, CommitTypeEnum commitType) {
        WorkflowComment entity=init();
        entity.setProcessInstanceId(processInstanceId);
        entity.setNodeId(nodeId);
        entity.setNodeName(nodeName);
        entity.setComment(comment);
        entity.setCommitType(commitType.name());
        add(entity);
    }

    @Override
    public WorkflowComment getLastHandleInfo(String processInstanceId, String nodeId) {
        List<WorkflowComment> list = this.lambdaQuery().eq(WorkflowComment::getProcessInstanceId, processInstanceId)
                .eq(WorkflowComment::getNodeId,nodeId)
                .orderByDesc(WorkflowComment::getCommitTime)
                .list();
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }else{
            throw new CustomException(CommonException.NOT_EXIST);
        }

    }

}

