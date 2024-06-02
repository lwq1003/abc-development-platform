package tech.abc.platform.system.vo;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 注册用户 视图对象类
 *
 * @author wqliu
 * @date 2023-05-24
 */
@Data
@Accessors(chain = true)
public class RegisterUserVO {

    /**
     * 姓名
     */
    @NotBlank(message = "【姓名】不能为空")
    private String name;

    /**
     * 账号
     */
    @NotBlank(message = "【账号】不能为空")
    private String account;

    /**
     * 密码
     */
    private String password;


    /**
     * 邮箱
     */
    @NotBlank(message = "【邮箱】不能为空")
    private String email;


}


