package tech.abc.platform.system.entity;

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
 * 用户配置 实体类
 *
 * @author wqliu
 * @date 2023-06-14
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user_profile")
public class UserProfile extends BaseEntity {

    /**
    * 用户
    */
    @TableField("user")
    private String user;
    /**
    * 语种
    */
    @TableField("language")
    private String language;
    /**
    * 时区
    */
    @TableField("time_zone")
    private String timeZone;
    /**
    * 桌面配置
    */
    @TableField("desktop_config")
    private String desktopConfig;
    /********非库表存储属性*****/
}
