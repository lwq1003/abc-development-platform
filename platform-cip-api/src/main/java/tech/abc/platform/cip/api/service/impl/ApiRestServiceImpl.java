package tech.abc.platform.cip.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.abc.platform.cip.api.exception.ApiException;
import tech.abc.platform.cip.api.framework.ApiFilterChain;
import tech.abc.platform.cip.api.framework.BasicValidateFilter;
import tech.abc.platform.cip.api.framework.BusinessFilter;
import tech.abc.platform.cip.api.framework.FrameworkValidateFilter;
import tech.abc.platform.cip.api.service.ApiRestService;
import tech.abc.platform.cip.common.entity.ApiRequest;
import tech.abc.platform.cip.common.entity.ApiResponse;
import tech.abc.platform.cip.enums.ApiServiceExecuteResultEnum;
import tech.abc.platform.cip.service.ApiServiceLogService;
import tech.abc.platform.common.exception.CustomException;

import java.time.LocalDateTime;

/**
 * '
 * API服务技术框架实现
 *
 * @author wqliu
 * @date 2022-2-10
 **/
@Service
public class ApiRestServiceImpl implements ApiRestService {
    private ApiFilterChain filterChain;

    @Autowired
    private ApiServiceLogService apiServiceLogService;

    public ApiRestServiceImpl(FrameworkValidateFilter frameworkValidateFilter,
                              BusinessFilter businessFilter, BasicValidateFilter basicValidateFilter) {
        filterChain = new ApiFilterChain(basicValidateFilter, frameworkValidateFilter, businessFilter);

    }

    @Override
    public ApiResponse handle(ApiRequest apiRequest) {

        LocalDateTime receiveTime = LocalDateTime.now();
        ApiResponse apiResponse = new ApiResponse();
        try {
            filterChain.doFilter(apiRequest, apiResponse);
            apiResponse = this.filterChain.getResponse();
            apiResponse.setExecuteResult(ApiServiceExecuteResultEnum.SUCCESS.name());
        } catch (CustomException ex) {
            // 自定义异常处理
            apiResponse.setExecuteResult(ApiServiceExecuteResultEnum.ERROR.name());
            apiResponse.setErrorCode("S00");
            apiResponse.setErrorMessage(ex.getMessage());
        } catch (ApiException ex) {
            // API异常处理
            apiResponse.setExecuteResult(ApiServiceExecuteResultEnum.ERROR.name());
            apiResponse.setErrorCode(ex.getErrorCode());
            apiResponse.setErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            // 非预期异常处理
            apiResponse.setExecuteResult(ApiServiceExecuteResultEnum.ERROR.name());
            apiResponse.setErrorCode("S99");
            apiResponse.setErrorMessage("未定义异常：" + ex.getMessage());
        } finally {
            // 需要重置，为下次请求服务
            filterChain.reset();
            // 记录日志
            apiServiceLogService.recordLog(apiRequest, apiResponse, receiveTime);

        }

        return apiResponse;
    }
}
