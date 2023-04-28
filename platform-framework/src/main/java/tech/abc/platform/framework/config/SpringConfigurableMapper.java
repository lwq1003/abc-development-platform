package tech.abc.platform.framework.config;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 处理深度拷贝配置
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Component
public class SpringConfigurableMapper extends ConfigurableMapper implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private MapperFactory mapperFactory;


    @Override
    protected void configure(MapperFactory factory) {
        super.configure(factory);
        this.mapperFactory = factory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        addSpringMappers();
        addSpringConverter();
    }


    private void addSpringMappers() {
        @SuppressWarnings("rawtypes") final Map<String, Mapper> mappers = applicationContext
                .getBeansOfType(Mapper.class);
        for (final Mapper<?, ?> mapper : mappers.values()) {
            addMapper(mapper);
        }
    }

    private void addMapper(Mapper<?, ?> mapper) {
        this.mapperFactory.registerMapper(mapper);
    }

    private void addSpringConverter() {
        @SuppressWarnings("rawtypes") final Map<String, Converter> converters = applicationContext
                .getBeansOfType(Converter.class);
        for (final Converter<?, ?> converter : converters.values()) {
            addConverter(converter);
        }
    }

    private void addConverter(Converter<?, ?> converter) {
        this.mapperFactory.getConverterFactory().registerConverter(converter);
    }


}