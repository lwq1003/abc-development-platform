package tech.abc.platform.scheduler.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 任务 视图对象类
*
* @author wqliu
* @date 2023-05-24
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class JobVO extends BaseVO {
    /**
    * 名称
    */
    @NotBlank(message = "【名称】不能为空")
    private String name;

    /**
    * 任务类
    */
    @NotBlank(message = "【任务类】不能为空")
    private String jobClass;

    /**
    * 状态
    */
    @NotBlank(message = "【状态】不能为空")
    private String status;

    /**
    * 排序
    */
    private String orderNo;

    /**
    * 备注
    */
    private String remark;



    /********非库表存储属性*****/


    /********字典类*****/
    /**
    * 状态
    */
    private String statusName;


    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
