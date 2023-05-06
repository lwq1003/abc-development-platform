package tech.popsoft.cip.client.framework.sender;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.abc.platform.cip.common.entity.RequestMessage;
import tech.abc.platform.cip.common.entity.ResponseMessage;
import tech.abc.platform.cip.common.enums.MessageResponseResultEnum;

/**
 * 响应消息发送器
 *
 * @author wqliu
 * @date 2022-1-31 8:14
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseMessageSender extends MessageSender {

    private String result;

    private String errorCode;

    private String errorMessage;


    public ResponseMessageSender(String topic) {
        super(topic);
        // 默认设置响应结果为成功
        this.result = MessageResponseResultEnum.SUCCESS.name();
    }


    /**
     * 发送响应
     *
     * @param channel        通道
     * @param requestMessage 请求消息
     */
    public void sendMessage(Channel channel, RequestMessage requestMessage) {

        // 组织响应消息
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setPublishAppCode(messageClientConfig.getAppCode());
        responseMessage.setRequestMessageId(requestMessage.getId());
        // 设置主题
        responseMessage.setTopic(this.getTopic());
        // 设置默认值
        responseMessage.setResult(this.result);
        responseMessage.setErrorCode(this.errorCode);
        responseMessage.setErrorMessage(this.errorMessage);
        // 设置响应
        setResponseContent(requestMessage, responseMessage, channel);
        // 发送响应给请求方
        channel.writeAndFlush(responseMessage);

        // 更新消息日志
        apiMessageLogService.updateResponsePart(responseMessage);

    }

    /**
     * 设置响应消息内容
     *
     * @param requestMessage  请求消息
     * @param responseMessage 响应消息
     * @param channel         通道
     */
    protected void setResponseContent(RequestMessage requestMessage, ResponseMessage responseMessage, Channel channel) {

    }


}
