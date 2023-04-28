package tech.abc.platform.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * web工具类
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Slf4j
public class WebUtils {

    public static final String UNKNOWN = "unknown";
    public static final String COMMA = ",";

    /**
     * 获取当前请求者IP地址
     *
     * @return
     */
    public static String getRemoteAddr() {

        String ip = "";
        if (RequestContextHolder.getRequestAttributes() != null) {
            ServletRequestAttributes requestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {

                HttpServletRequest request = requestAttributes.getRequest();


                ip = request.getHeader("x-forwarded-for");

                if (StringUtils.isNotBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                    if (ip.indexOf(COMMA) != -1) {
                        ip = ip.substring(0, ip.indexOf(","));
                    }

                }


                if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {

                    ip = request.getHeader("X-Real-IP");
                }
                if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {

                    ip = request.getRemoteAddr();
                }

            }
        }

        return ip;
    }
}
