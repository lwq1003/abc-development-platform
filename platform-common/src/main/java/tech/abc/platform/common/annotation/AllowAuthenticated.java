package tech.abc.platform.common.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 允许已登录用户访问
 *
 * @author wqliu
 * @date 2023-03-06
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@PreAuthorize("isAuthenticated()")
public @interface AllowAuthenticated {


}
