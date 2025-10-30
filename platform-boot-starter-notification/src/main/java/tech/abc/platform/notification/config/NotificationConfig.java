package tech.abc.platform.notification.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 通知配置文件
 *
 * @author wqliu
 * @date 2023-05-20
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "platform-config.notification")
public class NotificationConfig {

    /**
     * 服务端口
     */
    private Integer serverPort = 9997;

    /**
     * 通知类型
     * SSE: Server-Sent Events
     * WebSocket: WebSocket
     */
    private String notificationType = "SSE";
}
