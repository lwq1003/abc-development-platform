package tech.abc.platform.entityconfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 实体 实体类
 *
 * @author wqliu
 * @date 2023-04-11
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cfg_entity")
public class Entity extends BaseEntity {

    /**
     * 模块
     */
    @TableField("module")
    private String module;

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
     * 作者
     */
    @TableField("author")
    private String author;

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
