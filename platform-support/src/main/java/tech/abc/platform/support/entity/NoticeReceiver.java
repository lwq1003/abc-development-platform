package tech.abc.platform.support.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;

/**
 * 通知公告接收组织机构
 *
 * @author wqliu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_notice_receiver")
public class NoticeReceiver extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 通知公告标识
     */
    @TableField("notice_id")
    private String noticeId;

    /**
     * 组织机构标识
     */
    @TableField("organization_id")
    private String organizationId;


}
