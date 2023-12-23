package tech.abc.platform.common.annotation;


import tech.abc.platform.common.enums.ExecuteResultEnum;
import tech.abc.platform.common.enums.LogTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统操作日志切面注解
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SystemLog {

    /**
     * 日志内容
     */
    String value();

    /**
     * 日志类型
     */
    LogTypeEnum logType() default LogTypeEnum.OPERATION;

    /**
     * 是否记录请求参数
     */
    boolean logRequestParam() default false;

    /**
     * 是否记录响应数据
     */
    boolean logResponseData() default false;

    /**
     * 执行是否成功
     */
    ExecuteResultEnum executeResult() default ExecuteResultEnum.FAILURE;
}
