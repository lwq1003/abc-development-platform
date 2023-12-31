package tech.abc.platform.support.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;

import java.util.List;

/**
 * 组件 实体类
 *
 * @author wqliu
 * @date 2023-06-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("spt_portlet")
public class Portlet extends BaseEntity {

    /**
     * 分类
     */
    @TableField("category")
    private String category;
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
     * 宽度
     */
    @TableField("width")
    private Integer width;
    /**
     * 高度
     */
    @TableField("height")
    private Integer height;
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

    @TableField(exist = false)
    private List<PortletParam> paramList;
}
