package tech.abc.platform.entityconfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 视图属性 实体类
 *
 * @author wqliu
 * @date 2023-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cfg_view_property")
public class ViewProperty extends BaseEntity {

    /**
     * 视图
     */
    @TableField("view")
    private String view;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 数据类型
     */
    @TableField("data_type")
    private String dataType;

    /**
     * 字典类型
     */
    @TableField("dictionary_type")
    private String dictionaryType;

    /**
     * 控件类型
     */
    @TableField("widget_type")
    private String widgetType;

    /**
     * 显示格式
     */
    @TableField("format_pattern")
    private String formatPattern;

    /**
     * 是否只读
     */
    @TableField("readonly_flag")
    private String readonlyFlag;

    /**
     * 默认值
     */
    @TableField("default_value")
    private String defaultValue;

    /**
     * 是否显示
     */
    @TableField("show_flag")
    private String showFlag;


    /**
     * 显示表达式
     */
    @TableField("show_expression")
    private String showExpression;

    /**
     * 是否必填
     */
    @TableField("require_flag")
    private String requireFlag;

    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

}
