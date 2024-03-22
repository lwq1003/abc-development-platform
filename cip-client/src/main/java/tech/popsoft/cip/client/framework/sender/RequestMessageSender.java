package tech.popsoft.cip.client.framework.sender;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.enums.MessageStatusEnum;
import tech.popsoft.cip.client.framework.MessageClientGlobalHolder;
import tech.popsoft.cip.client.manage.entity.ApiMessageLog;


/**
 * 请求消息发送器
 *
 * @author wqliu
 * @date 2021-10-5
 */
@Slf4j
public class RequestMessageSender extends MessageSender {


    public RequestMessageSender(String topic) {
        super(topic);

    }

    /**
     * 发送消息
     *
     * @param content 消息内容
     * @param id      消息标识
     */
    public void sendMessage(String content, String id) {
        // 生成请求消息
        RequestMessage message = new RequestMessage();
        // 使用已有ID重置默认生成的ID，用于关联消息
        if (StringUtils.isNotBlank(id)) {
            message.setId(id);
        }
        // 设置相关属性
        message.setTopic(super.getTopic());
        // 参数中消息内容优先，如为空，取对象属性的值
        if (StringUtils.isNotBlank(content)) {
            message.setContent(content);
        } else {
            message.setContent(this.getContent());
        }

        message.setPublishAppCode(messageClientConfig.getAppCode());


        // 获取发送通道
        Channel channel = MessageClientGlobalHolder.channel;
        if (channel != null && channel.isActive()) {
            ChannelFuture channelFuture = channel.writeAndFlush(message);
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {

                    if (StringUtils.isBlank(id)) {
                        // 创建日志
                        ApiMessageLog log = apiMessageLogService.createRequestPart(message);
                        // 设置状态为已请求
                        apiMessageLogService.updateStatus(MessageStatusEnum.REQUESTED.name(), log.getRequestId());
                        // 发送次数加1
                        apiMessageLogService.incrementSendCount(log.getRequestId());
                    } else {
                        // 更新发送次数
                        apiMessageLogService.incrementSendCount(id);
                    }
                }
            });

        } else {
            // 创建日志
            apiMessageLogService.createRequestPart(message);
        }

    }

    /**
     * 发送请求消息
     *
     * @param content 消息内容
     */
    public void sendMessage(String content) {
        // 发送消息
        sendMessage(content, null);
    }


    /**
     * 发送请求消息
     *
     * @param content 消息内容
     */
    public void sendMessage() {
        // 发送消息
        sendMessage(null, null);
    }


}
