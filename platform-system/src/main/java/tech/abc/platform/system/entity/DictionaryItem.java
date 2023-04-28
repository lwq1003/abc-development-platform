package tech.abc.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典项 实体类
 *
 * @author wqliu
 * @date 2023-04-26
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dictionary_item")
public class DictionaryItem extends BaseEntity {

    /**
     * 字典类型
     */
    @TableField("dictionary_type")
    private String dictionaryType;

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
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

}
