package tech.abc.platform.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程模板 实体类
 *
 * @author wqliu
 * @date 2023-07-06
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wf_workflow_template")
public class WorkflowTemplate extends BaseEntity {

    /**
    * 类别
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
    * 模板版本
    */
    @TableField("template_version")
    private String templateVersion;

    /**
    * 状态
    */
    @TableField("status")
    private String status;

    /**
    * 模型
    */
    @TableField("model")
    private String model;

    /**
     * 排序
     */
    @TableField("order_no")
    private String orderNo;

    /********非库表存储属性*****/
}
