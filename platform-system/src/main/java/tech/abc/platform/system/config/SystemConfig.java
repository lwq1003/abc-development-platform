package tech.abc.platform.system.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 系统配置
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "platform-config.system")
public class SystemConfig {

    /**
     * 用户初始化密码
     */
    private String userInitPassword = "654321";


}
