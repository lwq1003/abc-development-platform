package tech.popsoft.cip.client.framework.customhandler;


import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import tech.abc.platform.cip.common.entity.BaseMessage;
import tech.abc.platform.cip.common.enums.MessageTypeEnum;
import tech.abc.platform.common.utils.SpringUtil;
import tech.popsoft.cip.client.framework.sender.MessageSenderFactory;
import tech.popsoft.cip.client.framework.sender.RequestMessageSender;
import tech.popsoft.cip.client.manage.entity.ApiMessageLog;
import tech.popsoft.cip.client.manage.service.ApiMessageLogService;
import tech.popsoft.cip.client.manage.service.ApiMessageTopicService;


/**
 * 去重处理器
 *
 * @author wqliu
 * @date 2022-1-19 13:50
 **/
public class DistinctMessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private ApiMessageLogService apiMessageLogService = SpringUtil.getBean(ApiMessageLogService.class);


    private ApiMessageTopicService apiMessageTopicService = SpringUtil.getBean(ApiMessageTopicService.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String content = textWebSocketFrame.text();
        BaseMessage message = JSON.parseObject(content, BaseMessage.class);
        String messageType = message.getMessageType();
        String messageId = message.getId();
        String topic = message.getTopic();
        boolean hasReceived = false;
        if (messageType.equals(MessageTypeEnum.REQUEST.name())) {
            hasReceived = apiMessageLogService.checkRequestMessageExist(messageId);
            if (hasReceived) {
                // 发送响应，终止流程
                String responseTopic = apiMessageTopicService.getResponseTopicCodeByCode(topic);
                RequestMessageSender sender = (RequestMessageSender) MessageSenderFactory.createSender(responseTopic);
                ApiMessageLog log = apiMessageLogService.getByRequestMessageId(messageId);
                sender.sendMessage(log.getResponseData(), log.getResponseId());


            } else {
                // 继续往下传递
                ReferenceCountUtil.retain(textWebSocketFrame);
                ctx.fireChannelRead(textWebSocketFrame);
            }

        } else if (messageType.equals(MessageTypeEnum.RESPONSE.name())) {
            hasReceived = apiMessageLogService.checkResponseMessageExist(messageId);
            if (hasReceived) {
                // 不做处理，终止流程
            } else {
                // 继续往下传递
                ReferenceCountUtil.retain(textWebSocketFrame);
                ctx.fireChannelRead(textWebSocketFrame);
            }
        }
    }


}
