package tech.abc.platform.entityconfig.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 实体模型属性 实体类
 *
 * @author wqliu
 * @date 2023-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cfg_entity_model_property")
public class EntityModelProperty extends BaseEntity {

    /**
     * 实体模型
     */
    @TableField("entity_model")
    private String entityModel;

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
     * 是否可为空
     */
    @TableField("null_flag")
    private String nullFlag;

    /**
     * 最大长度
     */
    @TableField(value = "max_length", updateStrategy = FieldStrategy.IGNORED)
    private Integer maxLength;

    /**
     * 小数位数
     */
    @TableField("decimal_length")
    private Integer decimalLength;

    /**
     * 默认值
     */
    @TableField("default_value")
    private String defaultValue;

    /**
     * 是否唯一
     */
    @TableField("unique_flag")
    private String uniqueFlag;

    /**
     * 唯一性参照
     */
    @TableField("entity_model_property")
    private String entityModelProperty;

    /**
     * 是否主属性
     */
    @TableField("main_flag")
    private String mainFlag;

    /**
     * 是否上级属性
     */
    @TableField("parent_property_flag")
    private String parentPropertyFlag;

    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}
