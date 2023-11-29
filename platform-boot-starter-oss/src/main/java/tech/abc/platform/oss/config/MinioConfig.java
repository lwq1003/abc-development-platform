package tech.abc.platform.oss.config;

import lombok.Data;

/**
 * miniio配置属性
 *
 * @author wqliu
 * @date 2023-11-21
 */
@Data
public class MinioConfig {

    /**
     * 服务
     */
    private String server = "http://127.0.0.1:9000";
    /**
     * 账号
     */
    private String accessKey = "admin";

    /**
     * 密钥
     */
    private String secretKey = "12345678";

    /**
     * 桶名
     */
    private String bucketName = "abc";


}
