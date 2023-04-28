package tech.abc.platform.entityconfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 视图查询结果 实体类
 *
 * @author wqliu
 * @date 2023-04-16
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cfg_view_query_result")
public class ViewQueryResult extends BaseEntity {

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
     * 宽度
     */
    @TableField("width")
    private String width;

    /**
     * 是否支持排序
     */
    @TableField("sortable_flag")
    private String sortableFlag;

    /**
     * 格式化方法
     */
    @TableField("format_function")
    private String formatFunction;

    /**
     * 是否悬浮显示
     */
    @TableField("show_overflow_tooltip_flag")
    private String showOverflowTooltipFlag;

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
