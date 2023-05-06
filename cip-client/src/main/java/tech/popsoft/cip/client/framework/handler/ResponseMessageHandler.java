package tech.popsoft.cip.client.framework.handler;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.common.entity.ResponseMessage;

/**
 * 响应消息处理器
 *
 * @author wqliu
 * @date 2022-1-8 11:07
 **/
@Slf4j
public class ResponseMessageHandler extends MessageHandler {

    /**
     * 消息处理
     *
     * @param message 消息
     * @param channel 通道
     */
    public void handleMessage(ResponseMessage responseMessage, Channel channel) {


        // 消息主题验证（是否存在及是否可用）
        validateTopic(responseMessage.getTopic());

        // 更新消息日志
        apiMessageLogService.updateResponsePart(responseMessage);

        // 特殊处理
        messageOperation(responseMessage, channel);
    }


    /**
     * 响应消息处理
     *
     * @param message
     * @param channel
     */
    protected void messageOperation(ResponseMessage message, Channel channel) {

    }


}
