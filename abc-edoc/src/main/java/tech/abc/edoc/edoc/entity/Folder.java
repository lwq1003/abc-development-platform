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
 * 文件夹 实体类
 *
 * @author wqliu
 * @date 2024-02-01
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ed_folder")
public class Folder extends BaseEntity {

    /**
    * 上级
    */
    @TableField("parent_id")
    private String parentId;

    /**
    * 名称
    */
    @TableField("name")
    private String name;



    /********非库表存储属性*****/
}
