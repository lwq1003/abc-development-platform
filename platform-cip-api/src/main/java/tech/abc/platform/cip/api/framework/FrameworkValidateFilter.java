package tech.abc.platform.cip.api.framework;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.abc.platform.cip.api.exception.ApiException;
import tech.abc.platform.cip.common.entity.ApiRequest;
import tech.abc.platform.cip.common.entity.ApiResponse;
import tech.abc.platform.cip.entity.ApiService;
import tech.abc.platform.cip.entity.App;
import tech.abc.platform.cip.service.ApiServicePermissionService;
import tech.abc.platform.cip.service.ApiServiceService;
import tech.abc.platform.cip.service.AppService;
import tech.abc.platform.common.constant.DateConstant;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.exception.CustomException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * 框架验证过滤器
 *
 * @author wqliu
 * @date 2022-2-12
 **/
@Slf4j
@Component
public class FrameworkValidateFilter implements ApiFilter {

    @Autowired
    private ApiServiceService apiServiceService;

    @Autowired
    private AppService apiAppService;

    @Autowired
    private ApiServicePermissionService apiServicePermissionService;

    /**
     * 有效时间(单位：秒）
     */
    private static final int VALID_TIME_SPAN = 10 * 60;


    @Override
    public void doFilter(ApiRequest request, ApiResponse response, ApiFilterChain chain) {
        // 验证数据
        validateData(request);
        // 继续往下传递
        chain.doFilter(request, response);
    }

    // region 数据验证

    /**
     * 验证数据
     *
     * @param apiRequest api请求
     */
    private void validateData(ApiRequest apiRequest) {

        // 验证应用
        validateApp(apiRequest.getAppCode());
        // 验证服务
        validateService(apiRequest.getServiceCode());
        // 验证权限
        validatePermission(apiRequest.getAppCode(), apiRequest.getServiceCode());
        // 验证时效
        validateTimeLimitation(apiRequest.getRequestTime());
        // 验证签名
        validateSign(apiRequest);

    }


    /**
     * 验证应用
     *
     * @param apiRequest api请求
     */
    private void validateApp(String appCode) {

        App app = null;
        try {
            app = apiAppService.getByCode(appCode);
        } catch (CustomException exception) {
            throw new ApiException("S10", "应用代码不存在");
        }

        if (app.getStatus().equals(StatusEnum.DEAD.name())) {
            throw new ApiException("S11", "应用已停用");
        }


    }

    /**
     * 验证服务
     *
     * @param apiRequest api请求
     */
    private void validateService(String serviceCode) {

        ApiService apiService = null;
        try {
            apiService = apiServiceService.getByCode(serviceCode);
        } catch (CustomException exception) {
            throw new ApiException("S20", "服务不存在");
        }

        if (apiService.getStatus().equals(StatusEnum.DEAD.name())) {
            throw new ApiException("S21", "服务已停用");
        }
    }

    /**
     * 验证权限
     *
     * @param apiRequest api请求
     */
    private void validatePermission(String appCode, String serviceCode) {

        if (!apiServicePermissionService.hasPermission(appCode, serviceCode)) {
            throw new ApiException("S30", "无权限");
        }
    }

    /**
     * 验证时效
     *
     * @param apiRequest api请求
     */
    private void validateTimeLimitation(String requestTimeString) {

        // 转换时间格式
        LocalDateTime requestTime = LocalDateTime.parse(requestTimeString, DateTimeFormatter.ofPattern(DateConstant.DATE_FORMAT_FULL));
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 获取时间差值
        Duration duration = Duration.between(requestTime, now);
        if (Math.abs(duration.toMillis()) > VALID_TIME_SPAN * 1000) {
            throw new ApiException("S40", "请求时间超出合理范围");
        }

    }

    /**
     * 验证签名
     *
     * @param apiRequest api请求
     */
    private void validateSign(ApiRequest apiRequest) {
        // 参数进入map，并自动排序
        TreeMap<String, String> requestDataMap = new TreeMap<>();
        requestDataMap.put("appCode", apiRequest.getAppCode());
        requestDataMap.put("data", apiRequest.getData());
        requestDataMap.put("requestTime", apiRequest.getRequestTime());
        requestDataMap.put("serviceCode", apiRequest.getServiceCode());
        requestDataMap.put("signMethod", apiRequest.getSignMethod());
        // 获取密钥
        String appSecret = apiAppService.getByCode(apiRequest.getAppCode()).getSecretKey();

        // 拼装参数与密钥
        StringBuffer sb = new StringBuffer();
        sb.append(appSecret);
        Iterator<String> iterator = requestDataMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            sb.append(key).append("=").append(requestDataMap.get(key));
        }
        sb.append(appSecret);
        String param = sb.toString();
        // 进行签名
        String sign = DigestUtils.md5Hex(param);
        log.info("签名:{}", sign);
        // 签名比对
        if (!sign.equals(apiRequest.getSign())) {
            throw new ApiException("S50", "签名验证失败");
        }
    }
    // endregion
}
