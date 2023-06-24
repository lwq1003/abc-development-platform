package tech.abc.platform.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;

/**
 * 模块 实体类
 *
 * @author wqliu
 * @date 2023-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_module")
public class Module extends BaseEntity {

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
     * 应用
     */
    @TableField("app")
    private String app;

    /**
     * 缩略码
     */
    @TableField("abbreviation")
    private String abbreviation;

    /**
     * 包路径
     */
    @TableField("package_path")
    private String packagePath;

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
