package tech.abc.platform.cip.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 应用 视图对象类
*
* @author wqliu
* @date 2023-05-02
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AppVO extends BaseVO {
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
    * 密钥
    */
    @NotBlank(message = "【密钥】不能为空")
    private String secretKey;

    /**
    * 对接模式
    */
    @NotBlank(message = "【对接模式】不能为空")
    private String integrationModel;

    /**
    * 状态
    */
    @NotBlank(message = "【状态】不能为空")
    private String status;

    /**
    * 排序
    */
    private String orderNo;


    /********字典类*****/
    /**
    * 对接模式
    */
    private String integrationModelName;
    /**
    * 状态
    */
    private String statusName;

    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
