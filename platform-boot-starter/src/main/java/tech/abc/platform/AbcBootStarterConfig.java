package tech.abc.platform;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tech.abc.platform.cip.config.AppConfig;
import tech.abc.platform.common.utils.SpringUtil;
import tech.abc.platform.framework.config.PlatformConfig;


/**
 * @author wqliu
 * <p>
 * 注：该模块为开发平台核心包，需作为业务系统的基础包，因此虽然是springboot的结构
 * 但不能加SpringBootApplication注解，并且在打包时需打成普通jar包
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({PlatformConfig.class, AppConfig.class})
@Import(SpringUtil.class)
@EnableRetry
@ServletComponentScan
@EnableTransactionManagement
public class AbcBootStarterConfig {


}
