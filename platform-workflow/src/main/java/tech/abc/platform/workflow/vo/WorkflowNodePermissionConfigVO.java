package tech.abc.platform.workflow.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 工作流程环节权限配置 视图对象类
*
* @author wqliu
* @date 2023-07-26
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkflowNodePermissionConfigVO extends BaseVO {
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
    * 区域标识
    */
    @NotBlank(message = "【区域标识】不能为空")
    private String areaId;

    /**
    * 处理意见
    */
    @NotBlank(message = "【处理意见】不能为空")
    private String comment;

    /**
    * 区域编码
    */
    @NotBlank(message = "【区域编码】不能为空")
    private String areaCode;

    /**
    * 是否可见
    */
    @NotBlank(message = "【是否可见】不能为空")
    private String visibleFlag;

    /**
    * 是否只读
    */
    @NotBlank(message = "【是否只读】不能为空")
    private String readonlyFlag;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 是否可见
    */
    private String visibleFlagName;

    /**
    * 是否只读
    */
    private String readonlyFlagName;


    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
