package tech.abc.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户配置
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user_profile")

public class UserProfile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户标识
     */
    @TableField("user_id")
    private String userId;

    /**
     * 桌面配置
     */
    @TableField("desktop_config")
    private String desktopConfig;

    /**
     * 时区
     */
    @TableField("time_zone")
    private String timeZone;

    /**
     * 语种
     */
    @TableField("language")
    private String language;

}
