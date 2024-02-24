package tech.abc.platform.boot;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.util.StopWatch;

import tech.abc.edoc.edoc.entity.Document;
import tech.abc.platform.cip.message.framework.MessageServer;

import javax.annotation.Resource;

/**
 * 文档管理系统启动
 * @author wqliu
 * @date 2024-1-26
 */
@SpringBootApplication(scanBasePackages = "tech.abc")
@MapperScan("tech.abc.**.mapper")
@Slf4j
@EnableRetry
@EnableElasticsearchRepositories(basePackages = "tech.abc")
public class EdocBootApplication implements CommandLineRunner {

    @Autowired
    private MessageServer messageServer;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ConfigurableApplicationContext context = new SpringApplicationBuilder(EdocBootApplication.class)
                .logStartupInfo(false)
                .run(args);
        stopWatch.stop();
        Integer port = context.getBean(ServerProperties.class).getPort();
        log.info("服务启动完成，耗时:{}s，端口号:{}", stopWatch.getTotalTimeSeconds(), port);


    }


    @Override
    public void run(String... args) throws Exception {
        //TODO 为提升启动速度，临时停用全文搜索运行
        startElasticSearch();

        //启动消息服务端
        startMessageServer();


    }

    /**
     * 启动全文搜索
     */
    private void startElasticSearch() {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                org.springframework.data.elasticsearch.core.document.Document mapping = elasticsearchRestTemplate.indexOps(Document.class).createMapping(Document.class);
                elasticsearchRestTemplate.indexOps(Document.class).putMapping(mapping);
            }
        };
        //此处通过单线程启动，是为了不堵塞主应用，不需要线程池
        //noinspection AlibabaAvoidManuallyCreateThread
        Thread thread=new Thread(runnable);
        thread.start();
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

