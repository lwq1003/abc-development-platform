package tech.abc.platform.cip.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import tech.abc.platform.cip.config.properties.message.MessageProperties;

/**
 * 应用配置
 *
 * @author wqliu
 * @date 2021-1-7 10:56
 */

@Data
@ConfigurationProperties(prefix = "platform-config")
public class AppConfig {
    /**
     * 消息配置
     */
    private MessageProperties message = new MessageProperties();


}
