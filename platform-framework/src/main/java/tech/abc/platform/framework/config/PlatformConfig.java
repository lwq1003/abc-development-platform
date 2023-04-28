package tech.abc.platform.framework.config;

import tech.abc.platform.framework.config.properties.system.SystemProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 应用配置
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
@ConfigurationProperties(prefix = "platform-config")
public class PlatformConfig {

    /**
     * 系统
     */
    private SystemProperties system = new SystemProperties();


}
