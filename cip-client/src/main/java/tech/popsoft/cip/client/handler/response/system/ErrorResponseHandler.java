package tech.popsoft.cip.client.handler.response.system;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.popsoft.cip.client.framework.handler.ResponseMessageHandler;

/**
 * 错误响应处理器
 *
 * @author wqliu
 * @date 2022-1-16 8:53
 **/
@Slf4j
public class ErrorResponseHandler extends ResponseMessageHandler {
    @Override
    protected void messageOperation(ResponseMessage message, Channel channel) {
        log.info("收到错误响应");


    }
}
