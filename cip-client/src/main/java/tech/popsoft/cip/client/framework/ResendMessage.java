package tech.popsoft.cip.client.framework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.popsoft.cip.client.framework.sender.MessageSenderFactory;
import tech.popsoft.cip.client.framework.sender.RequestMessageSender;
import tech.popsoft.cip.client.manage.entity.ApiMessageLog;
import tech.popsoft.cip.client.manage.service.ApiMessageLogService;

import java.util.List;

/**
 * 重发消息
 *
 * @author wqliu
 * @date 2022-1-24 8:22
 **/
@Component
@Slf4j
public class ResendMessage {
    @Autowired
    private ApiMessageLogService apiMessageLogService;

    @Autowired
    private MessageClientConfig config;

    public void resend() {
        // 需要进行异常处理，否则某次异常会导致定时器停止运行
        try {
            // 判断连接状态
            if (MessageClientGlobalHolder.channel != null) {
                // 查找待重发的消息
                List<ApiMessageLog> list =
                        apiMessageLogService.getResendMessage(config.getSendMessageCount(), config.getMaxSendCount());
                for (int i = 0; i < list.size(); i++) {
                    ApiMessageLog log = list.get(i);
                    // 根据消息主题构建发送器
                    RequestMessageSender sender = (RequestMessageSender) MessageSenderFactory.createSender(log.getRequestTopicCode());
                    // 传入原请求的消息标识和消息内容
                    sender.sendMessage(log.getRequestData(), log.getRequestId());
                }

            }
        } catch (Exception e) {
            log.error("消息重发处理异常", e);
        }
    }
}
