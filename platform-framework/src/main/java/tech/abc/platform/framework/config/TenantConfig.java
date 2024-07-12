package tech.abc.platform.framework.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 租户配置
 *
 * @author wqliu
 * @date 2024-06-25
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "platform-config.tenant")
public class TenantConfig {

    /**
     * 进行租户处理的表集合
     */
    private List<String> tableName = new ArrayList<>();

}
