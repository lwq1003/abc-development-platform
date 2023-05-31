package tech.abc.platform.support.entity;

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
 * 流水号 实体类
 *
 * @author wqliu
 * @date 2023-05-30
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_serial_no")
public class SerialNo extends BaseEntity {

    /**
    * 模块
    */
    @TableField("module")
    private String module;
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
    * 前缀
    */
    @TableField("prefix")
    private String prefix;
    /**
    * 日期格式
    */
    @TableField("date_format")
    private String dateFormat;
    /**
    * 长度
    */
    @TableField("length")
    private Integer length;
    /**
    * 连接符
    */
    @TableField("connector")
    private String connector;
    /**
    * 当前值
    */
    @TableField("current_value")
    private Integer currentValue;
    /**
    * 重置策略
    */
    @TableField("reset_strategy")
    private String resetStrategy;
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
    /********非库表存储属性*****/
}
