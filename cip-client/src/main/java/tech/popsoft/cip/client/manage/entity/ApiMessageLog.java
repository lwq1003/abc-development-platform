package tech.popsoft.cip.client.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 消息日志
 *
 * @author wqliu
 * @date 2021-08-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cip_message_log")
public class ApiMessageLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 请求消息标识
     */
    @TableField("request_id")
    private String requestId;

    /**
     * 请求应用编码
     */
    @TableField("request_app_code")
    private String requestAppCode;

    /**
     * 请求消息主题编码
     */
    @TableField("request_topic_code")
    private String requestTopicCode;

    /**
     * 请求时间
     */
    @TableField("request_time")
    private LocalDateTime requestTime;

    /**
     * 请求内容
     */
    @TableField("request_data")
    private String requestData;

    /**
     * 响应消息标识
     */
    @TableField("response_id")
    private String responseId;

    /**
     * 响应应用编码
     */
    @TableField("response_app_code")
    private String responseAppCode;

    /**
     * 响应消息主题编码
     */
    @TableField("response_topic_code")
    private String responseTopicCode;

    /**
     * 响应时间
     */
    @TableField("response_time")
    private LocalDateTime responseTime;

    /**
     * 响应内容
     */
    @TableField("response_data")
    private String responseData;

    /**
     * 响应结果
     */
    @TableField("response_result")
    private String responseResult;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 当前状态
     */
    @TableField("status")
    private String status;

    /**
     * 发送次数
     */
    @TableField("send_count")
    private Integer sendCount;

}
