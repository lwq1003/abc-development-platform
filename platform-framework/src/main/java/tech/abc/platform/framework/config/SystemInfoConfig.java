package tech.abc.platform.framework.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * 系统信息
 *
 * @author wqliu
 * @date 2023-03-08
 **/
@Data
@Configuration
public class SystemInfoConfig {

    /**
     * 系统编码
     */
    private String softCode = "edoc";
    /**
     * 版本号
     */
    private String softVersion = "1.0.0";
}
