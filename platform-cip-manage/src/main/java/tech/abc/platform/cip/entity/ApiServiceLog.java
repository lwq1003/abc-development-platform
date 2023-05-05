package tech.abc.platform.cip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * API服务日志 实体类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cip_api_service_log")
public class ApiServiceLog extends BaseEntity {

    /**
     * 应用编码
     */
    @TableField("app_code")
    private String appCode;

    /**
     * 服务编码
     */
    @TableField("service_code")
    private String serviceCode;

    /**
     * 请求时间
     */
    @TableField("request_time")
    private LocalDateTime requestTime;

    /**
     * 收到时间
     */
    @TableField("receive_time")
    private LocalDateTime receiveTime;

    /**
     * 请求业务数据
     */
    @TableField("request_business_data")
    private String requestBusinessData;

    /**
     * 执行结果
     */
    @TableField("execute_result")
    private String executeResult;

    /**
     * 错误编码
     */
    @TableField("error_code")
    private String errorCode;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 响应时间
     */
    @TableField("response_time")
    private LocalDateTime responseTime;

    /**
     * 响应业务数据
     */
    @TableField("response_business_data")
    private String responseBusinessData;

    /**
     * 执行耗时ms
     */
    @TableField("execution_time")
    private Long executionTime;

}
