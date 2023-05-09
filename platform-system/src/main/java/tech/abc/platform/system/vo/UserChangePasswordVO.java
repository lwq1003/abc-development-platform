package tech.abc.platform.system.vo;

import lombok.Data;

/**
 * 用户修改密码视图
 *
 * @author wqliu
 * @date 2023-05-09
 */
@Data
public class UserChangePasswordVO {


    /**
     * 用户标识
     */
    private String userId;
    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;
}
