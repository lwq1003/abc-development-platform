package tech.abc.platform.system.constant;

import lombok.experimental.UtilityClass;

/**
 * 系统管理模块常量
 *
 * @author wqliu
 * @date 2023-03-06
 */
@UtilityClass
public class SystemConstant {
    /**
     * 未分配 部门标识
     */
    public static final String UNSIGNED_ORGANIZATION_ID = "-1";


    /**
     * 系统内置超级管理员admin账号用户标识
     */
    public static final String ADMIN_USER_ID = "1";

    // region 安全策略
    /**
     * 密码最小长度
     */
    public static final String PASSWORD_LENGTH = "PASSWORD_LENGTH";
    /**
     * 密码修改间隔天数
     */
    public static final String PASSWORD_UPDATE_INTERVAL = "PASSWORD_UPDATE_INTERVAL";
    /**
     * 密码在几次内不能重复
     */
    public static final String PASSWORD_UPDATE_SAME_TIMES = "PASSWORD_UPDATE_SAME_TIMES";
    /**
     * 连续输入几次密码错误就锁定帐号
     */
    public static final String PASSWORD_INPUT_ERROR_TIMES = "PASSWORD_INPUT_ERROR_TIMES";
    /**
     * 账号锁定自动解锁时间间隔（分）
     */
    public static final String ACCOUNT_UNLOCK_INTERVAL = "ACCOUNT_UNLOCK_INTERVAL";

    // endregion
}
