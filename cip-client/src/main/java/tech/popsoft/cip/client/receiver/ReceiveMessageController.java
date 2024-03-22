package tech.popsoft.cip.client.receiver;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.abc.platform.cip.common.entity.MessageException;
import tech.popsoft.cip.client.framework.sender.MessageSenderFactory;
import tech.popsoft.cip.client.framework.sender.RequestMessageSender;

/**
 * 接收业务系统产生的消息
 *
 * @author wqliu
 * @date 2022-1-18
 **/
@RestController
@RequestMapping("/message")
public class ReceiveMessageController {

    @GetMapping
    public String receive(String topic, String id) {
        // 数据验证
        if (StringUtils.isBlank(topic)) {
            throw new RuntimeException("消息主题不能为空");
        }
        // 根据消息主题查找发送器
        try {
            RequestMessageSender messageSender = (RequestMessageSender) MessageSenderFactory.createSender(topic);
            messageSender.sendMessage(id);
            return "ok";
        } catch (MessageException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
