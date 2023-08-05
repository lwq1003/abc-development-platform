package tech.abc.platform.businessflow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseFlowBill;

import java.time.LocalDateTime;

/**
 * 请假单 实体类
 *
 * @author wqliu
 * @date 2023-07-03
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bf_leave")
public class Leave extends BaseFlowBill {

    /**
    * 单据编号
    */
    @TableField("bill_no")
    private String billNo;

    /**
    * 开始时间
    */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
    * 结束时间
    */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
    * 总计天数
    */
    @TableField("total")
    private Double total;

    /**
    * 请假类型
    */
    @TableField("leave_type")
    private String leaveType;

    /**
    * 事由
    */
    @TableField("reason")
    private String reason;

    /**
    * 部门审批人
    */
    @TableField("organization_approval_name")
    private String organizationApprovalName;

    /**
    * 部门审批时间
    */
    @TableField("organization_approval_time")
    private LocalDateTime organizationApprovalTime;

    /**
    * 部门审批意见
    */
    @TableField("organization_approval_advice")
    private String organizationApprovalAdvice;

    /**
    * 人事审批人
    */
    @TableField("hr_approval_name")
    private String hrApprovalName;

    /**
    * 人事审批时间
    */
    @TableField("hr_approval_time")
    private LocalDateTime hrApprovalTime;

    /**
    * 人事审批意见
    */
    @TableField("hr_approval_advice")
    private String hrApprovalAdvice;

    /********非库表存储属性*****/
}
