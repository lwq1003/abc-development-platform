package tech.popsoft.cip.client.handler.request.lms.transportbill;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.popsoft.cip.client.framework.handler.RequestMessageHandler;

/**
 * 委托单创建请求消息处理器
 *
 * @author wqliu
 * @date 2022-1-22 10:20
 **/
@Slf4j
public class ConsignmentBillCreateRequestHandler extends RequestMessageHandler {

    @Override
    protected void messageOperation(RequestMessage message, Channel channel) {

        // 进行业务处理


    }
}
