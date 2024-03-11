package tech.abc.platform.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseMapEntity;


/**
 * 用户组与权限项对应表
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_group_permission_item")
public class GroupPermissionItem extends BaseMapEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组标识
     */
    @TableField("group_id")
    private String groupId;

    /**
     * 权限项标识
     */
    @TableField("permission_item_id")
    private String permissionItemId;

}
