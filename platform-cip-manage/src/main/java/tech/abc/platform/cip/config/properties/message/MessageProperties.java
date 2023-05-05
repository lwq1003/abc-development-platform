package tech.abc.platform.cip.config.properties.message;

import lombok.Data;

/**
 * 消息配置
 *
 * @author wqliu
 */
@Data
public class MessageProperties {

    /**
     * 服务端监听端口号
     */
    private int serverPort = 8997;

    /**
     * 消息服务端应用编码
     */
    private String messageServerAppCode = "CIP";


    /**
     * 触发读空闲事件的时间，单位秒
     */
    private int readIdleTimeOut = 60;


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

}
