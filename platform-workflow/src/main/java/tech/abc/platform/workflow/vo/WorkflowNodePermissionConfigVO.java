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
* @date 2023-08-08
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkflowNodePermissionConfigVO extends BaseVO {
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
    * 区域编码
    */
    @NotBlank(message = "【区域编码】不能为空")
    private String areaCode;

    /**
    * 区域名称
    */
    @NotBlank(message = "【区域名称】不能为空")
    private String areaName;

    /**
    * 权限
    */
    @NotBlank(message = "【权限】不能为空")
    private String permission;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 权限
    */
    private String permissionName;


    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
