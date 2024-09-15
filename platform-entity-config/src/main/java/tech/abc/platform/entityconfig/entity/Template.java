package tech.abc.platform.entityconfig.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import tech.abc.platform.common.base.BaseEntity;

import java.time.LocalDateTime;

/**
 * 模板 实体类
 *
 * @author wqliu
 * @date 2024-09-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cfg_template")
public class Template extends BaseEntity {

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("content")
    private String content;

    /**
     * 组织机构
     */
    @TableField("organizaiton")
    private String organizaiton;

    /**
     * 时分秒
     */
    @TableField("time1")
    private String time1;

    /**
     * 时分
     */
    @TableField("time2")
    private String time2;

    /**
     * 时
     */
    @TableField("time3")
    private String time3;

    /**
     * 年月日
     */
    @TableField(value = "date1", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime date1;

    /**
     * 年月日时分
     */
    @TableField(value = "day2", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime day2;

    /**
     * 年月日时分秒
     */
    @TableField(value = "day3", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime day3;

    /********非库表存储属性*****/
}
