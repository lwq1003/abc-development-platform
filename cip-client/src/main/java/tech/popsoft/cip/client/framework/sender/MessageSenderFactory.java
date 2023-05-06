package tech.popsoft.cip.client.framework.sender;


import tech.abc.platform.cip.common.entity.MessageException;
import tech.abc.platform.common.exception.CustomException;
import tech.abc.platform.common.utils.SpringUtil;
import tech.popsoft.cip.client.manage.service.ApiMessageTopicService;


/**
 * 消息发送器工厂
 *
 * @author wqliu
 * @date 2022-01-29 16:30
 **/
public class MessageSenderFactory {
    private MessageSenderFactory() {
    }

    ;

    public static MessageSender createSender(String topic) {
        // 使用反射技术获取类
        Class<MessageSender> messageSender = null;
        try {
            // 根据消息主题获取对应的消息处理类名
            ApiMessageTopicService service = SpringUtil.getBean(ApiMessageTopicService.class);
            String senderName = service.getSenderByCode(topic);
            messageSender = (Class<MessageSender>) Class.forName(senderName);
            // 返回消息发送器类的实例
            return messageSender.newInstance();
        } catch (CustomException e) {
            throw new MessageException("S101", e.getMessage());
        } catch (Exception e) {
            throw new MessageException("S102", "消息发送器不存在");
        }
    }
}
