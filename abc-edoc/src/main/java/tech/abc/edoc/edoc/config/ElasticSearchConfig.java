package tech.abc.edoc.edoc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * ElasticSearch 配置
 * @author wqliu
 * @date 2024-02-21
 */
@Configuration
public class ElasticSearchConfig {

    @Bean
    ElasticsearchConverter elasticsearchConverter(SimpleElasticsearchMappingContext mappingContext) {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        defaultConversionService.addConverter(LongToLocalDateTimeConverter.INSTANCE);
        return new MappingElasticsearchConverter(mappingContext, defaultConversionService);
    }

    @ReadingConverter
    static enum LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {
        /**
         * 单例模式
         */
        INSTANCE;

        private LongToLocalDateTimeConverter() {
        }

        @Override
        public LocalDateTime convert(Long source) {
            return Instant.ofEpochMilli(source).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
    }
}
