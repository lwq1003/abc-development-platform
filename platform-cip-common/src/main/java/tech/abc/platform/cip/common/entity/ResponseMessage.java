package tech.abc.platform.cip.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.abc.platform.cip.common.enums.MessageTypeEnum;


/**
 * 响应消息
 *
 * @author wqliu
 * @date 2021-10-5
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseMessage extends BaseMessage {

    /**
     * 执行结果
     */
    private String result;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 请求消息id
     */
    private String requestMessageId;


    public ResponseMessage() {
        // 默认设置消息类型
        super.setMessageType(MessageTypeEnum.RESPONSE.name());
    }

}
