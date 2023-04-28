package tech.abc.platform.entityconfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 视图按钮 实体类
 *
 * @author wqliu
 * @date 2023-04-16
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cfg_view_button")
public class ViewButton extends BaseEntity {

    /**
     * 视图
     */
    @TableField("view")
    private String view;

    /**
     * 按钮类型
     */
    @TableField("button_type")
    private String buttonType;

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
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 是否需确认
     */
    @TableField("confirm_flag")
    private String confirmFlag;

    /**
     * 确认信息
     */
    @TableField("confirm_message")
    private String confirmMessage;

    /**
     * 是否控制权限
     */
    @TableField("permission_flag")
    private String permissionFlag;

    /**
     * 权限编码
     */
    @TableField("permission_code")
    private String permissionCode;

    /**
     * 是否用于更多
     */
    @TableField("more_flag")
    private String moreFlag;

    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

}
