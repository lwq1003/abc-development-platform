package tech.abc.platform.support.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 组件
 * @author wqliu
 * *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_portlet")

public class Portlet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 类别标识
     */
    @TableField("category_id")
    private String categoryId;

    /**
     * 名称
     */
    @TableField("name")
    @NotBlank(message="名称不能为空")
    private String name;

    /**
     * 编码
     */
    @TableField("code")
    @NotBlank(message="编码不能为空")
    private String code;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 排序号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 排序号
     */
    @TableField(exist = false)
    private List<PortletParam> paramList=new ArrayList<>();
}
