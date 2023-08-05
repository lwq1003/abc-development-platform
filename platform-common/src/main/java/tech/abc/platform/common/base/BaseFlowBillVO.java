package tech.abc.platform.common.base;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 流程表单基类
 *
 * @author wqliu
 * @date 2023-07-22
 */
@Data
public class BaseFlowBillVO extends BaseVO {



    /**
     * 发起人
     */
    private String initiateUserId;
    /**
     * 发起人姓名
     */
    private String initiateUserName;
    /**
     * 发起人电话
     */
    private String initiateUserPhone;

    /**
     * 发起部门
     */
    private String initiateOrganizationId;
    /**
     * 发起部门名称
     */
    private String initiateOrganizationName;
    /**
     * 发起部门全称
     */
    private String initiateOrganizationFullName;

    /**
     * 发起时间
     */
    private LocalDateTime initiateTime;


    /**
     * 流程类型名称
     */
    private String flowTypeName;
    /**
     * 流程实例标识
     */
    private String flowInstanceId;

    /**
     * 流程状态
     */
    private String flowStatus;



    /********字典类*****/
    /**
     * 流程状态
     */
    private String flowStatusName;

}


