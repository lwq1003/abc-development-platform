package tech.abc.platform.cip.api.framework;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.abc.platform.cip.api.exception.ApiException;
import tech.abc.platform.cip.common.entity.ApiRequest;
import tech.abc.platform.cip.common.entity.ApiResponse;
import tech.abc.platform.cip.service.ApiServiceService;


/**
 * 业务处理过滤器
 *
 * @author wqliu
 * @date 2022-2-11
 **/
@Slf4j
@Component
public class BusinessFilter implements ApiFilter {
    @Autowired
    private ApiServiceService apiServiceService;


    @Override
    public void doFilter(ApiRequest request, ApiResponse response, ApiFilterChain chain) {

        BaseServiceHandler handler = getHandler(request.getServiceCode());
        String responseData = handler.handle(request.getData(), request.getAppCode());
        response.setData(responseData);
        chain.doFilter(request, response);
    }

    /**
     * 根据服务代码进行服务分发
     *
     * @param serviceCode 服务代码
     * @return
     */
    private BaseServiceHandler getHandler(String serviceCode) {

        String handlerClass = apiServiceService.getHandlerByCode(serviceCode);
        try {
            BaseServiceHandler handler = (BaseServiceHandler) Class.forName(handlerClass).newInstance();
            return handler;
        } catch (Exception e) {
            throw new ApiException("S60", "未找到该服务对应的处理器");
        }

    }

}

