package tech.abc.platform.framework.security;

import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.constant.CommonConstant;
import tech.abc.platform.common.enums.LogTypeEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义注销处理器
 * 当前作用是生成注销审计日志
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Component
public class MyLogoutHandler implements LogoutHandler {

    @Override
    @SystemLog(value = "注销成功", logType = LogTypeEnum.AUDIT, logRequestParam = false, executeResult = CommonConstant.YES)
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

    }
}
