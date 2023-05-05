package tech.abc.platform.cip.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 应用数据权限 视图对象类
*
* @author wqliu
* @date 2023-05-03
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AppDataPermissionVO extends BaseVO {
    /**
    * 应用
    */
    @NotBlank(message = "【应用】不能为空")
    private String app;

    /**
    * 角色编码
    */
    @NotBlank(message = "【角色编码】不能为空")
    private String roleCode;

    /**
    * 业务编码
    */
    @NotBlank(message = "【业务编码】不能为空")
    private String businessCode;

    /**
    * 二级业务编码
    */
    private String secondBusinessCode;

    /**
    * 排序
    */
    private String orderNo;

    /**
    * 备注
    */
    private String remark;


    /********字典类*****/
    /**
    * 角色编码
    */
    private String roleCodeName;

    /********实体类*****/
    /**
    * 应用
    */
    private String appName;

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
