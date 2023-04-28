package tech.abc.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 组织机构 实体类
 *
 * @author wqliu
 * @date 2023-04-19
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_organization")
public class Organization extends BaseEntity {

    /**
     * 组织机构
     */
    @TableField("organization")
    private String organization;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

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

}
