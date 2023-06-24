package tech.abc.platform.entityconfig.vo;


import tech.abc.platform.common.base.BaseVO;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 模板 视图对象类
*
* @author wqliu
* @date 2023-06-24
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TemplateVO extends BaseVO {
    /**
    * 用户单选
    */
    @NotBlank(message = "【用户单选】不能为空")
    private String userSingle;

    /**
    * 组织机构单选
    */
    private String organizationSingle;

    /**
    * 用户
    */
    private String entity;

    /**
    * 图标
    */
    @NotBlank(message = "【图标】不能为空")
    private String icon;

    /**
    * 流水号
    */
    private String serialNo;

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
    * 日期
    */
    private LocalDateTime date;

    /**
    * 时间
    */
    private LocalDateTime time;

    /**
    * 是否
    */
    @NotBlank(message = "【是否】不能为空")
    private String yesOrNo;

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
    * 是否
    */
    private String yesOrNoName;

    /**
    * 状态
    */
    private String statusName;


    /********实体类、用户单选、组织机构单选*****/
    /**
    * 用户单选
    */
    private String userSingleName;

    /**
    * 组织机构单选
    */
    private String organizationSingleName;

    /**
    * 用户
    */
    private String entityName;


    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/




}
