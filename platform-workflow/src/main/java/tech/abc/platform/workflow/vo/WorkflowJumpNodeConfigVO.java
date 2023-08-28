package tech.abc.platform.workflow.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 工作流程跳转环节配置 视图对象类
*
* @author wqliu
* @date 2023-08-23
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkflowJumpNodeConfigVO extends BaseVO {
    /**
    * 流程定义
    */
    @NotBlank(message = "【流程定义】不能为空")
    private String processDefinitionId;

    /**
    * 环节标识
    */
    @NotBlank(message = "【环节标识】不能为空")
    private String nodeId;

    /**
    * 目标环节标识
    */
    @NotBlank(message = "【目标环节标识】不能为空")
    private String targetNodeId;

    /**
    * 目标环节名称
    */
    @NotBlank(message = "【目标环节名称】不能为空")
    private String targetNodeName;

    /**
    * 排序
    */
    @NotBlank(message = "【排序】不能为空")
    private String orderNo;


    /********非库表存储属性*****/



    /********字典类*****/

    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
