package tech.popsoft.cip.client.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;


/**
 * 消息主题
 *
 * @author wqliu
 * @date 2021-08-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cip_message_topic")
public class ApiMessageTopic extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

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
     * 分类
     */
    @TableField("category")
    private String category;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 排序号
     */
    @TableField("order_no")
    private String orderNo;

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
