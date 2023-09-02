package tech.abc.platform.workflow.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 处理意见 视图对象类
*
* @author wqliu
* @date 2023-08-30
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkflowCommentVO extends BaseVO {
    /**
    * 流程实例
    */
    @NotBlank(message = "【流程实例】不能为空")
    private String processInstanceId;

    /**
    * 环节标识
    */
    @NotBlank(message = "【环节标识】不能为空")
    private String nodeId;

    /**
    * 环节名称
    */
    @NotBlank(message = "【环节名称】不能为空")
    private String nodeName;

    /**
    * 处理人
    */
    @NotBlank(message = "【处理人】不能为空")
    private String assignee;

    /**
    * 处理人姓名
    */
    @NotBlank(message = "【处理人姓名】不能为空")
    private String assigneeName;

    /**
    * 处理意见
    */
    @NotBlank(message = "【处理意见】不能为空")
    private String comment;

    /**
    * 提交类型
    */
    @NotBlank(message = "【提交类型】不能为空")
    private String commitType;

    /**
    * 提交时间
    */
    private LocalDateTime commitTime;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 提交类型
    */
    private String commitTypeName;


    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
