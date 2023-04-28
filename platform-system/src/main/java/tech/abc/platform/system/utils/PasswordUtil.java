package tech.abc.platform.system.utils;

import lombok.experimental.UtilityClass;

/**
 * 密码工具类
 *
 * @author wqliu
 * @date 2020-05-05 9:24
 */

@UtilityClass
public class PasswordUtil {


    /**
     * 密码复杂度验证
     * 验证包括大写字母、小写字母、数字、特殊符号这4种类型中的3种
     *
     * @param password
     * @return true 是复杂密码
     */
    public boolean isComplexPassword(String password) {
        String regexRule = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)" +
                "(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,}$";
        return password.matches(regexRule);
    }
}
