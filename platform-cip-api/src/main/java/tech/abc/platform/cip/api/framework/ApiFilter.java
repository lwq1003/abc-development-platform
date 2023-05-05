package tech.abc.platform.cip.api.framework;


import tech.abc.platform.cip.common.entity.ApiRequest;
import tech.abc.platform.cip.common.entity.ApiResponse;

/**
 * API过滤器
 *
 * @author wqliu
 * @date 2022-2-12
 **/
public interface ApiFilter {

    /**
     * 初始化
     */
    default void init() {
    }

    /**
     * 执行过滤
     *
     * @param request  请求
     * @param response 应答
     * @param chain    链条
     */
    void doFilter(ApiRequest request, ApiResponse response, ApiFilterChain chain);

    /**
     * 销毁
     */
    default void destroy() {
    }
}
