package tech.abc.platform.support.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 内容模板 实体类
 *
 * @author wqliu
 * @date 2023-05-31
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_content_template")
public class ContentTemplate extends BaseEntity {

    /**
    * 分类
    */
    @TableField("category")
    private String category;
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
    * 排序
    */
    @TableField("order_no")
    private String orderNo;
    /**
    * 备注
    */
    @TableField("remark")
    private String remark;
    /********非库表存储属性*****/
}
