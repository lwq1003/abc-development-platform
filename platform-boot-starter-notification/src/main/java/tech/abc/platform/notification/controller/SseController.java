package tech.abc.platform.notification.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import tech.abc.platform.common.annotation.AllowAll;
import tech.abc.platform.common.utils.JwtUtil;
import tech.abc.platform.common.utils.SpringUtil;
import tech.abc.platform.notification.server.global.SseEmitterManager;

/**
 * 系统消息sse控制器
 *
 * @author wqliu
 * @date 2025-10-28
 */
@RestController
@RequestMapping("/sse")
@Slf4j
public class SseController {

    @Autowired
    private TaskScheduler taskScheduler;

    /**
     * 客户端建立SSE连接（需携带身份信息）
     *
     * @param token 客户端携带的登录令牌（用于验证用户身份）
     * @return SseEmitter对象
     */
    @GetMapping("/connect")
    @AllowAll
    public SseEmitter connect(String token) {
        // 1. 验证token，解析出用户唯一标识（如userId）
        // 实际场景：调用认证服务解析token，获取userId（此处简化为模拟）

        JwtUtil jwtUtil = SpringUtil.getBean(JwtUtil.class);
        jwtUtil.verifyToken(token);
        // 获取用户账号
        String account = jwtUtil.decode(token).getSubject();


        if (account == null) {
            // 身份验证失败，立即关闭连接
            return new SseEmitter(0L);
        }

        // 2. 创建SSE连接（设置超时时间，如30分钟）
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);

        // 3. 将用户与连接关联，存入管理器
        SseEmitterManager.addEmitter(account, emitter);
        log.info("用户{}连接SSE成功", account);

        return emitter;
    }


}
