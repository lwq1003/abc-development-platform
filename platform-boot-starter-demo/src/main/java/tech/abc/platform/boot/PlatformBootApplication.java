package tech.abc.platform.boot;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.util.StopWatch;
import tech.abc.platform.cip.message.framework.MessageServer;

/**
 * @author wqliu
 * @date 2023-3-4
 */
@SpringBootApplication(scanBasePackages = "tech.abc")
@MapperScan("tech.abc.**.mapper")
@Slf4j
@EnableRetry
public class PlatformBootApplication implements CommandLineRunner {

    @Autowired
    private MessageServer messageServer;


    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ConfigurableApplicationContext context = new SpringApplicationBuilder(PlatformBootApplication.class)
                .logStartupInfo(false)
                .run(args);
        stopWatch.stop();
        Integer port = context.getBean(ServerProperties.class).getPort();
        log.info("服务启动完成，耗时:{}s，端口号:{}", stopWatch.getTotalTimeSeconds(), port);


    }


    @Override
    public void run(String... args) throws Exception {
        // 启动消息服务端
        startMessageServer();
    }


    /**
     * 启动消息服务端
     */
    private void startMessageServer() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                messageServer.run();
            }
        };
        // 此处通过单线程启动netty，是为了不堵塞主应用，不需要线程池
        // noinspection AlibabaAvoidManuallyCreateThread
        Thread thread = new Thread(runnable);
        // 启动消息服务端
        thread.start();
    }


}

