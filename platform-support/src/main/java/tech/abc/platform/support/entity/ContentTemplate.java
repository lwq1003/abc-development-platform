package tech.abc.platform.support.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 内容模板
 * @author wqliu
 * *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_content_template")

public class ContentTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 模板分类标识
     */
    @TableField("category_id")
    private String categoryId;

    /**
     * 排序号
     */
    @TableField("order_no")
    private String orderNo;

}
