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
 * 工作流程监听器配置 实体类
 *
 * @author wqliu
 * @date 2023-08-30
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wf_workflow_listener_config")
public class WorkflowListenerConfig extends BaseEntity {

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
    * 事件名称
    */
    @TableField("event_name")
    private String eventName;

    /**
    * 类型
    */
    @TableField("type")
    private String type;

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

    /********非库表存储属性*****/
}
