package tech.abc.platform.notification.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.abc.platform.notification.config.NotificationConfig;

/**
 * netty实现的服务端
 *
 * @author wqliu
 * @date 2021-2-5
 **/
@Component
@Slf4j
public class InsideMessageServer {


    @Autowired
    private NotificationConfig notificationConfig;

    @Autowired
    private InsideMessageChannelInitializer myChannelInitializer;

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
            serverBootstrap.childHandler(myChannelInitializer);
            // 绑定端口,开始接收进来的连接
            int port = notificationConfig.getServerPort();
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            log.info("系统通知服务启动 [port:{}]", port);
            // 等待服务器socket关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("系统通知服务启动异常-" + e.getMessage(), e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
