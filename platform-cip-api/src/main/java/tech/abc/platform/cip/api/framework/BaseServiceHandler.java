package tech.abc.platform.cip.api.framework;

import com.alibaba.fastjson.JSON;
import org.hibernate.validator.HibernateValidator;
import tech.abc.platform.cip.api.exception.ApiException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

/**
 * 服务处理接口
 *
 * @author wqliu
 * @date 2021-8-20
 **/
public abstract class BaseServiceHandler<T> {

    /**
     * 主处理流程
     *
     * @param data
     * @return
     */
    public String handle(String data, String appCode) {
        // 获取泛型的类
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // 解析数据
        T parameter = JSON.parseObject(data, tClass);
        // 验证数据
        validateData(parameter);
        // 业务处理
        return handleBusiness(parameter, appCode);

    }

    /**
     * 业务处理
     *
     * @param parameter 入参
     * @param appCode   应用编码
     * @return 处理后的结果
     */
    protected abstract String handleBusiness(T parameter, String appCode);

    private void validateData(T parameter) {
        Validator validator = Validation.byProvider(HibernateValidator.class).configure()
                .failFast(true).buildValidatorFactory().getValidator();

        Set<ConstraintViolation<T>> set = validator.validate(parameter);
        if (set.size() > 0) {
            throw new ApiException("B01", set.iterator().next().getMessage());
        }
    }
}
