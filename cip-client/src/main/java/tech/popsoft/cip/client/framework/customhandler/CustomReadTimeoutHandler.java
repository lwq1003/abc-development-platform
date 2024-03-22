package tech.popsoft.cip.client.framework.customhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.common.utils.SpringUtil;
import tech.popsoft.cip.client.framework.MessageClient;
import tech.popsoft.cip.client.framework.MessageClientGlobalHolder;


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
        log.info("读空闲");
        // 关闭连接
        ctx.channel().close();
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 置空连接通道
        MessageClientGlobalHolder.channel = null;
        log.info("客户端检测通道失效，启动重连");
        MessageClient messageClient = SpringUtil.getBean(MessageClient.class);
        messageClient.reconnect();

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("与服务端建立连接，通道开启！");
        // 必须调用父类方法，避免其他处理器的channelActive事件不再触发
        super.channelActive(ctx);

    }
}
