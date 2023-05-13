package tech.abc.platform.common.modules.system.params;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: wqliu
 * @date 2020-03-26 19:34
 * :
 */
@Data
public class LogDTO {


    /**
     * 日志类型 OPERATION 操作日志 AUDIT 审计日志
     */
    private String logType;


    /**
     * 操作描述
     */
    private String content;


    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求时间
     */
    private LocalDateTime requestTime;

    /**
     * 执行耗时（毫秒）
     */
    private Integer timeConsuming;


    /**
     * 请求路径
     */
    private String requestPath;


    /**
     * 请求方法
     */
    private String requestMethod;


    /**
     * 是否执行成功
     */
    private String responseCode;

    /**
     * 响应数据
     */
    private String responseData;
}
