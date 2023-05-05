package tech.abc.platform.cip.entity;

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
 * API服务权限 实体类
 *
 * @author wqliu
 * @date 2023-05-03
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cip_api_service_permission")
public class ApiServicePermission extends BaseEntity {

    /**
     * 应用
     */
    @TableField("app")
    private String app;

    /**
     * API服务
     */
    @TableField("api_service")
    private String apiService;

}
