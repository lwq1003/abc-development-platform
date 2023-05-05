package tech.abc.platform.cip.api.service;


import tech.abc.platform.cip.common.entity.ApiRequest;
import tech.abc.platform.cip.common.entity.ApiResponse;

/**
 * API服务技术框架
 *
 * @author wqliu
 * @date 2022-2-10
 **/
public interface ApiRestService {


    /**
     * 处理
     *
     * @param apiRequest api请求
     * @return {@link ApiResponse} api响应
     */
    ApiResponse handle(ApiRequest apiRequest);
}
