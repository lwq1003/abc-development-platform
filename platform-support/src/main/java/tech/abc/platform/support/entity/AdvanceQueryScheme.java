package tech.abc.platform.support.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 高级查询方案
 * @author wqliu
 * *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_advance_query_scheme")

public class AdvanceQueryScheme extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 用户标识
     */
    @TableField("user_id")
    private String userId;

    /**
     * 实体编码
     */
    @TableField("entity_code")
    private String entityCode;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 排序号
     */
    @TableField("order_no")
    private String orderNo;

}
