package tech.abc.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统参数 实体类
 *
 * @author wqliu
 * @date 2023-04-17
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_param")
public class Param extends BaseEntity {

    /**
     * 参数名称
     */
    @TableField("param_name")
    private String paramName;

    /**
     * 参数编码
     */
    @TableField("param_key")
    private String paramKey;

    /**
     * 参数值
     */
    @TableField("param_value")
    private String paramValue;

    /**
     * 排序号
     */
    @TableField("order_no")
    private String orderNo;

}
