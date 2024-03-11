package tech.abc.platform.common.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 映射关系实体模型
 *
 * @author wqliu
 * @date 2023-03-06
 */
@Data
public class BaseMapEntity extends BaseIdEntity {
    /**
     * 创建人标识
     */

    @TableField(value = "create_id", fill = FieldFill.INSERT)
    private String createId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人标识
     */

    @TableField(value = "update_id", fill = FieldFill.INSERT_UPDATE)
    private String updateId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 版本
     */

    @TableField(value = "version", fill = FieldFill.INSERT)
    @Version
    private Integer version;


}
