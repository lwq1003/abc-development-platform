package tech.abc.platform.framework.aspect;

import com.alibaba.fastjson.JSON;
import tech.abc.platform.common.annotation.SystemLog;
import tech.abc.platform.common.constant.CommonConstant;
import tech.abc.platform.common.modules.system.api.LogApi;
import tech.abc.platform.common.modules.system.params.LogDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统日志切面类
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Aspect
// TODO: 开发阶段停用，避免控制台输出日志sql语句
// @Component
@Slf4j
public class SystemLogAspect {


    @Autowired
    private LogApi logApi;

    /**
     * 进行系统操作日志收集的入口方法
     *
     * @param joinPoint
     */
    @Around("@annotation(tech.abc.platform.common.annotation.SystemLog)")
    public Object collectSystemLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录请求开始时间
        LocalDateTime requestTime = LocalDateTime.now();

        Object result = null;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        }
        // 写日志的方法放到finally里，是为了保证在出错时也能记录操作
        finally {
            // 记录日志
            saveLog(joinPoint, requestTime, result);

        }
        return result;
    }


    /**
     * 保存日志
     *
     * @param joinPoint
     * @param requestTime
     */
    private void saveLog(ProceedingJoinPoint joinPoint, LocalDateTime requestTime, Object result) {

        // 获取签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取注解
        SystemLog logAnnotation = methodSignature.getMethod().getAnnotation(SystemLog.class);

        // 定义日志对象
        LogDTO log = new LogDTO();
        // 设置操作描述
        String value = logAnnotation.value();
        log.setContent(value);

        // 设置操作类型
        log.setLogType(logAnnotation.logType().name());

        // 设置请求参数  保存为json格式
        if (logAnnotation.logRequestParam()) {
            // 排除掉HttpServletRequest和HttpServletResponse类型的参数
            List<Object> args =
                    Arrays.stream(joinPoint.getArgs())
                            .filter(x -> !(x instanceof HttpServletResponse) && !(x instanceof HttpServletRequest))
                            .collect(Collectors.toList());

            log.setRequestParam(JSON.toJSONString(args));
        }

        // 设置请求发起的时间
        log.setRequestTime(requestTime);

        // 请求路径
        log.setRequestPath(joinPoint.getTarget().getClass().getName());
        // 请求方法
        log.setRequestMethod(methodSignature.getName());

        // 发生异常时执行结果会被全局异常捕获，result返回null
        // 此处默认设置为失败
        String executeResult = logAnnotation.executeResult();
        // 返回结果且为2xx才视为成功
        if (result instanceof ResponseEntity) {
            if (((ResponseEntity) result).getStatusCode().is2xxSuccessful()) {
                executeResult = CommonConstant.YES;
            }
        }
        log.setResponseCode(executeResult);


        // 响应数据  发生异常时，result对象为null
        if (result != null && logAnnotation.logResponseData()) {
            log.setResponseData(JSON.toJSONString(result));
        }


        // 结束时间
        LocalDateTime endTime = LocalDateTime.now();
        // 耗时
        log.setTimeConsuming((int) Duration.between(requestTime, endTime).toMillis());


        // 保存日志信息
        logApi.add(log);
    }

}
