package tech.abc.platform.notification.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 系统消息 实体类
 *
 * @author wqliu
 * @date 2023-05-27
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ntfc_system_message")
public class SystemMessage extends BaseEntity {

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 接收人
     */
    @TableField("receiver")
    private String receiver;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 是否已读
     */
    @TableField("read_flag")
    private String readFlag;

    /********非库表存储属性*****/
}
