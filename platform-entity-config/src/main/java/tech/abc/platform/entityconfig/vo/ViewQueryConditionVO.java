package tech.abc.platform.entityconfig.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseVO;

import javax.validation.constraints.NotBlank;

/**
 * 视图查询条件 视图对象类
 *
 * @author wqliu
 * @date 2023-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ViewQueryConditionVO extends BaseVO {
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
     * 模块编码
     */
    private String moduleCode;

    /**
     * 实体编码
     */
    private String entityCode;

    /**
     * 控件类型
     */

    private String widgetType;

    /**
     * 匹配规则
     */
    private String matchRule;

    /**
     * 显示格式
     */
    private String formatPattern;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否只读
     */
    @NotBlank(message = "是否只读不能为空")
    private String readonlyFlag;

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
     * 匹配规则
     */
    private String matchRuleName;
    /**
     * 是否只读
     */
    private String readonlyFlagName;
    /**
     * 是否显示
     */
    private String showFlagName;

    /********实体类*****/

    /********范围查询*****/

    /********自定义扩展*****/

    /********子对象*****/

}
