package tech.abc.platform.cip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;

/**
 * 消息主题 实体类
 *
 * @author wqliu
 * @date 2023-05-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cip_message_topic")
public class MessageTopic extends BaseEntity {

    /**
     * 分类
     */
    @TableField("category")
    private String category;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 处理器
     */
    @TableField("handler")
    private String handler;

    /**
     * 发送器
     */
    @TableField("sender")
    private String sender;

    /**
     * 响应主题编码
     */
    @TableField("response_topic_code")
    private String responseTopicCode;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


    /**
     * 是否已授权
     */
    @TableField(exist = false)
    private String hasPermission;

    /**
     * 是否已订阅
     */
    @TableField(exist = false)
    private String hasSubscription;
}
