package tech.abc.platform.support.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 内容模板分类
 * @author wqliu
 * *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_content_template_category")

public class ContentTemplateCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父标识
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 排序号
     */
    @TableField("order_no")
    private String orderNo;

}
