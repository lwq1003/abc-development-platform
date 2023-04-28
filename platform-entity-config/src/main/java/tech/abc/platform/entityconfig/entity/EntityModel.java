package tech.abc.platform.entityconfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 实体模型 实体类
 *
 * @author wqliu
 * @date 2023-04-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cfg_entity_model")
public class EntityModel extends BaseEntity {

    /**
     * 父级模型
     */
    @TableField("parent_model")
    private String parentModel;

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
     * 是否主模型
     */
    @TableField("main_model_flag")
    private String mainModelFlag;

    /**
     * 是否自关联
     */
    @TableField("self_reference_flag")
    private String selfReferenceFlag;

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

    /**
     * 实体
     */
    @TableField("entity")
    private String entity;

}
