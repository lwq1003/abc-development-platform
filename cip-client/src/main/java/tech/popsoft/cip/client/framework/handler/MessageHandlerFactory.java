package tech.popsoft.cip.client.framework.handler;


import tech.abc.platform.cip.common.entity.MessageException;
import tech.abc.platform.common.utils.SpringUtil;
import tech.popsoft.cip.client.manage.service.ApiMessageTopicService;
import tech.popsoft.cip.client.platform.exception.CustomException;


/**
 * 消息处理器工厂
 *
 * @author wqliu
 * @date 2021-10-13 9:07
 **/
public class MessageHandlerFactory {
    private MessageHandlerFactory() {
    }

    ;

    public static MessageHandler createHandler(String topic) {
        // 使用反射技术获取类
        Class<MessageHandler> messageHandler = null;
        try {
            // 根据消息主题获取对应的消息处理类名
            ApiMessageTopicService service = SpringUtil.getBean(ApiMessageTopicService.class);
            String handlerName = service.getHandlerByCode(topic);
            messageHandler = (Class<MessageHandler>) Class.forName(handlerName);
            // 返回消息处理类的实例
            return messageHandler.newInstance();
        } catch (CustomException e) {
            throw new MessageException("S101", e.getMessage());
        } catch (Exception e) {
            throw new MessageException("S102", "消息处理器不存在");
        }
    }
}
