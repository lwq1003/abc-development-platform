package tech.abc.platform.scheduler.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 调度任务参数 视图对象类
*
* @author wqliu
* @date 2023-05-24
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SchedulerJobParamVO extends BaseVO {
    /**
    * 调度任务
    */
    @NotBlank(message = "【调度任务】不能为空")
    private String schedulerJob;

    /**
    * 参数名称
    */
    @NotBlank(message = "【参数名称】不能为空")
    private String paramName;

    /**
    * 参数编码
    */
    @NotBlank(message = "【参数编码】不能为空")
    private String paramCode;

    /**
    * 参数值
    */
    private String paramValue;

    /**
    * 排序
    */
    private String orderNo;



    /********非库表存储属性*****/


    /********字典类*****/

    /********实体类*****/
    /**
    * 调度任务
    */
    private String schedulerJobName;


    /********范围查询*****/

    /********自定义扩展*****/
    /**
    * 忽略上级
    */
    private Boolean ignoreParent;


    /********子对象*****/




}
