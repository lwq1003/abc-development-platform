package tech.abc.platform.framework.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 通过切面技术实现控制台输出异常
 * 该功能用于开发调试阶段，可以便捷看到前端输入参数、执行结果以及返回给前端的响应数据
 * 增加@Aspect 注解启用
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Component
@Slf4j
public class HttpLogAspect {


    @Pointcut("execution(public * tech.abc..controller.*.*(..))")
    public void log() {
        // 切面处理

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // url
        log.info("url={}", request.getRequestURL());
        // method
        log.info("http method={}", request.getMethod());
        // class_method
        log.info("class method={}", joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        // args[]
        log.info("args={}", joinPoint.getArgs());
    }


    @AfterReturning(pointcut = "log()", returning = "object")
    public void doAfterReturning(Object object) {
        // 打印输出结果
        log.info("response={}", JSON.toJSONString(object));
    }
}
