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
 * 组件参数 实体类
 *
 * @author wqliu
 * @date 2023-06-02
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_portlet_param")
public class PortletParam extends BaseEntity {

    /**
    * 组件
    */
    @TableField("portlet")
    private String portlet;
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
    * 值
    */
    @TableField("value")
    private String value;
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
