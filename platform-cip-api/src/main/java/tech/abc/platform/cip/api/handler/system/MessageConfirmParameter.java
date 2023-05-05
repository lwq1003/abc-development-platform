package tech.abc.platform.cip.api.handler.system;

import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * 消息确认参数
 *
 * @author wqliu
 * @date 2022-02-14
 */
@Data
public class MessageConfirmParameter {

    /**
     * 消息标识
     */
    @NotNull(message = "消息标识不能为空")
    private String messageId;
}
