package tech.abc.platform.framework.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tech.abc.platform.common.annotation.NoRepeatSubmit;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.CacheUtil;
import tech.abc.platform.common.utils.JwtUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 防重复提交切面
 *
 * @author wqliu
 * @date 2025-05-19
 */
@Aspect
@Slf4j
@Component
public class NoRepeatAspect {

    @Resource
    private CacheUtil cacheUtil;

    @Resource
    private JwtUtil jwtUtil;

    @Around("@annotation(noRepeatSubmit)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String key = buildKey(request, noRepeatSubmit);

        if (cacheUtil.hasKey(key)) {
            throw new CustomException(CommonException.REPEAT_SUBMIT);
        }
        // 不存在键值则添加
        cacheUtil.set(key, "", noRepeatSubmit.lockSeconds(), TimeUnit.SECONDS);

        return pjp.proceed();
    }

    private String buildKey(HttpServletRequest request, NoRepeatSubmit annotation) {

        String token = jwtUtil.getToken(request);
        return String.format("submit:lock:%s:%s",
                annotation.key(),
                token + ":" + request.getRequestURI());
    }
}
