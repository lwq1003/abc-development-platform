package tech.abc.platform.notification.server.constant;

import lombok.experimental.UtilityClass;

/**
 * netty常量
 *
 * @author wqliu
 * @date 2021-2-6 15:40
 **/
@UtilityClass
public class NettyConstant {

    /**
     * 心跳请求内容
     */
    public static final String HEART_BEAT_REQUEST = "@heartbeatRequest@";

    /**
     * 心跳响应内容
     */
    public static final String HEART_BEAT_RESPONSE = "@heartbeatResponse@";


    /**
     * 读取空闲时间
     */
    public static final long READ_IDLE_TIME_OUT = 0;

    /**
     * 写入空闲时间
     */
    public static final long WRITE_IDLE_TIME_OUT = 0;

    /**
     * 读写皆空闲时间
     */
    public static final long ALL_IDLE_TIME_OUT = 30;
}
