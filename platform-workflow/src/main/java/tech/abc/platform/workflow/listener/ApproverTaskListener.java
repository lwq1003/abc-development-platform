package tech.abc.platform.workflow.listener;

import tech.abc.platform.workflow.constant.WorkFlowConstant;
import org.apache.commons.collections4.CollectionUtils;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 任务监听器——审批人处理
 *
 * @author wqliu
 * @date 2023-07-25
 */
public class ApproverTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        // 获取事件名称
        String eventName = delegateTask.getEventName();
        if(eventName.equals(WorkFlowConstant.EVENT_NAME_CREATE)){
            //读取变量
            List<String> approverList=(ArrayList<String>)  delegateTask.getVariable(WorkFlowConstant.INSTANCE_APPROVER_LIST);
           if(CollectionUtils.isNotEmpty(approverList)){
               if(approverList.size()==1){
                   //只有一人，直接设置为执行人
                   delegateTask.setAssignee(approverList.get(0));
               }else{
                   //多人，多实例处理，由工作流引擎自己处理
               }
           }

        }
    }
}
