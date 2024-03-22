package tech.abc.platform.cip.message.framework.handler;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.enums.MessageStatusEnum;
import tech.abc.platform.cip.message.framework.MessageServerHolder;
import tech.abc.platform.cip.message.framework.sender.MessageSenderFactory;
import tech.abc.platform.cip.message.framework.sender.RequestMessageSender;
import tech.abc.platform.cip.message.framework.sender.ResponseMessageSender;


/**
 * 请求消息处理器
 *
 * @author wqliu
 * @date 2022-1-8
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
        messageLogService.createRequestPart(requestMessage);

        // 验证框架
        String appCode = MessageServerHolder.getAppCode(channel);
        validateFramework(requestMessage, appCode);

        // 将请求消息状态默认设置为无需发送
        messageLogService.updateStatus(MessageStatusEnum.NOT_TO_REQUEST.name(), requestMessage.getId());


        // 特殊处理
        messageOperation(requestMessage, channel);

        // 发送响应至消息发送者
        sendResponse(requestMessage, channel);


        // 消息处理（复制及转发）
        if (isNeedRepost()) {
            repostMessage(requestMessage.getTopic(), requestMessage.getContent());
        }

    }

    /**
     * 是否需要转发消息
     *
     * @return boolean
     */
    protected boolean isNeedRepost() {
        return true;
    }

    /**
     * 消息转发
     *
     * @param topic
     * @param content
     */
    private void repostMessage(String topic, String content) {
        // 根据消息主题构建发送器
        RequestMessageSender requestMessageSender = (RequestMessageSender) MessageSenderFactory.createSender(topic);
        // 传入原请求的消息标识和消息内容
        requestMessageSender.sendMessage(content);
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
        return messageTopicService.getResponseTopicCodeByCode(topic);
    }


    /**
     * 请求消息处理
     * 侧重于业务处理
     *
     * @param message
     * @param channel
     */
    protected void messageOperation(RequestMessage message, Channel channel) {

    }


}
