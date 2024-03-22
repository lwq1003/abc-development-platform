package tech.abc.platform.cip.message.framework.handler;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.entity.ActiveMessage;
import tech.abc.platform.cip.entity.MessageLog;
import tech.abc.platform.cip.message.framework.MessageServerHolder;

/**
 * 响应消息处理器
 *
 * @author wqliu
 * @date 2022-1-8
 **/
@Slf4j
public class ResponseMessageHandler extends MessageHandler {

    /**
     * 消息处理
     *
     * @param message 消息
     * @param channel 通道
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleMessage(ResponseMessage responseMessage, Channel channel) {

        // 验证框架
        String appCode = MessageServerHolder.getAppCode(channel);
        validateFramework(responseMessage, appCode);

        // 更新活跃消息
        activeMessageService.updateResponsePart(responseMessage);
        // 拷贝至消息日志
        ActiveMessage activeMessage = activeMessageService.getByRequestMessageId(responseMessage.getRequestMessageId());
        MessageLog messageLog = new MessageLog();
        BeanUtils.copyProperties(activeMessage, messageLog);
        messageLogService.add(messageLog);
        // 删除活跃消息
        activeMessageService.remove(activeMessage.getId());
        // 特殊处理
        messageOperation(responseMessage, channel);
    }


    /**
     * 响应消息处理
     *
     * @param message
     * @param channel
     */
    protected void messageOperation(ResponseMessage message, Channel channel) {

    }


}
