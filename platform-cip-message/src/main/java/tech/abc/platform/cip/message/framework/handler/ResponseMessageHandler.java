package tech.abc.platform.cip.message.framework.handler;

import io.netty.channel.Channel;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.message.framework.MessageServerHolder;

/**
 * 响应消息处理器
 *
 * @author wqliu
 * @date 2022-1-8 11:07
 **/
public class ResponseMessageHandler extends MessageHandler {

    /**
     * 消息处理
     *
     * @param message 消息
     * @param channel 通道
     */
    public void handleMessage(ResponseMessage responseMessage, Channel channel) {


        // 验证框架
        String appCode = MessageServerHolder.getAppCode(channel);
        validateFramework(responseMessage, appCode);


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
