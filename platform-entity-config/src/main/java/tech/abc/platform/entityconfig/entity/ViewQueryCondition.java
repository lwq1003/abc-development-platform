package tech.abc.platform.entityconfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;

/**
 * 视图查询条件 实体类
 *
 * @author wqliu
 * @date 2023-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cfg_view_query_condition")
public class ViewQueryCondition extends BaseEntity {

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
     * 模块编码
     */
    @TableField("module_code")
    private String moduleCode;

    /**
     * 实体编码
     */
    @TableField("entity_code")
    private String entityCode;

    /**
     * 控件类型
     */
    @TableField("widget_type")
    private String widgetType;

    /**
     * 匹配规则
     */
    @TableField("match_rule")
    private String matchRule;

    /**
     * 显示格式
     */
    @TableField("format_pattern")
    private String formatPattern;

    /**
     * 默认值
     */
    @TableField("default_value")
    private String defaultValue;

    /**
     * 是否只读
     */
    @TableField("readonly_flag")
    private String readonlyFlag;

    /**
     * 是否显示
     */
    @TableField("show_flag")
    private String showFlag;

    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

}
