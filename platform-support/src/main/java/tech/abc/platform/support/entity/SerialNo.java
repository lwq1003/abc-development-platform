package tech.abc.platform.support.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;

/**
 * 单据流水号
 *
 * @author wqliu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_serial_no")

public class SerialNo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 单据编码
     */
    @TableField("code")
    private String code;

    /**
     * 单据名称
     */
    @TableField("name")
    private String name;

    /**
     * 前缀
     */
    @TableField("prefix")
    private String prefix;

    /**
     * 日期
     */
    @TableField("date_part")
    private String datePart;

    /**
     * 流水号长度
     */
    @TableField("length")
    private Integer length;

    /**
     * 连接符
     */
    @TableField("connectors")
    private String connectors;

    /**
     * 当前流水号
     */
    @TableField("serial_no")
    private Integer serialNo;

    /**
     * 重置策略
     */
    @TableField("reset_strategy")
    private String resetStrategy;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 排序号
     */
    @TableField("order_no")
    private String orderNo;

}
