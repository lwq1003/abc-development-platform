package tech.popsoft.cip.client.handler.response.system;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.common.enums.MessageResponseResultEnum;
import tech.abc.platform.common.utils.SpringUtil;
import tech.popsoft.cip.client.framework.MessageClientConfig;
import tech.popsoft.cip.client.framework.MessageClientGlobalHolder;
import tech.popsoft.cip.client.framework.handler.ResponseMessageHandler;

import java.util.concurrent.TimeUnit;

/**
 * 登录响应处理器
 *
 * @author wqliu
 * @date 2022-1-16
 **/
@Slf4j
public class LoginResponseHandler extends ResponseMessageHandler {
    @Override
    protected void messageOperation(ResponseMessage message, Channel channel) {
        log.info("收到登录响应");

        if (message.getResult().equals(MessageResponseResultEnum.SUCCESS.name())) {
            // 登录成功，将连接保存到全局变量
            MessageClientGlobalHolder.channel = channel;
            // 启动心跳
            startHeartbeat(channel);

        } else {
            // 登录失败，关闭通道，重新连接
            log.error("登录失败：{}", message.getErrorMessage());
            channel.close();
        }
    }

    private void startHeartbeat(Channel channel) {
        // 获取心跳发送频率
        MessageClientConfig config = SpringUtil.getBean(MessageClientConfig.class);
        int heartbeatRate = config.getHeartbeatRate();
        // 启动心跳
        EventLoop eventLoop = channel.eventLoop();
        eventLoop.scheduleWithFixedDelay(new Runnable() {
            private Channel channel;


            @Override
            public void run() {
                // log.info("发送心跳");
                PingWebSocketFrame frame = new PingWebSocketFrame();
                ChannelFuture channelFuture = channel.writeAndFlush(frame);

            }

            public Runnable setChannel(Channel channel) {
                this.channel = channel;
                return this;
            }

        }.setChannel(channel), 15, heartbeatRate, TimeUnit.SECONDS);
    }
}
