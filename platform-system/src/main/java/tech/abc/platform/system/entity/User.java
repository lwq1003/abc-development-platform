package tech.abc.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户 实体类
 *
 * @author wqliu
 * @date 2023-04-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
public class User extends BaseEntity {

    /**
     * 组织机构
     */
    @TableField("organization")
    private String organization;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 出生日期
     */
    @TableField("birthday")
    private LocalDateTime birthday;

    /**
     * 手机号
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 职位
     */
    @TableField("position")
    private String position;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 强制修改密码
     */
    @TableField("force_change_password_flag")
    private String forceChangePasswordFlag;

    /**
     * 失败登录次数
     */
    @TableField("fail_login_count")
    private Integer failLoginCount;

    /**
     * 锁定时间
     */
    @TableField("lock_time")
    private LocalDateTime lockTime;

    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

}
