package tech.abc.platform.notification.server.global;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import tech.abc.platform.notification.entity.SystemMessage;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * netty客户端容器
 *
 * @author wqliu
 * @date 2021-2-5
 **/
@Slf4j
@UtilityClass
public class ClientHolder {

    /**
     * 全局组
     */
    private static ChannelGroup globalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 用户channel
     */
    private static ConcurrentMap<String, Set<Channel>> userChannelMap = new ConcurrentHashMap();


    /**
     * 用户标识键值
     */
    private final static String ACCOUNT = "account";

    /**
     * 创建通道
     *
     * @param channel
     * @param account
     */
    public static void addChannel(Channel channel, String account) {
        // 全局组
        globalGroup.add(channel);
        // 为channel添加属性，便于移除时查找
        AttributeKey<String> accountAttribute = AttributeKey.valueOf(ACCOUNT);
        channel.attr(accountAttribute).set(account);
        // 全局用户map
        if (account != null) {
            boolean containsKey = userChannelMap.containsKey(account);
            if (containsKey) {
                Set<Channel> channelSet = userChannelMap.get(account);
                channelSet.add(channel);
            } else {
                HashSet<Channel> set = new HashSet<>();
                set.add(channel);
                userChannelMap.put(account, set);
            }
        }
    }

    /**
     * 移除通道
     *
     * @param channel
     */
    public static void removeChannel(Channel channel) {
        // 全局组
        globalGroup.remove(channel);
        // 从channel属性中读取到用户标识
        AttributeKey<String> userIdAttribute = AttributeKey.valueOf(ACCOUNT);
        String userId = channel.attr(userIdAttribute).get();

        if (StringUtils.isNotBlank(userId)) {
            if (userChannelMap.containsKey(userId)) {
                Set<Channel> set = userChannelMap.get(userId);
                if (set.contains(channel)) {
                    set.remove(channel);
                }
            }
        }
    }

    public static void sendMessage(SystemMessage entity) {

        Set<Channel> channelSet = userChannelMap.get(entity.getReceiver());
        if (CollectionUtils.isNotEmpty(channelSet)) {
            for (Channel channel : channelSet) {
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(entity)));
            }
        }

    }
}
