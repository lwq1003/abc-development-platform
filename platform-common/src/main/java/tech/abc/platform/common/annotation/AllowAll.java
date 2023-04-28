package tech.abc.platform.common.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 允许任意访问
 * 已确认，方法上不配置权限表达式，起到的效果一样
 * 但仍建议加上，以明确该方法允许访问。而不是忘记做权限控制
 *
 * @author wqliu
 * @date 2023-03-06
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@PreAuthorize("permitAll()")
public @interface AllowAll {


}
