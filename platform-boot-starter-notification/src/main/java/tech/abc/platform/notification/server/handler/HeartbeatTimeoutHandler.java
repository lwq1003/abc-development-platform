package tech.abc.platform.notification.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.notification.server.global.ClientHolder;

/**
 * 心跳超时处理器
 *
 * @author wqliu
 * @date 2021-2-6
 **/
@Slf4j
public class HeartbeatTimeoutHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            // 发生读写空闲超时，说明客户端不再发送心跳，关闭该连接
            ctx.channel().close();
            ClientHolder.removeChannel(ctx.channel());
            log.info("服务端检测到客户端不再发送心跳，主动关闭连接");
        } else {
            super.userEventTriggered(ctx, evt);
        }

    }
}
