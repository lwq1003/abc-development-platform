package tech.abc.platform.entityconfig.vo;


import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 视图按钮模板 视图对象类
*
* @author wqliu
* @date 2023-04-16
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ViewButtonTemplateVO extends BaseVO {
    /**
    * 按钮类型
    */
    @NotBlank(message = "按钮类型不能为空")
    private String buttonType;

    /**
    * 名称
    */
    @NotBlank(message = "名称不能为空")
    private String name;

    /**
    * 编码
    */
    @NotBlank(message = "编码不能为空")
    private String code;

    /**
    * 内容
    */
    private String content;

    /**
    * 图标
    */
    private String icon;

    /**
    * 是否需确认
    */
    @NotBlank(message = "是否需确认不能为空")
    private String confirmFlag;

    /**
    * 确认信息
    */
    private String confirmMessage;

    /**
    * 是否控制权限
    */
    @NotBlank(message = "是否控制权限不能为空")
    private String permissionFlag;

    /**
    * 权限编码
    */
    private String permissionCode;

    /**
    * 是否用于更多
    */
    @NotBlank(message = "是否用于更多不能为空")
    private String moreFlag;

    /**
    * 排序
    */
    private String orderNo;


    /********字典类*****/
    /**
    * 按钮类型
    */
    private String buttonTypeName;
    /**
    * 是否需确认
    */
    private String confirmFlagName;
    /**
    * 是否控制权限
    */
    private String permissionFlagName;
    /**
    * 是否用于更多
    */
    private String moreFlagName;

    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/

}
