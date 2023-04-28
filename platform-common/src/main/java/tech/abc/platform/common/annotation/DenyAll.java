package tech.abc.platform.common.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 禁止任何访问
 * 仅是预留，目前未有合适的场景使用，可能用于部分极其重要的控制器，仅限于内部使用
 * 但内部使用往往是服务层调用，而不是控制器层
 *
 * @author wqliu
 * @date 2023-03-06
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@PreAuthorize("denyAll()")
public @interface DenyAll {


}
