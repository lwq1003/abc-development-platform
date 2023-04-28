package tech.abc.platform.boot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wqliu
 * @date 2023-3-4
 */
@SpringBootApplication(scanBasePackages = "tech.abc")
@MapperScan("tech.abc.**.mapper")
public class PlatformBootApplication {
    public static void main(String[] args) {

        SpringApplication.run(PlatformBootApplication.class, args);

    }
}
