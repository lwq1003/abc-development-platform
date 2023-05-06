package tech.popsoft.cip.client.framework;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.popsoft.cip.client.framework.customhandler.WebSocketClientHandshakerHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author wqliu
 * @date 2021-9-28 16:05
 **/
@Slf4j
@Component
public class MessageClient {


    @Autowired
    private MessageClientConfig config;

    @Autowired
    private MessageClientChannelInitializer messageClientChannelInitializer;

    @Autowired
    private ResendMessage resendMessage;


    /**
     * 启动客户端方法
     */
    public void start() {

        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(messageClientChannelInitializer);

            // 客户端与服务端连接的通道，final修饰表示只会有一个
            ChannelFuture channelFuture = bootstrap.connect(config.getHost(), config.getPort());

            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        // 未成功
                        log.error("连接失败", future.cause());
                        // 执行重连
                        reconnect();
                    } else {
                        log.info("连接成功");
                        Channel channel = future.channel();
                        // 将channel保存到全局变量
                        MessageClientGlobalHolder.channel = channel;
                        // 发起握手
                        WebSocketClientHandshakerHandler handler = (WebSocketClientHandshakerHandler) channel.pipeline().get("hookedHandler");
                        handler.handshake(MessageClientGlobalHolder.channel);
                    }
                }
            });

            // 消息重发
            if (config.isEnableResend()) {
                log.info("启动消息重发机制");
                // 延迟30秒启动，给建立连接预留充足的时间
                workerGroup.scheduleAtFixedRate(() -> {
                    resendMessage.resend();
                }, 30, config.getSendMessageSpan(), TimeUnit.SECONDS);
            }
            // 等待服务器端关闭
            channelFuture.channel().closeFuture().sync();


        } catch (Exception e) {
            log.error("消息客户端启动失败:{}" + e.getMessage(), e);
            // 执行重连
            reconnect();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 重连
     */
    public void reconnect() {
        try {
            // 重连期间将连接置空
            MessageClientGlobalHolder.channel = null;
            // 休眠5秒
            Thread.sleep(5000);
            // 执行重连
            log.info("消息客户端进行重连");
            start();

        } catch (InterruptedException e) {
            log.error("消息客户端重连过程中线程休眠失败", e);
        }

    }


}
