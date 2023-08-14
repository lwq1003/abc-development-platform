package tech.abc.platform.workflow.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 工作流程模板 视图对象类
*
* @author wqliu
* @date 2023-08-10
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkflowTemplateVO extends BaseVO {
    /**
    * 类别
    */
    @NotBlank(message = "【类别】不能为空")
    private String category;

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

    /**
    * 模板版本
    */
    private String templateVersion;

    /**
    * 启用状态
    */
    private String status;

    /**
    * 模型
    */
    private String model;

    /**
    * 排序
    */
    private String orderNo;

    /**
    * 模板状态
    */
    private String templateStatus;

    /**
    * 流程定义
    */
    private String processDefinitionId;


    /********非库表存储属性*****/



    /********字典类*****/
    /**
    * 类别
    */
    private String categoryName;

    /**
    * 启用状态
    */
    private String statusName;

    /**
    * 模板状态
    */
    private String templateStatusName;


    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
