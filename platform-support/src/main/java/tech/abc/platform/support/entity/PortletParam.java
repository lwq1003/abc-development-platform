package tech.abc.platform.support.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 组件参数
 * @author wqliu
 * *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_portlet_param")

public class PortletParam extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组件标识
     */
    @TableField("portlet_id")
    private String portletId;

    /**
     * 名称
     */
    @TableField("param_name")
    @NotBlank(message="名称不能为空")
    private String paramName;

    /**
     * 编码
     */
    @TableField("param_code")
    @NotBlank(message="编码不能为空")
    private String paramCode;

    /**
     * 值
     */
    @TableField("param_value")
    @NotBlank(message="值不能为空")
    private String paramValue;

    /**
     * 排序号
     */
    @TableField("order_no")
    private String orderNo;

}
