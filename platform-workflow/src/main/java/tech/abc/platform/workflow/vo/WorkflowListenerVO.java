package tech.abc.platform.workflow.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 工作流监听器 视图对象类
*
* @author wqliu
* @date 2023-08-29
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkflowListenerVO extends BaseVO {
    /**
    * 名称
    */
    @NotBlank(message = "【名称】不能为空")
    private String name;

    /**
    * 类别
    */
    @NotBlank(message = "【类别】不能为空")
    private String category;

    /**
    * 类型
    */
    @NotBlank(message = "【类型】不能为空")
    private String type;

    /**
    * 内容
    */
    @NotBlank(message = "【内容】不能为空")
    private String content;

    /**
    * 状态
    */
    @NotBlank(message = "【状态】不能为空")
    private String status;

    /**
    * 排序
    */
    private String orderNo;


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

    /**
    * 状态
    */
    private String statusName;


    /********实体类、用户单选、组织机构单选*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
