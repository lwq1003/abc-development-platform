package tech.abc.platform.framework.security;

import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义注销处理
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Component
@Slf4j
public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {


        ResponseEntity<Result> result = ResultUtil.success(null, "注销成功");
        ResultUtil.returnJsonToFront(response, result);
    }


}
