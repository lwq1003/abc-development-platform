package tech.abc.platform.businessflow.vo;


import tech.abc.platform.common.base.BaseFlowBillVO;
import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 请假单 视图对象类
*
* @author wqliu
* @date 2023-07-03
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LeaveVO extends BaseFlowBillVO {
    /**
    * 单据编号
    */
    private String billNo;

    /**
    * 开始时间
    */
    private LocalDateTime startTime;

    /**
    * 结束时间
    */
    private LocalDateTime endTime;

    /**
    * 总计天数
    */
    private Double total;

    /**
    * 请假类型
    */
    @NotBlank(message = "【请假类型】不能为空")
    private String leaveType;

    /**
    * 事由
    */
    private String reason;

    /**
    * 部门审批人
    */
    private String organizationApprovalName;

    /**
    * 部门审批时间
    */
    private LocalDateTime organizationApprovalTime;

    /**
    * 部门审批意见
    */
    private String organizationApprovalAdvice;

    /**
    * 人事审批人
    */
    private String hrApprovalName;

    /**
    * 人事审批时间
    */
    private LocalDateTime hrApprovalTime;

    /**
    * 人事审批意见
    */
    private String hrApprovalAdvice;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 请假类型
    */
    private String leaveTypeName;


    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/
    /**
    * 开始时间起
    */
    private String startTimeBeginForQuery;

    /**
    * 开始时间止
    */
    private String startTimeEndForQuery;

    /**
    * 总计天数起
    */
    private String totalBeginForQuery;

    /**
    * 总计天数止
    */
    private String totalEndForQuery;


    /********自定义扩展*****/

    /********子对象*****/




}
