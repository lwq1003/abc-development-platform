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
 * 任务参数 实体类
 *
 * @author wqliu
 * @date 2023-05-24
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("schd_job_param")
public class JobParam extends BaseEntity {

    /**
     * 任务
     */
    @TableField("job")
    private String job;

    /**
     * 参数名称
     */
    @TableField("param_name")
    private String paramName;

    /**
     * 参数编码
     */
    @TableField("param_code")
    private String paramCode;

    /**
     * 参数值
     */
    @TableField("param_value")
    private String paramValue;

    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

    /********非库表存储属性*****/
}
