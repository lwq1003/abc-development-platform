package tech.abc.platform.cip.message.framework.customerhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.message.framework.handler.MessageHandlerFactory;
import tech.abc.platform.cip.message.framework.handler.RequestMessageHandler;
import tech.abc.platform.cip.message.sender.response.system.ErrorResponseSender;
import tech.abc.platform.cip.service.MessageLogService;
import tech.abc.platform.common.utils.SpringUtil;


/**
 * 服务端请求消息业务逻辑处理
 *
 * @author wqliu
 * @date 2021-2-5
 **/
@Slf4j
public class RequestMessageBusinessHandler extends SimpleChannelInboundHandler<RequestMessage> {

    private MessageLogService apiMessageLogService = SpringUtil.getBean(MessageLogService.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage message) throws Exception {
        String requestMessageId = StringUtils.EMPTY;
        String topic = StringUtils.EMPTY;
        try {
            // 获取消息主题
            topic = message.getTopic();
            // 获取消息标识
            requestMessageId = message.getId();
            // 转具体的消息处理器进行处理
            RequestMessageHandler handler = (RequestMessageHandler) MessageHandlerFactory.createHandler(topic);
            handler.handleMessage(message, ctx.channel());
        } catch (Exception e) {
            // 记录消息请求日志
            apiMessageLogService.createRequestPart(message);
            String errorMessage = e.getMessage();
            // 统一响应错误
            ErrorResponseSender errorResponseSender = new ErrorResponseSender();
            errorResponseSender.setErrorMessage(e.getMessage());
            errorResponseSender.sendMessage(ctx.channel(), message);
            log.error("发生异常：", e);
        }
    }


}
