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
 * 工作流程跳转环节配置 实体类
 *
 * @author wqliu
 * @date 2023-08-23
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wf_workflow_jump_node_config")
public class WorkflowJumpNodeConfig extends BaseEntity {

    /**
    * 流程定义
    */
    @TableField("process_definition_id")
    private String processDefinitionId;

    /**
    * 环节标识
    */
    @TableField("node_id")
    private String nodeId;

    /**
    * 目标环节标识
    */
    @TableField("target_node_id")
    private String targetNodeId;

    /**
    * 目标环节名称
    */
    @TableField("target_node_name")
    private String targetNodeName;

    /**
    * 排序
    */
    @TableField("order_no")
    private String orderNo;

    /********非库表存储属性*****/
}
