package tech.abc.platform.system.exception;

import lombok.Getter;
import tech.abc.platform.common.exception.ExceptionInterface;


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
    PWD_CHANGE_NOT_STRONG("密码强度不够，请包含大写字母、小写字母、数字、特殊符号这4种类型中的3种,且至少8位"),
    /**
     * 新密码不能包含账号、电话号码或出生日期三者中任何一项
     */
    PWD_CHANGE_EASY("新密码不能包含账号、电话号码或出生日期三者中任何一项"),

    /**
     * 未找到组织机构
     */
    ORGANIZATION_NAME_NOT_FOUND("未找到组织机构名称"),
    /**
     * 未找到组织机构编码
     */
    ORGANIZATION_CODE_NOT_FOUND("未找到组织机构编码"),

    /**
     * 编码和名称不能同时为空
     */
    ORGANIZATION_NAME_AND_CODE_CANOT_NULL("组织机构名称和组织机构编码不能同时为空"),

    /**
     * 账号与邮箱不匹配
     */
    ACCOUNT_EMAIL_NOT_MATCH("账号与邮箱不匹配，请确认"),

    /**
     * 该邮箱未注册
     */
    EMAIl_NOT_REGISTER("该邮箱未注册，请确认"),

    /**
     * 授权码已失效
     */
    AUTHORIZATION_CODE_EXPIRED("授权码已失效，请重新获取"),
    /**
     * 账号不存在
     */
    ACCOUNT_NOT_EXIST("账号不存在"),


    /**
     * 邮箱已存在
     */
    EMAIL_EXIST("邮箱已存在"),

    ;
    private String message;

    UserExceptionEnum(String message) {
        this.message = message;
    }

}
