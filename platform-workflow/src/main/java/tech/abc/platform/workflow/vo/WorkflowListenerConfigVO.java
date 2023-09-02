package tech.abc.platform.workflow.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 工作流程监听器配置 视图对象类
*
* @author wqliu
* @date 2023-08-30
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkflowListenerConfigVO extends BaseVO {
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
    * 类别
    */
    @NotBlank(message = "【类别】不能为空")
    private String category;

    /**
    * 事件
    */
    @NotBlank(message = "【事件】不能为空")
    private String event;

    /**
    * 事件名称
    */
    @NotBlank(message = "【事件名称】不能为空")
    private String eventName;

    /**
    * 类型
    */
    @NotBlank(message = "【类型】不能为空")
    private String type;

    /**
    * 名称
    */
    @NotBlank(message = "【名称】不能为空")
    private String name;

    /**
    * 编码
    */
    @NotBlank(message = "【编码】不能为空")
    private String code;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 类别
    */
    private String categoryName;

    /**
    * 类型
    */
    private String typeName;


    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
