package tech.abc.platform.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import tech.abc.platform.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流程监听器配置
 * @author wqliu
 * @date 2020-10-08
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wf_workflow_listener_config")

public class WorkflowListenerConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义标识
     */
    @TableField("process_definition_id")
    private String processDefinitionId;

    /**
     * 元素编码
     */
    @TableField("definition_key")
    private String definitionKey;

    /**
     * 类别
     */
    @TableField("category")
    private String category;

    /**
     * 事件
     */
    @TableField("event")
    private String event;

    /**
     * 类型
     */
    @TableField("listener_type")
    private String listenerType;

    /**
     * 监听器名称
     */
    @TableField("listener_name")
    private String listenerName;

    /**
     * 监听器编码
     */
    @TableField("listener_code")
    private String listenerCode;

}
