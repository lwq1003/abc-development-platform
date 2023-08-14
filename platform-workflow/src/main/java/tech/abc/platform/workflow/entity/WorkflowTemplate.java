package tech.abc.platform.workflow.entity;

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
 * 工作流程模板 实体类
 *
 * @author wqliu
 * @date 2023-08-10
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
    * 启用状态
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

    /**
    * 模板状态
    */
    @TableField("template_status")
    private String templateStatus;

    /**
    * 流程定义
    */
    @TableField("process_definition_id")
    private String processDefinitionId;

    /********非库表存储属性*****/
}
