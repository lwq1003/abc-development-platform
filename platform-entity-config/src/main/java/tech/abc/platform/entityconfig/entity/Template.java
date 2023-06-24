package tech.abc.platform.entityconfig.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 模板 实体类
 *
 * @author wqliu
 * @date 2023-06-24
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cfg_template")
public class Template extends BaseEntity {

    /**
    * 用户单选
    */
    @TableField("user_single")
    private String userSingle;

    /**
    * 组织机构单选
    */
    @TableField("organization_single")
    private String organizationSingle;

    /**
    * 用户
    */
    @TableField("entity")
    private String entity;

    /**
    * 图标
    */
    @TableField("icon")
    private String icon;

    /**
    * 流水号
    */
    @TableField("serial_no")
    private String serialNo;

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
    * 日期
    */
    @TableField("date")
    private LocalDateTime date;

    /**
    * 时间
    */
    @TableField("time")
    private LocalDateTime time;

    /**
    * 是否
    */
    @TableField("yes_or_no")
    private String yesOrNo;

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

    /********非库表存储属性*****/
}
