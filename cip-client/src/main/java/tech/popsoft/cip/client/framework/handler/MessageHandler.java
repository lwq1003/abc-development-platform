package tech.popsoft.cip.client.framework.handler;


import tech.abc.platform.cip.common.entity.MessageException;
import tech.abc.platform.common.enums.StatusEnum;
import tech.abc.platform.common.utils.SpringUtil;
import tech.popsoft.cip.client.manage.entity.ApiMessageTopic;
import tech.popsoft.cip.client.manage.service.ApiMessageLogService;
import tech.popsoft.cip.client.manage.service.ApiMessageTopicService;


/**
 * 消息处理抽象类
 *
 * @author wqliu
 * @date 2021-10-13
 **/
public class MessageHandler {


    protected ApiMessageLogService apiMessageLogService = SpringUtil.getBean(ApiMessageLogService.class);

    protected ApiMessageTopicService apiMessageTopicService = SpringUtil.getBean(ApiMessageTopicService.class);


    /**
     * 验证主题编码
     *
     * @param topicCode 主题编码
     */
    protected void validateTopic(String topicCode) {
        try {

            ApiMessageTopic messageTopic = apiMessageTopicService.getByCode(topicCode);
            if (messageTopic.getStatus().equals(StatusEnum.DEAD.name())) {
                throw new MessageException("S102", "消息主题不可用");
            }
        } catch (Exception ex) {
            throw new MessageException("S101", "消息主题不存在");
        }

    }


}
