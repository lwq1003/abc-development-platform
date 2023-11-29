package tech.abc.platform.oss.config;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.abc.platform.common.exception.CommonException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.oss.service.ObjectStoreService;


/**
 * 对象存储服务配置
 *
 * @author wqliu
 * @date 2023-05-20
 */
@Configuration
@Slf4j
public class OssServiceConfig {

    @Autowired
    private OssConfig ossConfig;


    @Bean
    public ObjectStoreService instance() {

        String className = ossConfig.getStoreClass();
        try {
            return (ObjectStoreService) ClassUtils.getClass(className).newInstance();
        } catch (Exception e) {
            log.error("加载对象存储服务类出错", e);
            throw new CustomException(CommonException.CLASS_NOT_FOUND);
        }

    }

    @Bean
    @ConditionalOnClass(MinioClient.class)
    public MinioClient minioClient() {

        MinioConfig config = ossConfig.getMinioConfig();
        // 创建客户端
        MinioClient minioClient =
                MinioClient.builder()
                        // 服务地址
                        .endpoint(config.getServer())
                        // 身份认证
                        .credentials(config.getAccessKey(), config.getSecretKey())
                        .build();
        return minioClient;

    }
}
