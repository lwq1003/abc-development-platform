package tech.abc.platform.cip.message.framework.customerhandler;


import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import tech.abc.platform.cip.common.entity.BaseMessage;
import tech.abc.platform.cip.common.enums.MessageTypeEnum;
import tech.abc.platform.cip.entity.MessageLog;
import tech.abc.platform.cip.message.framework.sender.MessageSenderFactory;
import tech.abc.platform.cip.message.framework.sender.RequestMessageSender;
import tech.abc.platform.cip.service.MessageLogService;
import tech.abc.platform.cip.service.MessageTopicService;
import tech.abc.platform.common.utils.SpringUtil;


/**
 * 去重处理器
 *
 * @author wqliu
 * @date 2022-1-19
 **/
public class DistinctMessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private MessageLogService apiMessageLogService = SpringUtil.getBean(MessageLogService.class);


    private MessageTopicService apiMessageTopicService = SpringUtil.getBean(MessageTopicService.class);


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
                MessageLog log = apiMessageLogService.getByRequestMessageId(messageId);
                sender.sendMessage(log.getRequestAppCode(), log.getResponseData(), log.getResponseId());


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
