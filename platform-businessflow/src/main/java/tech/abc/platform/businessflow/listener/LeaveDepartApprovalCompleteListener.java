package tech.abc.platform.businessflow.listener;

import org.apache.commons.collections4.CollectionUtils;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import tech.abc.platform.businessflow.entity.Leave;
import tech.abc.platform.businessflow.service.LeaveService;
import tech.abc.platform.common.utils.SpringUtil;
import tech.abc.platform.workflow.constant.WorkFlowConstant;
import tech.abc.platform.workflow.entity.WorkflowComment;
import tech.abc.platform.workflow.service.WorkflowCommentService;

import java.util.ArrayList;
import java.util.List;


/**
 * 任务监听器——请假申请部门审批完成监听器
 *
 * @author wqliu
 * @date 2023-07-25
 */
public class LeaveDepartApprovalCompleteListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        // 获取事件名称
        String eventName = delegateTask.getEventName();
        if(eventName.equals(WorkFlowConstant.EVENT_NAME_COMPLETE)){
            // 将部门审批意见、审批人、审批时间更新到表单
            String processInstanceId = delegateTask.getProcessInstanceId();

            // 获取请假申请单据
            LeaveService leaveService= SpringUtil.getBean(LeaveService.class);
            Leave leave = leaveService.getByprocessInstanceId(processInstanceId);

            // 获取审批信息
            String nodeId = delegateTask.getTaskDefinitionKey();
            WorkflowCommentService workflowCommentService= SpringUtil.getBean(WorkflowCommentService.class);
            WorkflowComment workflowComment=workflowCommentService.getLastHandleInfo(processInstanceId,nodeId);

            // 更新表单
            leave.setOrganizationApprovalAdvice(workflowComment.getComment());
            leave.setOrganizationApprovalName(workflowComment.getAssigneeName());
            leave.setOrganizationApprovalTime(workflowComment.getCommitTime());

            leaveService.modify(leave);

        }
    }
}
