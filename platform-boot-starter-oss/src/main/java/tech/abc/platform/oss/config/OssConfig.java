package tech.abc.platform.oss.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 对象存储配置文件
 *
 * @author wqliu
 * @date 2023-05-20
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "platform-config.oss")
public class OssConfig {

    /**
     * 对象存储类名
     */
    private String storeClass = "";

    /**
     * 存储根路径
     */
    private String basePath = "";


    /**
     * minio配置
     */
    private MinioConfig minioConfig=new MinioConfig();

}
