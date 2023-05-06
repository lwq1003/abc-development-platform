package tech.popsoft.cip.client.framework;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tech.abc.platform.cip.common.handler.JsonEncodeHandler;
import tech.abc.platform.cip.common.handler.MessageTypeDecodeHandler;
import tech.abc.platform.cip.common.handler.TextWebSocketFrameEncodeHandler;
import tech.abc.platform.cip.common.handler.ValidateMessageHandler;
import tech.popsoft.cip.client.framework.customhandler.*;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * 初始化通道
 *
 * @author wqliu
 * @date 2021-2-5 15:12
 */
@Slf4j
@Component
public class MessageClientChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Autowired
    private MessageClientConfig config;

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

        // 仅在生产模式下加载ssl过滤器
        String mode = environment.getProperty("spring.profiles.active");
        if (PRD_MODE.equals(mode)) {
            // ssl
            SSLContext sslContext = createSslContext();
            SSLEngine engine = sslContext.createSSLEngine();
            engine.setNeedClientAuth(false);
            engine.setUseClientMode(false);
            // 客户端第一个消息，即发起握手的消息不要加密
            pipeline.addLast(new SslHandler(engine, true));
        }


        // HTTP 编解码
        pipeline.addLast(new HttpClientCodec());

        // 聚合为单个 FullHttpRequest 或者 FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));


        // 读超时处理
        pipeline.addLast(new CustomReadTimeoutHandler(config.getReadIdleTimeOut()));


        // 处理web socket协议与握手
        pipeline.addLast("hookedHandler", new WebSocketClientHandshakerHandler());


        // 数据基本验证
        pipeline.addLast(new ValidateMessageHandler());

        // 去重
        pipeline.addLast(new DistinctMessageHandler());


        // 将文本按消息类型转换为请求消息或响应消息
        pipeline.addLast(new MessageTypeDecodeHandler());

        // 请求消息业务逻辑处理器
        pipeline.addLast(new RequestMessageBusinessHandler());


        // 响应消息业务逻辑处理器
        pipeline.addLast(new ResponseMessageBusinessHandler());

        // 编码为TextWebSocketFrame
        pipeline.addLast(new TextWebSocketFrameEncodeHandler());

        // json序列化
        pipeline.addLast(new JsonEncodeHandler());


    }

    /**
     * 创建ssl上下文对象
     *
     * @return
     * @throws Exception
     */
    public SSLContext createSslContext() throws Exception {

        // 读取配置信息
        String path = environment.getProperty("server.ssl.key-store");
        String password = environment.getProperty("server.ssl.key-store-password");
        String type = environment.getProperty("server.ssl.key-store-type");

        // 构建证书上下文对象
        KeyStore ks = KeyStore.getInstance(type);
        path = path.replace("classpath:", "");
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
