package tech.abc.platform.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * SpringSecurity安全框架配置
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 登录处理地址
     */
    public static final String SYSTEM_USER_LOGIN = "/system/user/login";
    /**
     * 注销处理地址
     */
    public static final String SYSTEM_USER_LOGOUT = "/system/user/logout";
    /**
     * 会话超时地址
     */
    public static final String SYSTEM_USER_SESSION_INVALID = "/system/user/sessionInvalid";


    @Autowired
    private UserDetailsServiceImpl myUserService;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailHandler;

    @Autowired
    private MyLogoutHandler myLogoutHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private MyPermissionEvaluator myPermissionEvaluator;

    @Autowired
    private JwtFilter jwtFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置自定义用户服务及加密方式
        auth.userDetailsService(myUserService).passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 允许跨域访问
        http.cors();
        // 禁用csrf攻击防护
        http.csrf().disable();
        // 登录处理
        http.formLogin()
                // 此处的方法实际是虚拟的，并不需要在UserControl控制器中存在，与前端请求一致即可，会被SpringSecurity截获
                // 即使该方法存在，也会被SpringSecurity优先截获
                // 另外，前后端分离的情况下，不需要指定登录地址loginPage参数，指定了也不起作用
                .loginProcessingUrl(SYSTEM_USER_LOGIN)
                // 设置自定义的身份认证成功处理器
                .successHandler(myAuthenticationSuccessHandler)
                // 设置自定义的身份认证失败处理器
                .failureHandler(myAuthenticationFailHandler);

        // 注销处理
        http.logout()
                // 这里新加自定义处理处理器主要是生成用户注销审计日志，如放在logoutSuccessHandler则无法取到当前用户
                .addLogoutHandler(myLogoutHandler)
                .logoutUrl(SYSTEM_USER_LOGOUT)
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .invalidateHttpSession(true);


        // 会话管理
        http.sessionManagement()
                // 使用jwt token，不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        // 配置允许访问页面
        http.authorizeRequests()
                // 允许跨域请求中的Preflight请求
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()

                // 允许swagger文档接口匿名访问
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()


        ;

        // 配置其他请求,需认证
        http.authorizeRequests()
                .anyRequest()
                .authenticated();

        // 配置JWT过滤器
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


    }


    @Override
    public void configure(WebSecurity web) {
        web.expressionHandler(new DefaultWebSecurityExpressionHandler() {
            @Override
            protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, FilterInvocation fi) {
                WebSecurityExpressionRoot root = (WebSecurityExpressionRoot) super.createSecurityExpressionRoot(authentication, fi);
                root.setPermissionEvaluator(myPermissionEvaluator);
                return root;
            }
        });
    }
}
