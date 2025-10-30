package tech.abc.platform.notification.server.listener;

import org.springframework.beans.factory.annotation.Autowired;
import tech.abc.platform.notification.config.NotificationConfig;
import tech.abc.platform.notification.server.InsideMessageServer;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * NettyServer监听器，用于启动消息服务
 *
 * @author wqliu
 * @date 2021-2-5
 **/
@WebListener
public class InsideMessageServerListener implements ServletContextListener {

    /**
     * 通知类型常量，网络套接字
     */
    public static final String WEB_SOCKET = "WebSocket";

    @Resource
    private NotificationConfig notificationConfig;


    /**
     * 注入NettyServer
     */
    @Autowired
    private InsideMessageServer nettyServer;

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 当通知类型为WebSocket时，启动netty消息服务端
        if (notificationConfig.getNotificationType().equals(WEB_SOCKET)) {
            Thread thread = new Thread(new NettyServerThread());
            // 启动netty服务
            thread.start();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    /**
     * netty服务启动线程
     */
    private class NettyServerThread implements Runnable {

        @Override
        public void run() {
            nettyServer.run();
        }
    }

}