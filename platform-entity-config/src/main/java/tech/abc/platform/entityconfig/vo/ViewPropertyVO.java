package tech.abc.platform.entityconfig.vo;


import tech.abc.platform.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 视图属性 视图对象类
 *
 * @author wqliu
 * @date 2023-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ViewPropertyVO extends BaseVO {
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
     * 控件类型
     */
    @NotBlank(message = "控件类型不能为空")
    private String widgetType;

    /**
     * 显示格式
     */
    private String formatPattern;

    /**
     * 是否只读
     */
    @NotBlank(message = "是否只读不能为空")
    private String readonlyFlag;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否显示
     */
    @NotBlank(message = "是否显示不能为空")
    private String showFlag;

    /**
     * 显示表达式
     */
    private String showExpression;


    /**
     * 是否必填
     */
    @NotBlank(message = "是否必填不能为空")
    private String requireFlag;

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
     * 是否只读
     */
    private String readonlyFlagName;
    /**
     * 是否显示
     */
    private String showFlagName;
    /**
     * 是否必填
     */
    private String requireFlagName;

    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/

}
