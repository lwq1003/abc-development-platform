package tech.abc.platform.common.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 业务租户 视图对象 基类
 *
 * @author wqliu
 * @date 2024-06-25
 */
@Data
public class BaseTenantVO extends BaseVO {


    /**
     * 租户标识
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tenantId;
}
