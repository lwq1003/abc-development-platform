package tech.abc.edoc.edoc.entity;

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
 * 文档收藏夹 实体类
 *
 * @author wqliu
 * @date 2024-02-04
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ed_document_favorite")
public class DocumentFavorite extends BaseEntity {

    /**
    * 对象类型
    */
    @TableField("object_type")
    private String objectType;

    /**
    * 对象标识
    */
    @TableField("object_id")
    private String objectId;

    /**
    * 用户标识
    */
    @TableField("user_id")
    private String userId;

    /********非库表存储属性*****/
    /**
     * 名称
     */
    @TableField(exist = false)
    private String name;

    /**
     * 类型
     */
    @TableField(exist = false)
    private String type;

    /**
     * 大小
     */
    @TableField(exist = false)
    private String size;
}
