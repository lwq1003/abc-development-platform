package tech.popsoft.cip.client.framework.customhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.common.utils.SpringUtil;
import tech.popsoft.cip.client.framework.handler.MessageHandlerFactory;
import tech.popsoft.cip.client.framework.handler.RequestMessageHandler;
import tech.popsoft.cip.client.manage.service.ApiMessageLogService;
import tech.popsoft.cip.client.sender.response.system.ErrorResponseSender;


/**
 * 客户端请求消息业务逻辑处理
 *
 * @author wqliu
 * @date 2021-2-5 16:25
 **/
@Slf4j
public class RequestMessageBusinessHandler extends SimpleChannelInboundHandler<RequestMessage> {

    private ApiMessageLogService apiMessageLogService = SpringUtil.getBean(ApiMessageLogService.class);

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
