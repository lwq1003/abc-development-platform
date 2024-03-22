package tech.abc.platform.cip.message.framework;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.abc.platform.cip.config.AppConfig;
import tech.abc.platform.cip.entity.ActiveMessage;
import tech.abc.platform.cip.message.framework.sender.MessageSenderFactory;
import tech.abc.platform.cip.message.framework.sender.RequestMessageSender;
import tech.abc.platform.cip.service.ActiveMessageService;
import tech.abc.platform.cip.service.MessageLogService;

import java.util.List;

/**
 * 重发消息
 *
 * @author wqliu
 * @date 2022-1-29
 **/
@Component
@Slf4j
public class ResendMessage {
    @Autowired
    private MessageLogService apiMessageLogService;

    @Autowired
    private ActiveMessageService activeMessageService;

    @Autowired
    private AppConfig appConfig;

    public void resend() {
        // 需要进行异常处理，否则某次异常会导致定时器停止运行
        try {
            // 先获取需要重发的应用列表
            List<String> resendAppList = activeMessageService.getResendAppList(appConfig.getMessage().getMaxSendCount());
            if (CollectionUtils.isNotEmpty(resendAppList)) {
                log.info("读取到需要重发的应用数量:{}", resendAppList.size());
                // 遍历应用列表，获取要重发的消息
                for (String appCode : resendAppList) {

                    // 查找待重发的消息
                    List<ActiveMessage> list =
                            activeMessageService.getResendMessage(appConfig.getMessage().getSendMessageCount(),
                                    appConfig.getMessage().getMaxSendCount(), appCode);
                    log.info("读取到需要重发至应用{}的消息数量:{}", appCode, list.size());
                    for (int i = 0; i < list.size(); i++) {
                        ActiveMessage activeMessage = list.get(i);
                        // 根据消息主题构建发送器
                        RequestMessageSender sender = (RequestMessageSender) MessageSenderFactory.createSender(activeMessage.getRequestTopicCode());
                        // 传入原请求的消息标识和消息内容
                        sender.sendMessage(activeMessage.getResponseAppCode(), activeMessage.getRequestData(), activeMessage.getRequestId());
                    }

                }
            }

        } catch (Exception e) {
            log.error("消息重发处理异常", e);
        }
    }
}
