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
 * 应用数据权限 实体类
 *
 * @author wqliu
 * @date 2023-05-03
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cip_app_data_permission")
public class AppDataPermission extends BaseEntity {

    /**
     * 应用
     */
    @TableField("app")
    private String app;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 业务编码
     */
    @TableField("business_code")
    private String businessCode;

    /**
     * 二级业务编码
     */
    @TableField("second_business_code")
    private String secondBusinessCode;

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

}
