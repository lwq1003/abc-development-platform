package tech.abc.platform.cip.api.handler.system;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 消息查询参数
 *
 * @author wqliu
 * @date 2021-8-20
 **/
@Data
public class MessageQueryParameter {

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空")
    private Integer count;
}
