package tech.abc.platform.support.entity;

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
 * 通知接收 实体类
 *
 * @author wqliu
 * @date 2023-06-15
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_notice_receiver")
public class NoticeReceiver extends BaseEntity {

    /**
    * 通知通告
    */
    @TableField("notice")
    private String notice;
    /**
    * 组织机构
    */
    @TableField("organization")
    private String organization;
    /********非库表存储属性*****/
}
