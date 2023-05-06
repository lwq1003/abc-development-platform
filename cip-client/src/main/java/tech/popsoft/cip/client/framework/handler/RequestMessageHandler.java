package tech.popsoft.cip.client.framework.handler;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.popsoft.cip.client.framework.sender.MessageSenderFactory;
import tech.popsoft.cip.client.framework.sender.ResponseMessageSender;

/**
 * 请求消息处理器
 *
 * @author wqliu
 * @date 2022-1-8 10:42
 **/
@Slf4j
public class RequestMessageHandler extends MessageHandler {
    /**
     * 消息处理
     *
     * @param message 消息
     * @param channel 通道
     */
    public void handleMessage(RequestMessage requestMessage, Channel channel) {

        // 记录消息请求日志
        apiMessageLogService.createRequestPart(requestMessage);


        // 消息主题验证（是否存在及是否可用）
        validateTopic(requestMessage.getTopic());

        // 特殊处理
        messageOperation(requestMessage, channel);

        // 发送响应至消息发送者
        sendResponse(requestMessage, channel);

    }


    private void sendResponse(RequestMessage requestMessage, Channel channel) {
        // 获取响应消息的消息主题
        String responseTopicCode = getResponseTopicCode(requestMessage.getTopic());
        // 根据消息主题构建发送器
        ResponseMessageSender responseMessageSender = (ResponseMessageSender) MessageSenderFactory.createSender(responseTopicCode);
        // 发送消息
        responseMessageSender.sendMessage(channel, requestMessage);
    }


    /**
     * 获取响应消息主题
     *
     * @return
     */
    protected String getResponseTopicCode(String topic) {
        // 默认从消息主题实体类中获取
        return apiMessageTopicService.getResponseTopicCodeByCode(topic);
    }


    /**
     * 请求消息处理
     *
     * @param message
     * @param channel
     */
    protected void messageOperation(RequestMessage message, Channel channel) {

    }


}
