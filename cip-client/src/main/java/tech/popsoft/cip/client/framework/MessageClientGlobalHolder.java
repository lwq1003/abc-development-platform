package tech.popsoft.cip.client.framework;

import io.netty.channel.Channel;
import lombok.experimental.UtilityClass;

/**
 * 客户端全局容器
 *
 * @author wqliu
 * @date 2022-2-3 9:28
 **/
@UtilityClass
public class MessageClientGlobalHolder {
    /**
     * 通道
     */
    public static Channel channel = null;
}
