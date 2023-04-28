package tech.abc.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户组 实体类
 *
 * @author wqliu
 * @date 2023-04-24
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user_group")
public class UserGroup extends BaseEntity {

    /**
     * 上级
     */
    @TableField("user_group")
    private String userGroup;

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
     * 状态
     */
    @TableField("status")
    private String status;

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

}
