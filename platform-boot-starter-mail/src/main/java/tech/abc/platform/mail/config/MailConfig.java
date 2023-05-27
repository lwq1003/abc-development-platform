package tech.abc.platform.mail.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 对象存储配置文件
 *
 * @author wqliu
 * @date 2020-04-05 11:19
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "platform-config.mail")
public class MailConfig {

    /**
     * 发送方地址
     */
    private String senderAddress = "";

}
