package tech.abc.platform.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


/**
 * 业务租户实体 基类
 *
 * @author wqliu
 * @date 2024-06-25
 */
@Data
public class BaseTenantEntity extends BaseEntity {

    /**
     * 租户标识
     */
    @TableField("tenant_id")
    private Long tenantId;

}
