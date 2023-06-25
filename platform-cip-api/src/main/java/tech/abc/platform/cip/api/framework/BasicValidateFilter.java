package tech.abc.platform.cip.api.framework;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.stereotype.Component;
import tech.abc.platform.cip.api.exception.ApiException;
import tech.abc.platform.cip.common.entity.ApiRequest;
import tech.abc.platform.cip.common.entity.ApiResponse;
import tech.abc.platform.cip.common.tool.ValidateUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 基本数据验证
 *
 * @author wqliu
 * @date 2022-2-13
 **/
@Slf4j
@Component
public class BasicValidateFilter implements ApiFilter {
    @Override
    public void doFilter(ApiRequest request, ApiResponse response, ApiFilterChain chain) {
        log.info("进入基本数据验证");
        validate(request);
        validateRequestTimeFormat(request.getRequestTime());
        chain.doFilter(request, response);
    }


    private void validate(ApiRequest apiRequest) {
        Validator validator = Validation.byProvider(HibernateValidator.class).configure()
                .failFast(true).buildValidatorFactory().getValidator();

        Set<ConstraintViolation<ApiRequest>> set = validator.validate(apiRequest);
        if (set.size() > 0) {
            throw new ApiException("S00", set.iterator().next().getMessage());
        }
    }

    /**
     * 验证请求时间格式
     *
     * @param requestTime 请求时间
     */
    private void validateRequestTimeFormat(String requestTime) {

        if (!ValidateUtil.dateIsFormat(requestTime)) {
            throw new ApiException("S01", "请求时间格式不符合要求");
        }
    }

}
