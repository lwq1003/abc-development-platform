package tech.abc.platform.entityconfig.codegenerator.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 实体配置 配置属性
 *
 * @author wqliu
 * @date 2023-04-05
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "platform-config.entityconfig")
public class EntityConfigProperties {

    /**
     * 系统编码
     */
    private String appCode="platform";

}
