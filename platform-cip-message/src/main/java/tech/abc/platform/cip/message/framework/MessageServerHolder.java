package tech.abc.platform.cip.message.framework;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 服务端容器
 *
 * @author wqliu
 * @date 2021-2-5
 **/
@Slf4j
@UtilityClass
public class MessageServerHolder {


    /**
     * 应用channel
     */
    public static ConcurrentMap<String, Channel> appChannelMap = new ConcurrentHashMap();


    /**
     * 应用编码键值
     */
    private final static String APP_CODE_KEY = "appCode";

    /**
     * 创建通道
     *
     * @param channel
     * @param appCode
     */
    public static void addChannel(Channel channel, String appCode) {

        // 为channel添加应用编码属性，便于移除时查找，以及向指定的通道推送消息
        AttributeKey<String> appCodeAttribute = AttributeKey.valueOf(APP_CODE_KEY);
        channel.attr(appCodeAttribute).set(appCode);
        appChannelMap.put(appCode, channel);


    }

    /**
     * 移除通道
     *
     * @param channel
     */
    public static void removeChannel(Channel channel) {

        // 从channel属性中读取到用户标识
        String appCode = getAppCode(channel);
        if (StringUtils.isNotBlank(appCode)) {
            appChannelMap.remove(appCode);

        }
    }

    /**
     * 根据应用编码获取相应通道
     *
     * @param channel
     */
    public static Channel getChannel(String appCode) {

        return appChannelMap.get(appCode);


    }

    /**
     * 根据通道获取相应应用编码
     *
     * @param channel
     */
    public static String getAppCode(Channel channel) {

        // 从channel属性中读取到用户标识
        AttributeKey<String> appCodeAttribute = AttributeKey.valueOf(APP_CODE_KEY);
        return channel.attr(appCodeAttribute).get();
    }


}
