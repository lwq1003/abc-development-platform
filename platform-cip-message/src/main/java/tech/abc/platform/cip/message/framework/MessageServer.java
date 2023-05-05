package tech.abc.platform.cip.message.framework;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.abc.platform.cip.config.AppConfig;

import java.util.concurrent.TimeUnit;

/**
 * netty实现的服务端
 *
 * @author wqliu
 * @date 2021-2-5
 **/
@Component
@Slf4j
public class MessageServer {


    @Autowired
    private AppConfig appConfig;

    @Autowired
    private MessageServerChannelInitializer messageServerChannelInitializer;

    @Autowired
    private ResendMessage resendMessage;


    /**
     * 启动服务器方法
     */
    public void run() {

        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(messageServerChannelInitializer);
            // 绑定端口,开始接收进来的连接
            int port = appConfig.getMessage().getServerPort();

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            log.info("netty服务启动 [port:{}]", port);

            // 消息重发
            if (appConfig.getMessage().isEnableResend()) {
                log.info("启动消息重发机制");
                // 延迟30秒启动，给系统初始化接预留充足的时间
                workerGroup.scheduleAtFixedRate(() -> {
                    resendMessage.resend();
                }, 30, appConfig.getMessage().getSendMessageSpan(), TimeUnit.SECONDS);
            }

            // 等待服务器socket关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty服务启动异常-" + e.getMessage(), e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
