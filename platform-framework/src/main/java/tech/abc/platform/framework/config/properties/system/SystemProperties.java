package tech.abc.platform.framework.config.properties.system;

import lombok.Data;

/**
 * 系统配置属性
 *
 * @author wqliu
 * @date 2023-03-08
 */
@Data
public class SystemProperties {

    /**
     * 是否启用权限认证
     */
    private boolean enablePermission = true;

    /**
     * 身份认证令牌有效时长（单位分钟）
     */
    private long tokenValidSpan = 720;

    /**
     * 导出数据单次处理数据量
     */
    private long exportDataBatchSize = 10000;

    /**
     * 身份认证令牌密钥
     */
    private String tokenSecret = "wqliu";

}
