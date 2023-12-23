package tech.abc.platform.framework.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import tech.abc.platform.common.constant.SystemConstant;
import tech.abc.platform.common.enums.LogTypeEnum;
import tech.abc.platform.common.modules.system.api.LogApi;
import tech.abc.platform.common.modules.system.params.LogDTO;
import tech.abc.platform.common.utils.ResultUtil;
import tech.abc.platform.common.vo.Result;
import tech.abc.platform.system.entity.User;
import tech.abc.platform.common.enums.ExecuteResultEnum;
import tech.abc.platform.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 自定义登录失败处理器
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Component
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserService userService;


    @Autowired
    private LogApi logApi;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {


        String errMessage = "未知错误";
        // 根据异常类型，生成友好提示信息
        if (exception instanceof BadCredentialsException) {
            errMessage = "用户名或密码错误";
        } else if (exception instanceof DisabledException) {
            errMessage = "用户已停用，请联系系统管理员";
        } else if (exception instanceof LockedException) {
            errMessage = "用户已锁定，请联系系统管理员";
        }


        // 获取用户名
        String account = request.getParameter(SystemConstant.LOGIN_USER_KEY).toLowerCase();

        // 查询用户信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getAccount, account);
        User user = userService.getOne(userQueryWrapper);

        // 记录日志
        LogDTO log = new LogDTO();
        log.setContent(account + " 登录失败：" + errMessage);
        log.setLogType(LogTypeEnum.AUDIT.name());
        log.setResponseCode(ExecuteResultEnum.FAILURE.name());
        log.setRequestTime(LocalDateTime.now());
        logApi.add(log);

        // 密码输入有误
        if (user != null) {
            // 登陆失败，累加尝试次数
            userService.updateLoginFailureCount(user.getId());
            // 判断是否达到上限
            if (userService.checkTryCount(user.getId())) {
                // 达到上限，锁定用户
                userService.lock(user.getId());
            }
        }

        ResponseEntity<Result> result = ResultUtil.error(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        ResultUtil.returnJsonToFront(response, result);

    }


}
