package tech.abc.platform.cip.message.handler.request.system;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.message.framework.handler.RequestMessageHandler;

/**
 * 登录请求处理器
 *
 * @author wqliu
 * @date 2021-10-13
 **/
@Slf4j
public class LoginRequestHandler extends RequestMessageHandler {

    @Override
    protected void messageOperation(RequestMessage message, Channel channel) {


    }

    @Override
    protected boolean isNeedRepost() {
        return false;
    }
}
