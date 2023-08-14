package tech.abc.platform.businessflow.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.businessflow.entity.Leave;
import tech.abc.platform.businessflow.mapper.LeaveMapper;
import tech.abc.platform.businessflow.service.LeaveService;
import tech.abc.platform.common.base.BaseServiceImpl;
import tech.abc.platform.common.utils.UserUtil;
import tech.abc.platform.entityconfig.service.EntityModelService;
import tech.abc.platform.system.entity.Organization;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.system.service.OrganizationService;
import tech.abc.platform.system.service.UserService;
import tech.abc.platform.workflow.constant.WorkFlowConstant;
import tech.abc.platform.workflow.entity.WorkflowTemplate;
import tech.abc.platform.workflow.enums.WorkflowInstanceStatusEnum;
import tech.abc.platform.workflow.service.WorkflowTemplateService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
* 请假单 服务实现类
*
* @author wqliu
* @date 2023-07-03
*/
@Service
@Slf4j
public class LeaveServiceImpl extends BaseServiceImpl<LeaveMapper, Leave> implements LeaveService {
    @Autowired
    private EntityModelService entityModelService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private WorkflowTemplateService flowTemplateService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;


    @Override
    public Leave init() {
        Leave entity=new Leave();
        // 预先分配标识
        entity.setId(IdWorker.getIdStr());
        //默认值处理
        entity.setLeaveType("");


        User user=userService.getById(UserUtil.getId());
        entity.setInitiateUserId(user.getId());
        entity.setInitiateUserName(user.getName());
        entity.setInitiateOrganizationId(user.getOrganization());
        entity.setInitiateUserPhone(user.getTelephone());



        Organization organization = organizationService.query(user.getOrganization());
        entity.setInitiateOrganizationName(organization.getName());
        entity.setInitiateOrganizationFullName(organizationService.getFullName(user.getOrganization()));
        return entity;
    }

    @Override
    public void beforeAdd(Leave entity) {
        //唯一性验证

        //自动生成流水号
        entity.setBillNo(entityModelService.generateSerialNo("Leave"));


    }

    @Override
    protected void afterAdd(Leave entity) {

        String userId=UserUtil.getId();
        String code= entity.getClass().getSimpleName();
        //验证流程启动权限
        flowTemplateService.checkProcessStartPermission(code);

        //设置流程启动处理人,缺失此句会导致act_hi_procinst表的START_USER_ID_字段，即流程启动人数据为空
        identityService.setAuthenticatedUserId(userId);

        // 首环节处理人默认为启动人
        Map<String,Object> instanceParams=new HashMap<>();
        instanceParams.put(WorkFlowConstant.INSTANCE_FIRST_STEP_HANDLER,userId);

        //设置请假天数
        instanceParams.put("total",entity.getTotal());


        //启动流程
        ProcessInstance processInstance =runtimeService.startProcessInstanceByKey(code,entity.getBillNo(),instanceParams);

        //更新流程相关字段
        //流程类型
        WorkflowTemplate workflowTemplate=flowTemplateService.getByCode(code);
        entity.setFlowTypeName(workflowTemplate.getName());
        //流程实例标识
        entity.setFlowInstanceId(processInstance.getProcessInstanceId());
        //流程状态
        entity.setFlowStatus(WorkflowInstanceStatusEnum.ACTIVE.name());
        //发起时间
        entity.setInitiateTime(LocalDateTime.now());

        //保存
        modify(entity);

    }

    @Override
    public void beforeModify(Leave entity) {
        //唯一性验证
    }

    @Override
    public Map<String, String> getNameMap(List<String> idList) {
        Map<String, String> result = new HashMap<>(5);
        if (CollectionUtils.isNotEmpty(idList)) {
            List<Leave> list = this.lambdaQuery().in(Leave::getId, idList).list();
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(x -> {
                    result.put(x.getId(), x.getBillNo());
                });
            }
        }
        return result;
    }

    @Override
    protected void copyPropertyHandle(Leave entity, String... value) {
        // 主属性后附加“副本”用于区分
        entity.setBillNo (entity.getBillNo() + " 副本");
    }

}

