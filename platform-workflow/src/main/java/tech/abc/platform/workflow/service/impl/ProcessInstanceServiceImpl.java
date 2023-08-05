package tech.abc.platform.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstanceQuery;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.system.entity.GroupUser;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.enums.UserStatusEnum;
import tech.abc.platform.system.service.GroupUserService;
import tech.abc.platform.system.service.UserService;
import tech.abc.platform.workflow.constant.WorkFlowConstant;
import tech.abc.platform.workflow.entity.ProcessInstance;
import tech.abc.platform.workflow.mapper.ProcessInstanceMapper;
import tech.abc.platform.workflow.service.ProcessInstanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程实例 服务实现类
 * @author wqliu
 * @date 2020-09-18
 *
 */
@Service
public class ProcessInstanceServiceImpl extends BaseServiceImpl<ProcessInstanceMapper, ProcessInstance>
        implements ProcessInstanceService {

    @Autowired
    private ProcessInstanceMapper processInstanceMapper;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IdentityService identityService;
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private UserService userService;

    @Autowired
    protected MapperFacade mapperFacade;

    /**
     * 获取 我的申请
     * @param queryWrapper
     * @return
     */
    @Override
    public IPage<ProcessInstance> getApply(IPage<ProcessInstance> page, QueryWrapper<ProcessInstance> queryWrapper){

        //填充当前用户标识
        queryWrapper.lambda().eq(ProcessInstance::getProcessApplyId, UserUtil.getId());
        return processInstanceMapper.customPage(page,queryWrapper);
    }


    @Override
    public List<User> getLastCommitter(String processInstanceId, String nodeId) {
        List<User> result = new ArrayList<>();
        // 优先该环节找最后一次任务提交人
        HistoricTaskInstanceQuery historicTaskInstanceQuery=historyService.createHistoricTaskInstanceQuery();
        historicTaskInstanceQuery.processInstanceId(processInstanceId)
                .taskDefinitionKey(nodeId)
                .orderByHistoricTaskInstanceEndTime().desc();
        List<HistoricTaskInstance> historicTaskInstanceList = historicTaskInstanceQuery.list();
        if(CollectionUtils.isNotEmpty(historicTaskInstanceList)){
            HistoricTaskInstance lastTask=historicTaskInstanceList.get(0);
            String assignee=lastTask.getAssignee();
            User  user=userService.getById(assignee);
            result.add(user);

        }
        return result;
    }

    @Override
    public List<User> getNodeHandler(String processInstanceId, String nodeId) {
        List<User> result = new ArrayList<>();

        // 获取当前流程信息
        ProcessInstance processInstance=get(processInstanceId);

        // 获取流程模型
        BpmnModelInstance modelInstance = repositoryService.getBpmnModelInstance(processInstance
                .getProcessDefinitionId());

        // 获取流程节点
        ModelElementInstance node = modelInstance.getModelElementById(nodeId);
        if (node.getElementType().getTypeName().equals(WorkFlowConstant.USER_TASK_TYPE_NAME)) {
            UserTask userTask = (UserTask) node;
            // 获取用户组
            String userGroupId = userTask.getCamundaCandidateGroups();
            if (StringUtils.isNotEmpty(userGroupId)) {
                // 获取该用户组下所有人员标识
                QueryWrapper<GroupUser> groupUserQueryWrapper = new QueryWrapper<>();
                groupUserQueryWrapper.lambda().eq(GroupUser::getGroupId, userGroupId);
                List<GroupUser> groupUserList = groupUserService.list(groupUserQueryWrapper);
                List<String> userIdList = new ArrayList<>();
                for (int i = 0; i < groupUserList.size(); i++) {
                    userIdList.add(groupUserList.get(i).getUserId());
                }
                // 获取所有人员
                if (userIdList.size() > 0) {
                    LambdaQueryWrapper<User> userQueryWrapper = new QueryWrapper<User>().lambda()
                            .eq(User::getStatus, UserStatusEnum.NORMAL.name()).in(User::getId, userIdList);
                    List<User> userList = userService.list(userQueryWrapper);
                    result = userList;
                    // 获取流程启动人
                    String startUserId = processInstance.getProcessApplyId();
                    User startUser = userService.query(startUserId);
                    String startOrganizationId = startUser.getOrganization();
                    // 优先获取与流程启动人所在部门一致的人员
                    List<User> userListInSameOrganization = userList.stream()
                            .filter(x -> x.getOrganization().equals(startOrganizationId)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(userListInSameOrganization)) {
                        result = userListInSameOrganization;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public ProcessInstance get(String id) {

        // 标识非空判断
        if (StringUtils.isBlank(id) ) {
            throw new CustomException(CommonException.ID_EMPTY);
        }
        // 数据查询
        QueryWrapper<ProcessInstance> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(ProcessInstance::getId,id);
        List<ProcessInstance> entityList= processInstanceMapper.get(queryWrapper);
        if (CollectionUtils.isEmpty(entityList)) {
            throw new CustomException(CommonException.NOT_EXIST);
        }
        return entityList.get(0);

    }

}
