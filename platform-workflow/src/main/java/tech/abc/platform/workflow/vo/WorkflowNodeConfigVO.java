package tech.abc.platform.workflow.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 流程环节配置 视图对象类
*
* @author wqliu
* @date 2023-07-16
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkflowNodeConfigVO extends BaseVO {
    /**
     * 流程定义
     */
    @NotBlank(message = "【流程定义】不能为空")
    private String processDefinitionId;

    /**
    * 环节
    */
    @NotBlank(message = "【环节】不能为空")
    private String nodeId;

    /**
    * 名称
    */
    @NotBlank(message = "【名称】不能为空")
    private String name;

    /**
    * 模式
    */
    @NotBlank(message = "【模式】不能为空")
    private String mode;

    /**
    * 指定处理人
    */
    @NotBlank(message = "【指定处理人】不能为空")
    private String setAssigneeFlag;

    /**
    * 用户组
    */
    @NotBlank(message = "【用户组】不能为空")
    private String userGroup;

    /**
     * 用户组名称
     */
    private String userGroupName;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 模式
    */
    private String modeName;

    /**
    * 指定处理人
    */
    private String setAssigneeFlagName;


    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
