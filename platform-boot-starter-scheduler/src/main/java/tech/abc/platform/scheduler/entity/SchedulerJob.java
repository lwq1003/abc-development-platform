package tech.abc.platform.scheduler.entity;

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
 * 调度任务 实体类
 *
 * @author wqliu
 * @date 2023-05-24
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("schd_scheduler_job")
public class SchedulerJob extends BaseEntity {

    /**
     * 任务
     */
    @TableField("job")
    private String job;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 调度触发
     */
    @TableField("cron_expression")
    private String cronExpression;

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

    /********非库表存储属性*****/
}
