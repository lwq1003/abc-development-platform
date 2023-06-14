package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 桌面模板 视图对象类
*
* @author wqliu
* @date 2023-06-02
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DesktopTemplateVO extends BaseVO {
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
    * 配置
    */
    @NotBlank(message = "【配置】不能为空")
    private String config;

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
