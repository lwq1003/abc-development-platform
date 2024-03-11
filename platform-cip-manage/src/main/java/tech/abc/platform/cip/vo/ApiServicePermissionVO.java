package tech.abc.platform.cip.vo;


import tech.abc.platform.common.base.BaseMapVO;
import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* API服务权限 视图对象类
*
* @author wqliu
* @date 2023-05-03
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ApiServicePermissionVO extends BaseMapVO {
    /**
    * 应用
    */
    @NotBlank(message = "【应用】不能为空")
    private String app;

    /**
    * API服务
    */
    @NotBlank(message = "【API服务】不能为空")
    private String apiService;


    /********字典类*****/

    /********实体类*****/
    /**
    * 应用
    */
    private String appName;
    /**
    * API服务
    */
    private String apiServiceName;

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
