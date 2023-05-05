package tech.abc.platform.cip.api.framework;


import tech.abc.platform.cip.common.entity.ApiRequest;
import tech.abc.platform.cip.common.entity.ApiResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * API服务过滤器链条
 *
 * @author wqliu
 * @date 2022-2-12
 **/
public class ApiFilterChain {

    /**
     * 请求
     */
    private ApiRequest request;

    /**
     * 响应
     */
    private ApiResponse response = new ApiResponse();


    /**
     * 过滤器集合
     */
    private final List<ApiFilter> filters;

    /**
     * 过滤器迭代器
     */
    private Iterator<ApiFilter> iterator;

    public ApiFilterChain() {
        filters = Collections.EMPTY_LIST;
    }

    public ApiFilterChain(ApiFilter... filters) {
        this.filters = Arrays.asList(filters);

    }


    /**
     * 获取请求
     *
     * @return {@link ApiRequest}
     */
    public ApiRequest getRequest() {
        return this.request;
    }

    /**
     * 获取响应
     *
     * @return {@link ApiResponse}
     */
    public ApiResponse getResponse() {
        return this.response;
    }


    /**
     * 执行过滤
     *
     * @param request  请求
     * @param response 响应
     */
    public void doFilter(ApiRequest request, ApiResponse response) {
        // 如迭代器为空，则初始化
        if (this.iterator == null) {
            this.iterator = this.filters.iterator();
        }

        // 集合中还有过滤器，则继续往下传递
        if (this.iterator.hasNext()) {
            ApiFilter nextFilter = this.iterator.next();
            nextFilter.doFilter(request, response, this);
        }

        // 将处理结果更新到属性中
        this.request = request;
        this.response = response;
    }


    /**
     * 重置
     */
    public void reset() {
        this.request = null;
        this.response = null;
        this.iterator = null;
    }

}
