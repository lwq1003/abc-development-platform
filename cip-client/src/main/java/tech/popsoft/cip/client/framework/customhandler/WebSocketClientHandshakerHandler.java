package tech.popsoft.cip.client.framework.customhandler;

import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.common.utils.SpringUtil;
import tech.popsoft.cip.client.framework.MessageClientConfig;
import tech.popsoft.cip.client.sender.request.system.LoginRequestSender;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 处理web socket握手
 *
 * @author wqliu
 * @date 2021-9-28
 **/
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class WebSocketClientHandshakerHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * 握手
     */
    private WebSocketClientHandshaker handshaker;
    /**
     * 握手 异步处理
     */
    private ChannelPromise handshakeFuture;

    public WebSocketClientHandshakerHandler() {
        // 初始化握手处理者
        MessageClientConfig config = SpringUtil.getBean(MessageClientConfig.class);
        URI webSocketUri = null;
        try {
            webSocketUri = new URI(config.getWebSocketUrl());
        } catch (URISyntaxException e) {
            log.error("解析远程服务器地址出错", e);
        }
        WebSocketClientHandshaker webSocketClientHandshaker = WebSocketClientHandshakerFactory.newHandshaker(webSocketUri,
                WebSocketVersion.V13, (String) null, true, new DefaultHttpHeaders());
        this.setHandshaker(webSocketClientHandshaker);


    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        Channel ch = ctx.channel();
        FullHttpResponse response;
        // 进行握手操作
        if (!this.handshaker.isHandshakeComplete()) {
            try {
                response = (FullHttpResponse) msg;
                // 握手协议返回，设置结束握手
                this.handshaker.finishHandshake(ch, response);
                // 设置成功
                this.handshakeFuture.setSuccess();

            } catch (WebSocketHandshakeException var7) {
                FullHttpResponse res = (FullHttpResponse) msg;
                String errorMsg = String.format("握手失败,status:%s,reason:%s", res.status(), res.content().toString(CharsetUtil.UTF_8));
                this.handshakeFuture.setFailure(new Exception(errorMsg));
            }
        } else if (msg instanceof FullHttpResponse) {
            // 已握手成功并将http协议升级为了WebSocket协议，不应再收到Http消息，发生这种情况则抛出异常
            response = (FullHttpResponse) msg;
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.status() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        } else if (msg instanceof CloseWebSocketFrame) {
            log.info("收到关闭信息");

        } else if (msg instanceof TextWebSocketFrame) {
            ReferenceCountUtil.retain(msg);
            ctx.fireChannelRead(msg);
        }

    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {


        this.handshakeFuture = ctx.newPromise();
        this.handshakeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    // 发送登录请求
                    log.info("握手成功");
                    LoginRequestSender login = new LoginRequestSender();
                    login.sendMessage(null);

                } else {
                    // 握手失败
                    log.error("握手失败", future.cause());
                }
            }
        });


    }

    /**
     * 发起握手
     */
    public void handshake(Channel channel) {
        this.getHandshaker().handshake(channel);
    }


}