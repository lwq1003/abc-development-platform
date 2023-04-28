package tech.abc.platform.system.exception;

import tech.abc.platform.common.exception.ExceptionInterface;
import lombok.Getter;


/**
 * 用户相关异常
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Getter
public enum UserExceptionEnum implements ExceptionInterface {

    /**
     * 没有找到此用户
     */
    NOT_EXIST("没有找到此用户"),
    /**
     * 账号已存在
     */
    ACCOUNT_EXIST("账号已存在"),


    /**
     * 原密码不正确
     */
    PWD_CHANGE_NOT_CORRECT("原密码不正确"),
    /**
     * 密码长度不够
     */
    PWD_CHANGE_NEED_LENGTH("密码长度不够"),
    /**
     * 新密码不能与原密码相同
     */
    PWD_OLD_NEW_SAME("新密码不能与原密码相同"),
    /**
     * 新密码与历史使用密码相同
     */
    PWD_CHANGE_CYCLE_PASS("新密码与历史使用密码相同"),
    /**
     * 密码强度不够，请包含大写字母、小写字母、数字、特殊符号这4种类型中的3种
     */
    PWD_CHANGE_NOT_STRONG("密码强度不够，请包含大写字母、小写字母、数字、特殊符号这4种类型中的3种"),
    /**
     * 新密码不能包含账号、电话号码或出生日期三者中任何一项
     */
    PWD_CHANGE_EASY("新密码不能包含账号、电话号码或出生日期三者中任何一项"),

    ;
    private String message;

    UserExceptionEnum(String message) {
        this.message = message;
    }

}
