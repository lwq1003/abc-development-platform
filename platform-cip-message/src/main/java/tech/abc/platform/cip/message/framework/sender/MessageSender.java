package tech.abc.platform.cip.message.framework.sender;

import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.config.AppConfig;
import tech.abc.platform.cip.service.MessageLogService;
import tech.abc.platform.common.utils.SpringUtil;


/**
 * 消息发送器基类
 *
 * @author wqliu
 * @date 2021-10-5
 */
@Slf4j
public class MessageSender {

    public MessageSender() {

    }

    /**
     * 消息主题
     */
    private String topic;
    /**
     * 消息内容
     */
    private String content;

    public MessageSender(String topic) {
        this.topic = topic;
    }

    /**
     * 获取消息主题
     */
    public String getTopic() {
        return this.topic;
    }


    /**
     * 设置消息内容
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取消息内容
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 应用程序配置
     */
    protected AppConfig appConfig = SpringUtil.getBean(AppConfig.class);

    /**
     * 消息日志服务
     */
    protected MessageLogService apiMessageLogService = SpringUtil.getBean(MessageLogService.class);

}
