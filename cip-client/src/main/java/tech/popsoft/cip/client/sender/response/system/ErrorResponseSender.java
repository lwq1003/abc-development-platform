package tech.popsoft.cip.client.sender.response.system;


import tech.abc.platform.cip.common.enums.MessageResponseResultEnum;
import tech.popsoft.cip.client.framework.sender.ResponseMessageSender;


/**
 * 向请求方发送发生错误的响应
 *
 * @author wqliu
 * @date 2021-10-14 10:38
 **/
public class ErrorResponseSender extends ResponseMessageSender {

    public ErrorResponseSender() {
        super("framework.error.response");
        // 默认设置结果为出错
        this.setResult(MessageResponseResultEnum.ERROR.name());
        // 默认设置错误编码
        this.setErrorCode("500");
    }


}
