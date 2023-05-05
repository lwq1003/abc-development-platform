package tech.abc.platform.cip.message.framework.customerhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.message.framework.handler.MessageHandlerFactory;
import tech.abc.platform.cip.message.framework.handler.ResponseMessageHandler;
import tech.abc.platform.cip.service.MessageLogService;
import tech.abc.platform.common.utils.SpringUtil;


/**
 * 服务端响应消息业务逻辑处理
 *
 * @author wqliu
 * @date 2021-2-5
 **/
@Slf4j
public class ResponseMessageBusinessHandler extends SimpleChannelInboundHandler<ResponseMessage> {

    private MessageLogService apiMessageLogService = SpringUtil.getBean(MessageLogService.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMessage message) throws Exception {
        String requestMessageId = StringUtils.EMPTY;
        String topic = StringUtils.EMPTY;
        try {
            // 获取消息主题
            topic = message.getTopic();
            // 获取消息标识
            requestMessageId = message.getId();
            // 转具体的消息处理器进行处理
            ResponseMessageHandler handler = (ResponseMessageHandler) MessageHandlerFactory.createHandler(topic);
            handler.handleMessage(message, ctx.channel());
        } catch (Exception e) {
            // 更新消息日志
            apiMessageLogService.updateResponsePart(message);
            // 对于错误响应，只记录日志，不再向发送方反馈响应
            log.error("收到客户端的响应消息有误：", e);
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("发生异常", cause);

    }
}
