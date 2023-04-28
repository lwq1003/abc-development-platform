package tech.abc.platform.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * 加密工具类
 *
 * @author wqliu
 * @date 2023-03-06
 */
@UtilityClass
public class EncryptUtil {

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    /**
     * 加密，如参数为空，返回空字符串
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String bCryptPasswordEncode(String rawPassword) {
        if (StringUtils.isBlank(rawPassword)) {
            return StringUtils.EMPTY;
        }
        return bCryptPasswordEncoder.encode(rawPassword);
    }


    /**
     * 验证密码匹配
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 验证通过返回true，否则false
     */
    public static boolean bCryptPasswordMatches(CharSequence rawPassword, String encodedPassword) {
        if (StringUtils.isBlank(encodedPassword)) {
            return false;
        }
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }


}
