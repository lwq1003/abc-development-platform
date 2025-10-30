package tech.abc.platform.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复提交注解
 *
 * @author wqliu
 * @date 2025-05-19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRepeatSubmit {

    /**
     * 锁定秒数
     */
    long lockSeconds() default 5;

    /**
     * 键值
     */
    String key() default "";
}
