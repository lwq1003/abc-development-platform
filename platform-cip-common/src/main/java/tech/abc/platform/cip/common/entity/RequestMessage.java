package tech.abc.platform.cip.common.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import tech.abc.platform.cip.common.enums.MessageTypeEnum;


/**
 * 请求消息
 *
 * @author wqliu
 * @date 2021-10-5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class RequestMessage extends BaseMessage implements Cloneable {


    public RequestMessage() {
        // 默认设置消息类型
        super.setMessageType(MessageTypeEnum.REQUEST.name());


    }


    @Override
    public RequestMessage clone() {
        try {
            return (RequestMessage) super.clone();
        } catch (CloneNotSupportedException e) {
            log.error("对象不支持复制");
            return null;
        }

    }

}
