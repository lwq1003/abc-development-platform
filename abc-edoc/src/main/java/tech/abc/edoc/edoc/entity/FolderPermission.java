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
 * 文件夹权限 实体类
 *
 * @author wqliu
 * @date 2024-02-04
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ed_folder_permission")
public class FolderPermission extends BaseEntity {

    /**
    * 文件夹标识
    */
    @TableField("folder_id")
    private String folderId;

    /**
    * 授权对象类型
    */
    @TableField("object_type")
    private String objectType;

    /**
    * 授权对象标识
    */
    @TableField("object_id")
    private String objectId;

    /**
    * 权限编码
    */
    @TableField("permission_code")
    private String permissionCode;

    /********非库表存储属性*****/
}
