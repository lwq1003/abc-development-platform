package tech.abc.platform.elasticsearch.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 对象存储配置文件
 *
 * @author wqliu
 * @date 2020-04-05 11:19
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "platform-config.file")
public class FileTypeConfig {

    /**
     * 文本类型
     */
    private List<String> textFileType = new ArrayList<>();

}
