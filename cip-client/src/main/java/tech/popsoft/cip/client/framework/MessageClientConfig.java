package tech.popsoft.cip.client.framework;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.text.MessageFormat;


/**
 * 客户端配置参数
 * 使用spring单例模式
 *
 * @author wqliu
 * @date 2021-10-5 14:10
 */
@Data
@ConfigurationProperties(prefix = "params")
public class MessageClientConfig {


    /**
     * 应用编码
     */
    private String appCode;
    /**
     * 应用密钥
     */
    private String appSecret;
    /**
     * 消息中心主机
     */
    private String host;
    /**
     * 消息中心端口
     */
    private int port = 8997;

    /**
     * 心跳频率，单位秒
     */
    private int heartbeatRate = 5;


    /**
     * 最大发送次数
     */
    private int maxSendCount = 4;

    /**
     * 定时发送消息间隔时间，单位秒
     */
    private int sendMessageSpan = 30;

    /**
     * 每次定时发送消息的数量
     */
    private int sendMessageCount = 10;


    /**
     * 是否启用消息重发
     */
    private boolean enableResend;

    /**
     * web socket的路径
     */
    private String webSocketPath;


    /**
     * 触发读空闲事件的时间，单位秒
     */
    private int readIdleTimeOut;

    /**
     * 获取web socket的url地址
     */
    public String getWebSocketUrl() {
        String url = MessageFormat.format("ws://{0}:{1}/{2}", this.getHost(), String.valueOf(this.getPort()),
                this.getWebSocketPath());
        return url;

    }


}
