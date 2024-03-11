package tech.abc.platform.cip.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseMapEntity;

import java.time.LocalDateTime;

/**
 * 消息权限 实体类
 *
 * @author wqliu
 * @date 2023-05-03
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cip_message_permission")
public class MessagePermission extends BaseMapEntity {

    /**
     * 应用
     */
    @TableField("app")
    private String app;

    /**
     * 消息主题
     */
    @TableField("message_topic")
    private String messageTopic;

}
