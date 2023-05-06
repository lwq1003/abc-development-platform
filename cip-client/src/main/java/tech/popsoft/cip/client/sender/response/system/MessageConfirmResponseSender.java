package tech.popsoft.cip.client.sender.response.system;


import tech.popsoft.cip.client.framework.sender.ResponseMessageSender;

/**
 * 向请求方发送发生消息确认的响应
 *
 * @author wqliu
 * @date 2021-10-14 10:38
 **/
public class MessageConfirmResponseSender extends ResponseMessageSender {

    public MessageConfirmResponseSender() {
        super("framework.message.confirm.response");

    }


}
