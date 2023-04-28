package tech.abc.platform.entityconfig.vo;


import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 视图查询结果 视图对象类
*
* @author wqliu
* @date 2023-04-16
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ViewQueryResultVO extends BaseVO {
    /**
    * 视图
    */
    @NotBlank(message = "视图不能为空")
    private String view;

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
    * 数据类型
    */
    @NotBlank(message = "数据类型不能为空")
    private String dataType;

    /**
    * 字典类型
    */
    private String dictionaryType;

    /**
    * 宽度
    */
    private String width;

    /**
    * 是否支持排序
    */
    @NotBlank(message = "是否支持排序不能为空")
    private String sortableFlag;

    /**
    * 格式化方法
    */
    private String formatFunction;

    /**
    * 是否悬浮显示
    */
    @NotBlank(message = "是否悬浮显示不能为空")
    private String showOverflowTooltipFlag;

    /**
    * 是否显示
    */
    @NotBlank(message = "是否显示不能为空")
    private String showFlag;

    /**
    * 排序
    */
    private String orderNo;


    /********字典类*****/
    /**
    * 数据类型
    */
    private String dataTypeName;
    /**
    * 是否支持排序
    */
    private String sortableFlagName;
    /**
    * 是否悬浮显示
    */
    private String showOverflowTooltipFlagName;
    /**
    * 是否显示
    */
    private String showFlagName;

    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/

}
