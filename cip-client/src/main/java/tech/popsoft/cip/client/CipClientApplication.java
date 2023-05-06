package tech.popsoft.cip.client;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StopWatch;
import tech.abc.platform.common.utils.SpringUtil;
import tech.abc.platform.framework.config.PlatformConfig;
import tech.popsoft.cip.client.framework.MessageClient;
import tech.popsoft.cip.client.framework.MessageClientConfig;


/**
 * @author wqliu
 */
@Slf4j
@Import(SpringUtil.class)
@EnableRetry
@ServletComponentScan
@EnableTransactionManagement
@EnableConfigurationProperties({MessageClientConfig.class, PlatformConfig.class})
@SpringBootApplication(scanBasePackages = {"tech.abc.platform.**", "tech.popsoft.cip.**"})
@MapperScan({"tech.abc.platform.**.mapper", "tech.popsoft.cip.**.mapper"})
public class CipClientApplication implements CommandLineRunner {

    @Autowired
    private MessageClient messageClient;

    public static void main(String[] args) {


        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ConfigurableApplicationContext context = new SpringApplicationBuilder(CipClientApplication.class)
                .logStartupInfo(false)
                .run(args);
        stopWatch.stop();
        Integer port = context.getBean(ServerProperties.class).getPort();
        log.info("服务启动完成，耗时:{}s，端口号:{}", stopWatch.getTotalTimeSeconds(), port);


    }


    @Override
    public void run(String... args) throws Exception {

        // 此处通过单线程启动netty，是为了不堵塞主应用，不需要线程池
        // noinspection AlibabaAvoidManuallyCreateThread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                messageClient.start();
            }
        });
        // 启动netty服务
        thread.start();

    }


}
