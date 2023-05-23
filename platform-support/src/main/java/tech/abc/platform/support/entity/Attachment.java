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
 * 附件 实体类
 *
 * @author wqliu
 * @date 2023-05-20
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_attachment")
public class Attachment extends BaseEntity {

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 大小
     */
    @TableField("size")
    private String size;

    /**
     * 长度
     */
    @TableField("length")
    private Long length;

    /**
     * 路径
     */
    @TableField("path")
    private String path;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 实际名称
     */
    @TableField("real_name")
    private String realName;

    /**
     * 实体
     */
    @TableField("entity")
    private String entity;

    /**
     * 备注
     */
    @TableField("ramark")
    private String ramark;

    /********非库表存储属性*****/
}
