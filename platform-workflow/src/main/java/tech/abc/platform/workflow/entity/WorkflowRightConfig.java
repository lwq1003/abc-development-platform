package tech.abc.platform.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程权限配置
 * @author wqliu
 * @date 2020-11-08
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wf_workflow_right_config")

public class WorkflowRightConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义标识
     */
    @TableField("process_definition_id")
    private String processDefinitionId;

    /**
     * 环节编码
     */
    @TableField("definition_key")
    private String definitionKey;

    /**
     * 环节名称
     */
    @TableField("name")
    private String name;

    /**
     * 资源标识
     */
    @TableField("resource_id")
    private String resourceId;

    /**
     * 资源编码
     */
    @TableField("resource_code")
    private String resourceCode;

    /**
     * 是否可见
     */
    @TableField("visible_flag")
    private String visibleFlag;

    /**
     * 是否只读
     */
    @TableField("readonly_flag")
    private String readonlyFlag;

}
