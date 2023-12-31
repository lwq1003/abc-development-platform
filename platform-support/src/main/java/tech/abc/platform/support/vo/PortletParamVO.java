package tech.abc.platform.support.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 组件参数 视图对象类
*
* @author wqliu
* @date 2023-06-02
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PortletParamVO extends BaseVO {
    /**
    * 组件
    */
    @NotBlank(message = "【组件】不能为空")
    private String portlet;

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
    * 值
    */
    private String value;

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

    /********实体类*****/
    /**
    * 组件
    */
    private String portletName;


    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
