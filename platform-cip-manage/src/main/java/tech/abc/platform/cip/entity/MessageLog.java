package tech.abc.platform.cip.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 消息日志 实体类
 *
 * @author wqliu
 * @date 2023-05-03
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cip_message_log")
public class MessageLog extends BaseEntity {

    /**
     * 请求标识
     */
    @TableField("request_id")
    private String requestId;

    /**
     * 请求应用编码
     */
    @TableField("request_app_code")
    private String requestAppCode;

    /**
     * 请求主题编码
     */
    @TableField("request_topic_code")
    private String requestTopicCode;

    /**
     * 请求时间
     */
    @TableField("request_time")
    private LocalDateTime requestTime;

    /**
     * 请求数据
     */
    @TableField("request_data")
    private String requestData;

    /**
     * 响应标识
     */
    @TableField("response_id")
    private String responseId;

    /**
     * 响应应用编码
     */
    @TableField("response_app_code")
    private String responseAppCode;

    /**
     * 响应主题编码
     */
    @TableField("response_topic_code")
    private String responseTopicCode;

    /**
     * 响应时间
     */
    @TableField("response_time")
    private LocalDateTime responseTime;

    /**
     * 响应数据
     */
    @TableField("response_data")
    private String responseData;

    /**
     * 响应结果
     */
    @TableField("response_result")
    private String responseResult;

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
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 发送次数
     */
    @TableField("send_count")
    private Integer sendCount;

}
