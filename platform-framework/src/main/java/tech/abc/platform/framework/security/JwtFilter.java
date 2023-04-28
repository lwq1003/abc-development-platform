package tech.abc.platform.framework.security;

import tech.abc.platform.common.utils.JwtUtil;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.framework.config.PlatformConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基于jwt令牌的身份认证过滤器
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Slf4j
@Component
public class JwtFilter extends GenericFilterBean {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PlatformConfig platformConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) servletRequest;


        // 优先从http头中获取令牌
        String token = req.getHeader("X-Token");
        // 其次从cookie中获取
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    if ("token".equals(cookies[i].getName())) {
                        token = cookies[i].getValue();
                        break;
                    }
                }
            }
        }
        // 再次，从url地址中获取
        if (StringUtils.isBlank(token)) {
            token = req.getParameter("X-Token");
        }


        if (StringUtils.isNotBlank(token)) {
            // 验证令牌
            try {
                jwtUtil.verifyToken(token);
            } catch (Exception ex) {
                ResponseEntity<Result> result = ResultUtil.error(ex.getMessage(), HttpStatus.UNAUTHORIZED);
                ResultUtil.returnJsonToFront((HttpServletResponse) servletResponse, result);
                return;
            }


            // 获取账号
            String account = jwtUtil.decode(token).getSubject();
            // 查询用户信息
            UserDetails user = userDetailsService.loadUserByUsername(account);
            // 构造SpringSecurity的认证对象后放到SecurityContextHolder中
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);


        }
        // 执行后续过滤器
        filterChain.doFilter(servletRequest, servletResponse);
    }
}