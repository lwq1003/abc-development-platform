package tech.abc.platform.notification.server;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tech.abc.platform.notification.server.constant.NettyConstant;
import tech.abc.platform.notification.server.handler.HeartbeatTimeoutHandler;
import tech.abc.platform.notification.server.handler.WebSocketServerHandler;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

/**
 * 初始化通道
 *
 * @author wqliu
 * @date 2021-2-5
 */
@Slf4j
@Component
public class InsideMessageChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private Environment environment;

    /**
     * 生产运行模式
     */
    private final String PRD_MODE = "prd";

    /**
     * 初始化channel
     */
    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception {


        // 获取通道链路
        ChannelPipeline pipeline = socketChannel.pipeline();

        /**
         * 仅在生产模式下加载ssl过滤器
         */
        String mode = environment.getProperty("spring.profiles.active");
        if (PRD_MODE.equals(mode)) {
            // ssl
            SSLContext sslContext = createSslContext();
            SSLEngine engine = sslContext.createSSLEngine();
            engine.setNeedClientAuth(false);
            engine.setUseClientMode(false);
            pipeline.addLast(new SslHandler(engine));
        }


        // HTTP 编解码
        pipeline.addLast(new HttpServerCodec());

        // 将一个 HttpMessage 和跟随它的多个 HttpContent 聚合
        // 为单个 FullHttpRequest 或者 FullHttpResponse（取
        // 决于它是被用来处理请求还是响应）。安装了这个之后，
        // ChannelPipeline 中的下一个 ChannelHandler 将只会
        // 收到完整的 HTTP 请求或响应
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));


        // 处理web socket协议与握手
        pipeline.addLast(new WebSocketServerProtocolHandler("/webSocket"));

        // 添加读写通道空闲处理器，当超时时，会触发userEventTrigger，由下个处理器获取到
        pipeline.addLast(new IdleStateHandler(NettyConstant.READ_IDLE_TIME_OUT, NettyConstant.WRITE_IDLE_TIME_OUT,
                NettyConstant.ALL_IDLE_TIME_OUT, TimeUnit.SECONDS));

        // 心跳机制处理
        pipeline.addLast(new HeartbeatTimeoutHandler());

        // 业务处理Handler
        pipeline.addLast(new WebSocketServerHandler());

    }


    /**
     * 创建ssl上下文对象
     *
     * @return
     * @throws Exception
     */
    private SSLContext createSslContext() throws Exception {

        // 读取配置信息
        String path = environment.getProperty("server.ssl.key-store");
        log.info("证书地址:{}", path);
        String password = environment.getProperty("server.ssl.key-store-password");
        String type = environment.getProperty("server.ssl.key-store-type");

        // 构建证书上下文对象
        KeyStore ks = KeyStore.getInstance(type);
        path = path.replace("classpath:", "");
        log.info("处理后的证书地址:{}", path);
        ClassPathResource resource = new ClassPathResource(path);
        InputStream ksInputStream = resource.getInputStream();
        ks.load(ksInputStream, password.toCharArray());
        // KeyManagerFactory充当基于密钥内容源的密钥管理器的工厂。
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());
        // SSLContext的实例表示安全套接字协议的实现，它充当用于安全套接字工厂或 SSLEngine 的工厂。
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);
        return sslContext;
    }
}
