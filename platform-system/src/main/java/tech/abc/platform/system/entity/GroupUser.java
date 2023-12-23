package tech.abc.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户组与用户对应表
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_group_user")
public class GroupUser extends BaseEntity {


    /**
     * 组标识
     */
    @TableField("group_id")
    private String groupId;

    /**
     * 用户标识
     */
    @TableField("user_id")
    private String userId;

}
