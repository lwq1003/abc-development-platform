package tech.abc.platform.cip.message.framework.customerhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.message.framework.MessageServerHolder;


/**
 * 自定义的读超时处理器
 *
 * @author wqliu
 * @date 2022-2-10
 **/
@Slf4j
public class CustomReadTimeoutHandler extends ReadTimeoutHandler {
    public CustomReadTimeoutHandler(int timeoutSeconds) {
        super(timeoutSeconds);
    }

    @Override
    protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
        // 发生读空闲超时，说明客户端不再发送心跳，关闭该连接
        ctx.channel().close();
        log.info("服务端检测到客户端不再发送心跳，主动关闭连接");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("与客户端建立连接，通道开启！");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("与客户端连接失效，通道关闭！");
        MessageServerHolder.removeChannel(ctx.channel());

    }
}
